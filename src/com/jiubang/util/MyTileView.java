package com.jiubang.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import com.jiubang.intface.ITileMotionCallBack;
import com.jiubang.tile.Setting;
import com.jiubang.tile.Tile;
import com.jiubang.tile.R;

public class MyTileView extends View implements OnTouchListener {

	public void setCallBack(ITileMotionCallBack callBack) {
		this.callBack = callBack;
	}

	/*** 旋转动画视图 */
	public View ani_rotate = null;
	/*** 左边带子动画视图 */
	public View ani_tran_left = null;
	/*** 右边带子动画视图 */
	public View ani_tran_rigth = null;
	/*** 鞋带洞 */
	static Drawable tile_hole = null;
	/*** 鞋带洞遮罩 */
	Drawable tile_hole_cover = null;
	/*** 鞋带 */
	public Bitmap tile_long = null;
	static Tile_item leftTile = null;
	static Tile_item rightTile = null;

	/*** 旋转动画 */
	Animation mMyRotate = null;
	/*** 拖动动画，左边 */
	Animation mTranslate_left = null;
	/*** 拖动动画，右边 */
	Animation mTranslate_right = null;
	/*** 动画监听器 */
	AnimationListener mAnimationListener = null;

	private static float tile_length = 70 * Tile.scaledDensity;

	private ITileMotionCallBack callBack = null;

	/**
	 * 触摸ID
	 */
	ArrayList<Integer> pointer_ids = new ArrayList<Integer>();

	public MyTileView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mAnimationListener = new MyAnimationListener();
		tile_hole = getResources().getDrawable(R.drawable.tile_hole);

		tile_hole_cover = getResources()
				.getDrawable(R.drawable.tile_hole_cover);

		tile_long = BitmapFactory.decodeStream(getResources().openRawResource(
				R.drawable.tile_lang));

		if (Tile.scaledDensity >= 1.0) {

			Matrix matrix = new Matrix();
			matrix.postScale(Tile.scaledDensity, Tile.scaledDensity);
			tile_long = Bitmap.createBitmap(tile_long, 0, 0,
					tile_long.getWidth(), tile_long.getHeight(), matrix, true);

		}

		setOnTouchListener(this);

		leftTile = new Tile_item();
		int left = 30;
		leftTile.padding_left = left * Tile.scaledDensity;
		leftTile.padding_top = 30 * Tile.scaledDensity;

		rightTile = new Tile_item();
		rightTile.padding_left = Tile.SCREEN_WIDTH
				- tile_hole.getIntrinsicWidth() + leftTile.padding_left;

		rightTile.padding_top = 30 * Tile.scaledDensity;

		initialSeneor();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		leftTile.onDraw(canvas);
		rightTile.onDraw(canvas);
	}

	public int currentPointCount = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		boolean falg = true;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			falg = false;

			// 多点触控

			for (int i = 0; i < event.getPointerCount(); i++) {
				int id = event.getPointerId(i);
				if (!leftTile.isTouched) {
					boolean isSelected = leftTile.isDownInRect(
							(int) event.getX(id), (int) event.getY(id));
					if (isSelected) {
						leftTile.Touched_pointer_id = id;
						falg = true;
					}
				}

				if (!rightTile.isTouched) {
					boolean isSelected = rightTile.isDownInRect(
							(int) event.getX(id), (int) event.getY(id));
					if (isSelected) {
						rightTile.Touched_pointer_id = id;
						falg = true;
					}
				}
				if (leftTile.isTouched || rightTile.isTouched) {
					showMenuBarAndAlwaysShow();
				}
			}

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			falg = false;

			pointer_ids.clear();
			for (int i = 0; i < event.getPointerCount(); i++) {
				pointer_ids.add(event.getPointerId(i));
			}
			if (!pointer_ids.contains(leftTile.Touched_pointer_id)) {
				translate_left();
			}
			if (!pointer_ids.contains(rightTile.Touched_pointer_id)) {
				translate_right();
			}

			for (int i = 0; i < event.getPointerCount(); i++) {
				int id = event.getPointerId(i);
				/**
				 * 设置每个鞋带的pointID，然后去匹配
				 */
				if (mTranslate_left == null) {
					if (leftTile.isTouched) {
						if (id == leftTile.Touched_pointer_id) {
							caluateDegree(event.getX(id), event.getY(id), true);
							falg = true;
						}

					} else {
						boolean isSelected = leftTile.isDownInRect(
								(int) event.getX(id), (int) event.getY(id));
						if (isSelected) {
							leftTile.Touched_pointer_id = id;
							falg = true;
						}
					}
				}

				if (mTranslate_right == null) {
					if (rightTile.isTouched) {
						if (id == rightTile.Touched_pointer_id) {
							caluateDegree(event.getX(id), event.getY(id), false);
							falg = true;
						}
					} else {
						boolean isSelected = rightTile.isDownInRect(
								(int) event.getX(id), (int) event.getY(id));
						if (isSelected) {
							rightTile.Touched_pointer_id = id;
							falg = true;
						}
					}
				}
				if (leftTile.isTouched || rightTile.isTouched) {
					setOnMenuBarSelectedIndex(event);
				}
			}

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			falg = false;

			translate_left();
			translate_right();
			setOnClickMeunBarIndex();
		} else {
			falg = false;
		}

		invalidate();
		return falg;
	}

	/**
	 * 松开鞋带弹回原处
	 */
	public void translate_left() {
		if (leftTile.distance != tile_length) {
			mTranslate_left = new MyTransformation(tile_length,
					leftTile.distance, leftTile);
			mTranslate_left.setDuration(4000);
			mTranslate_left.setAnimationListener(mAnimationListener);
			ani_tran_left.setAnimation(mTranslate_left);
			ani_tran_left.startAnimation(mTranslate_left);
		}
	}

	public void translate_right() {
		if (rightTile.distance != tile_length) {
			mTranslate_right = new MyTransformation(tile_length,
					rightTile.distance, rightTile);
			mTranslate_right.setDuration(4000);
			mTranslate_right.setAnimationListener(mAnimationListener);
			ani_tran_rigth.setAnimation(mTranslate_right);
			ani_tran_rigth.startAnimation(mTranslate_right);
		}
	}

	/*** 底部MenuBar选中的索引 */
	private int MenuBarSelectedIndex = -1;

	/*** 设置MenuBar中被选中的Index */
	public void setOnMenuBarSelectedIndex(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if (y > Tile.SCREEN_HEIGHT - Tile.MenuBar_Item_Height
				* Tile.scaledDensity) {
			float menu_one_dis = (x - Tile.MenuBar_first_x)
					* (x - Tile.MenuBar_first_x) + (y - Tile.MenuBar_y)
					* (y - Tile.MenuBar_y);
			if (menu_one_dis < Tile.MenuBar_Item_distance) {
				MenuBarSelectedIndex = 0;
			}

			menu_one_dis = (x - Tile.MenuBar_second_x)
					* (x - Tile.MenuBar_second_x) + (y - Tile.MenuBar_y)
					* (y - Tile.MenuBar_y);
			if (menu_one_dis < Tile.MenuBar_Item_distance) {
				MenuBarSelectedIndex = 1;
			}

			menu_one_dis = (x - Tile.MenuBar_third_x)
					* (x - Tile.MenuBar_third_x) + (y - Tile.MenuBar_y)
					* (y - Tile.MenuBar_y);
			if (menu_one_dis < Tile.MenuBar_Item_distance) {
				MenuBarSelectedIndex = 2;
			}

		} else {
			MenuBarSelectedIndex = -1;
		}
		callBack.onItemPressed(MenuBarSelectedIndex);
	}

	public void setOnClickMeunBarIndex() {
		HiddenMenuBarAndNotAlwaysShow();
		callBack.onMotionUpItemSelected(MenuBarSelectedIndex);
	}

	/**
	 * 当有鞋带被选中的时候
	 */
	public void showMenuBarAndAlwaysShow() {
		callBack.animation_buttom_menu_alwaysShow(true);
		callBack.animation_buttom_menu_show();
	}

	public void HiddenMenuBarAndNotAlwaysShow() {
		callBack.animation_buttom_menu_alwaysShow(false);
		callBack.animation_buttom_menu_hidden();
	}

	/**
	 * 
	 * @param event
	 * @param isLeftTile
	 *            是否是左边的带子
	 */
	private void caluateDegree(MotionEvent event, boolean isLeftTile) {

		if (isLeftTile) {
			float x = event.getX() - leftTile.padding_left;
			float y = event.getY() - leftTile.padding_top;
			float distance = (float) Math.sqrt(x * x + y * y);
			if (distance < tile_length - 30) {
				return;
			}
			leftTile.distance = distance;

			float endDegree = 0;
			if (y >= 0) {
				if (x != 0 && y != 0) {
					float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
					endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				}
			} else {
				float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
				endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				endDegree = +180 - endDegree;
			}
			leftTile.degree = endDegree;
		} else {
			float x = event.getX() - rightTile.padding_left;
			float y = event.getY() - rightTile.padding_top;
			float distance = (float) Math.sqrt(x * x + y * y);
			if (distance < tile_length - 30) {
				return;
			}
			rightTile.distance = distance;
			float endDegree = 0;
			if (y >= 0) {
				if (x != 0 && y != 0) {
					float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
					endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				}
			} else {
				float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
				endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				endDegree = +180 - endDegree;
			}
			rightTile.degree = endDegree;
		}

	}

	private void caluateDegree(float event_x, float event_y, boolean isLeftTile) {

		if (isLeftTile) {
			float x = event_x - leftTile.padding_left;
			float y = event_y - leftTile.padding_top;
			float distance = (float) Math.sqrt(x * x + y * y);
			if (distance < tile_length - 30) {
				return;
			}
			leftTile.distance = distance;

			float endDegree = 0;
			if (y >= 0) {
				if (x != 0 && y != 0) {
					float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
					endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				}
			} else {
				float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
				endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				endDegree = +180 - endDegree;
			}
			leftTile.degree = endDegree;
		} else {
			float x = event_x - rightTile.padding_left;
			float y = event_y - rightTile.padding_top;
			float distance = (float) Math.sqrt(x * x + y * y);
			if (distance < tile_length - 30) {
				return;
			}
			rightTile.distance = distance;
			float endDegree = 0;
			if (y >= 0) {
				if (x != 0 && y != 0) {
					float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
					endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				}
			} else {
				float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
				endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
				endDegree = +180 - endDegree;
			}
			rightTile.degree = endDegree;
		}

	}
	
	private class Tile_item {
		/*** 角度 */

		float degree = 0;
		/*** 长度 */
		float distance = tile_length;

		/*** 是否被选中 */
		boolean isTouched = false;

		/*** 按下去的索引 */
		int Touched_pointer_id = -1;
		float padding_left = 0;
		float padding_top = 0;

		int hole_top = 0;
		int hole_left = 0;

		public void onDraw(Canvas canvas) {
			canvas.save();
			canvas.translate(padding_left, padding_top);

			Paint p = new Paint();
			hole_left = (int) (-tile_hole.getIntrinsicWidth() / 2);
			hole_top = (int) (-tile_hole.getIntrinsicHeight() / 2);
			int widht = hole_left + tile_hole.getIntrinsicWidth();
			int hegiht = hole_top + tile_hole.getIntrinsicHeight();

			tile_hole.setBounds(hole_left, hole_top, widht, hegiht);

			tile_hole_cover.setBounds(hole_left, hole_top, widht, hegiht);
			canvas.rotate(-degree, 0, 0);

			tile_hole.draw(canvas);

			canvas.save();
			int drawTile_length = (int) (-tile_long.getHeight() + distance);
			canvas.clipRect(new Rect(-tile_long.getWidth() / 2, 0, tile_long
					.getWidth(), (int) distance));
			canvas.drawBitmap(tile_long, -tile_long.getWidth() / 2,
					-tile_long.getHeight() + distance, p);
			canvas.restore();

			tile_hole_cover.draw(canvas);
			canvas.restore();

		}

		/**
		 * 鞋带是否被选中
		 * 
		 * @param x
		 * @param y
		 * @return
		 */
		public boolean isDownInRect(int x, int y) {
			float center_hole_x = padding_left;
			float center_hole_y = padding_top;

			float dis = (center_hole_x - x) * (center_hole_x - x)
					+ (center_hole_y - y) * (center_hole_y - y);
			float scale = (tile_length / 2) * (tile_length / 2);

			if (dis < scale) {
				isTouched = true;
				return true;
			} else {
				isTouched = false;
				return false;
			}
		}

	}

	public class MyTransformation extends TranslateAnimation {

		public MyTransformation(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		/*** 关联带子 */
		private Tile_item mAssociateTile = null;

		public MyTransformation(float fromXDelta, float toXDelta,
				Tile_item associate) {
			super(fromXDelta, toXDelta, 0, 0);
			this.mAssociateTile = associate;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);

			Matrix matrix = t.getMatrix();
			float[] values = new float[9];
			matrix.getValues(values);
			/*** 左边的转动 */
			if (mAssociateTile == leftTile) {
				leftTile.distance = leftTile.distance - values[2];
				if (leftTile.distance < tile_length) {
					leftTile.distance = tile_length;
					ani_tran_left.clearAnimation();
					mTranslate_left = null;
					leftTile.isTouched = false;
				}
			}
			if (mAssociateTile == rightTile) {
				rightTile.distance = rightTile.distance - values[2];
				if (rightTile.distance < tile_length) {
					rightTile.distance = tile_length;
					ani_tran_rigth.clearAnimation();
					mTranslate_right = null;
					rightTile.isTouched = false;
				}
			}
		}
	}

	public class MyRotateAnimation extends RotateAnimation {
		float mFromDegrees = 0;
		float mToDegrees = 0;

		public MyRotateAnimation(float fromDegrees, float toDegrees) {
			super(fromDegrees, toDegrees);
			this.mFromDegrees = fromDegrees;
			this.mToDegrees = toDegrees;

			if (leftTile.distance == tile_length)
				leftTile.degree = mFromDegrees;
			if (rightTile.distance == tile_length)
				rightTile.degree = mFromDegrees;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			float degrees = mFromDegrees
					+ ((mToDegrees - mFromDegrees) * interpolatedTime);

			if (leftTile.distance == tile_length)
				leftTile.degree = degrees;
			if (rightTile.distance == tile_length)
				rightTile.degree = degrees;
		}
	}

	private float startDegree = 90;
	private float endDegree = 0;

	public static final int mess_id_rotate = 1;
	public static final int mess_id_tile = 2;
	private static SensorManager sensorManager;
	private static int delay_time = 500;
	private static SensorEventListener lsn;
	private static Sensor sensor;

	private void initialSeneor() {
		sensorManager = (SensorManager) getContext().getSystemService(
				getContext().SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {

				float x = e.values[SensorManager.DATA_X];
				float y = e.values[SensorManager.DATA_Y];

				if (Math.abs(x) < 0.2 && Math.abs(y) < 0.2) {
					startDegree = endDegree = 0;
				} else {
					startDegree = endDegree;

					if (y >= 0) {
						if (x != 0 && y != 0) {
							float d_sin = (float) (x / (Math
									.sqrt(x * x + y * y)));
							endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
						}
					} else {
						float d_sin = (float) (x / (Math.sqrt(x * x + y * y)));
						endDegree = (float) (Math.asin(d_sin) * (180 / Math.PI));
						endDegree = +180 - endDegree;
					}
					if (Math.abs(endDegree - startDegree) > 1) {
						Message message = Message.obtain();
						message.what = mess_id_tile;
						hander.sendMessageDelayed(message, delay_time);
					}
				}

			}

			public void onAccuracyChanged(Sensor s, int accuracy) {
			}
		};
		SharedPreferences mPerferences = getContext().getSharedPreferences(
				"com.jiubang.tile_preferences", Context.MODE_PRIVATE);

		boolean isSensor = mPerferences.getBoolean("sensor_checkBox", true);

		if (isSensor == true)
			MyTileView.sensorRegisterListener();
		else
			MyTileView.sensorUnregisterListener();

	}
	/**
	 * 注册传感器监听,开启重力感应
	 */

	public static void sensorRegisterListener() {
		sensorManager.registerListener(lsn, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		Tile.isRegister = true;
	}

	/**
	 * 注销传感器监听,关闭重力感应
	 */
	public static void sensorUnregisterListener() {
		sensorManager.unregisterListener(lsn);// 注消所有传感器监听
		Tile.isRegister = false;
	}

	private Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (mess_id_tile == msg.what) {
				startRotateAnimation(startDegree, endDegree);
			}
		};
	};

	public void startRotateAnimation(float fromDegrees, float toDegrees) {
		mMyRotate = new MyRotateAnimation(-fromDegrees, -toDegrees);
		mMyRotate.setDuration(1000);

		ani_rotate.setAnimation(mMyRotate);
		ani_rotate.startAnimation(mMyRotate);
	}

	private class MyAnimationListener implements AnimationListener {
		@Override
		public void onAnimationEnd(Animation animation) {
			if (animation == mTranslate_left) {
				leftTile.Touched_pointer_id = -1;
			}
			if (animation == mTranslate_right) {
				rightTile.Touched_pointer_id = -1;
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationStart(Animation animation) {

		}
	}

}
