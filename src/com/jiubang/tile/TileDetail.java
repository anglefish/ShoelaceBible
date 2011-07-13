package com.jiubang.tile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class TileDetail extends Activity implements OnGestureListener {

	public static final int[] tile_pic_big_show = { R.drawable.criss_big,
			R.drawable.doublebacklacing_big, R.drawable.ladderlacin_big,
			R.drawable.sawtoothlacing_big, R.drawable.spiderweblacing_big,
			R.drawable.straighteasylacing_big,
			R.drawable.straighteuropeanlacing_big,
			R.drawable.straightbarlacing_big, R.drawable.traintracktacing_big,
			R.drawable.zipperlacin_big ,
			
			R.drawable.overunder_big, R.drawable.hiking_big,
			R.drawable.shoeshop_big, R.drawable.displayshoe_big,
			R.drawable.bowtie_big,
			R.drawable.army_big,
			R.drawable.doublehelix_big, R.drawable.doublecross_big,
			R.drawable.hash_big ,
			R.drawable.lattice_big, R.drawable.riddingboot_big,
			R.drawable.onehand_big, R.drawable.segmented_big,
			R.drawable.knottedsegment_big,
			R.drawable.hiddenknot_big,
			R.drawable.loopback_big, R.drawable.knotted_big,
			R.drawable.twistie_big ,
			R.drawable.roman_big, R.drawable.hexagram_big,
			R.drawable.pentagram_big, R.drawable.asterisk_big,
			R.drawable.starburst_big,
			R.drawable.supernova_big,
			R.drawable.footbag_big, R.drawable.lock_big
	};
	public static final int[] tile_pic_show_use = { R.drawable.criss_use,
			R.drawable.doublebacklacing_use, R.drawable.ladderlacin_use,
			R.drawable.sawtoothlacing_use, R.drawable.spiderweblacing_use,
			R.drawable.straighteasylacing_use,
			R.drawable.straighteuropeanlacing_use,
			R.drawable.straightbarlacing_use, R.drawable.traintracktacing_use,
			R.drawable.zipperlacin_use ,
			
			R.drawable.overunder_use, R.drawable.hiking_use,
			R.drawable.shoeshop_use, R.drawable.displayshoe_use,
			R.drawable.bowtie_use,
			R.drawable.army_use,
			R.drawable.doublehelix_use, R.drawable.doublecross_use,
			R.drawable.hash_use ,
			R.drawable.lattice_use, R.drawable.riddingboot_use,
			R.drawable.onehand_use, R.drawable.segmented_use,
			R.drawable.knottedsegment_use,
			R.drawable.hiddenknot_use,
			R.drawable.loopback_use, R.drawable.knotted_use,
			R.drawable.twistie_use ,
			R.drawable.roman_use, R.drawable.hexagram_use,
			R.drawable.pentagram_use, R.drawable.asterisk_use,
			R.drawable.starburst_use,
			R.drawable.supernova_use,
			R.drawable.footbag_use, R.drawable.lock_use
	
	};
	
	public static final int[] tile_name={
		R.string.criss_name, R.string.doublebacklacing_name,
		R.string.ladderlacin_name, R.string.sawtoothlacing_name,
		R.string.spiderweblacing_name, R.string.straighteasylacing_name,
		R.string.straighteuropeanlacing_name,
		R.string.straightbarlacing_name, R.string.traintracktacing_name,
		R.string.zipperlacin_name,
		R.string.overunder_name,R.string.hiking_name,
		R.string.shoeshop_name,R.string.displayshoe_name,
		R.string.bowtie_name,R.string.army_name,
		R.string.doublehelix_name,R.string.doublecross_name,
		R.string.hash_name,R.string.lattice_name,
		R.string.riddingboot_name,R.string.onehand_name,
		R.string.segmented_name,R.string.knottedsegment_name,
		R.string.hiddenknot_name,R.string.loopback_name,
		R.string.knotted_name,R.string.twistie_name,
		R.string.roman_name,R.string.hexagram_name,
		R.string.pentagram_name,R.string.asterisk_name,
		R.string.starburst_name,R.string.supernova_name,
		R.string.footbag_name,R.string.lock_name
	};
	/**
	 * 显示细节图
	 */
	ImageView big_show = null;
	ImageView show_use = null;
	TextView show_desc = null;
	ImageView left = null;
	ImageView right = null;
	TextView buttom_center=null;
	/**
	 * 显示按钮细节的
	 */
	ImageView title = null;

	private MyOnclick onclick = null;
	private MyOnTouch ontouch = null;
	public static String show_index = "showindex";

	public MyanimationListener animationListener = null;

	Animation ani_buttom = null;
	Animation ani_top = null;
	/**
	 * 底部的两个箭头
	 */
	View buttom_button = null;
	
	View buttom = null;
	View buttom_bg = null;
	private boolean isShowButtomBar = true;

	ViewFlipper fliper = null;
	private GestureDetector mGestureDetector = null;
	
	
	
	View top_button = null;
	View top_left=null;
	View top_right=null;
	TextView top_name=null;
	
	private Handler myHander = new Handler() {

		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 1)
			{
				animation_buttom_bar();
			}
		}

	};
	int index =0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detail);

		index = getIntent().getIntExtra(show_index, 0);
		big_show = (ImageView) findViewById(R.id.detail_big);
		big_show.setImageDrawable(getResources().getDrawable(tile_pic_big_show[index]));

		show_use = (ImageView) findViewById(R.id.detail_use);
		show_use.setImageDrawable(getResources().getDrawable(tile_pic_show_use[index]));
		top_name=(TextView)findViewById(R.id.top_name);
		top_name.setText(tile_name[index]);
		
		initialButton();
		animationListener = new MyanimationListener();
		mGestureDetector = new GestureDetector(this);

		myHander.sendMessageDelayed(myHander.obtainMessage(1), 1000);
		
	}

	private void initialButton()
	{

		fliper = (ViewFlipper) findViewById(R.id.myFliper);
		
		buttom_center=(TextView) findViewById(R.id.splash_center);

		buttom_button = findViewById(R.id.buttom_button);
		buttom = (View) findViewById(R.id.buttom);

		title = (ImageView) findViewById(R.id.title);
		left = (ImageView) findViewById(R.id.splash_left);
		right = (ImageView) findViewById(R.id.splash_right);
		onclick = new MyOnclick();
		left.setOnClickListener(onclick);
		right.setOnClickListener(onclick);
		title.setOnTouchListener(ontouch);
		buttom.setOnTouchListener(ontouch);
		
		
		top_button=findViewById(R.id.top);
		top_left=findViewById(R.id.top_left);
		top_right=findViewById(R.id.top_right);
		top_left.setOnClickListener(onclick);
		top_right.setOnClickListener(onclick);
		
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1&&resultCode==-1)
		{
			this.finish();
		}
	
	}
	private static final int titile_move_detal = 45;

	private class MyOnTouch extends SimpleOnGestureListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if (isShowButtomBar)
			{
				if (v == title)
				{
					animation_buttom_bar();
				}
			}
			else
			{
				animation_buttom_bar();
			}

			return true;
		}

	}

	private class MyOnclick implements OnClickListener {

		@Override
		public void onClick(View v)
		{
			if (isShowButtomBar)
			{
				if (v == left)
				{
					fliper.setInAnimation(AnimationUtils.loadAnimation(
							TileDetail.this, R.anim.push_right_in));
					fliper.setOutAnimation(AnimationUtils.loadAnimation(
							TileDetail.this, R.anim.push_right_out));
					fliper.showPrevious();
					if(buttom_center.getText().equals("1/2"))
						buttom_center.setText("2/2");
					else
						buttom_center.setText("1/2");

				}
				else if (v == right)
				{
					fliper.setInAnimation(AnimationUtils.loadAnimation(TileDetail.this, R.anim.push_left_in));
					fliper.setOutAnimation(AnimationUtils.loadAnimation(TileDetail.this, R.anim.push_left_out));
					fliper.showNext();
					if(buttom_center.getText().equals("1/2"))
						buttom_center.setText("2/2");
					else
						buttom_center.setText("1/2");
				}
				else if (v == title)
				{
					animation_buttom_bar();
				}else if(v==top_left)
				{
					finish();
				}else if(v==top_right)
				{
					
					Intent intent=new Intent(TileDetail.this, TileDetailHelp.class);
					intent.putExtra(TileDetailHelp.detail_help_index, index);
					intent.putExtra(TileDetailHelp.detail_help_title, tile_name[index]);
					TileDetail.this.startActivityForResult(intent, 1);
				}
			}
			else
			{
				animation_buttom_bar();
			}
		}

	}

	public void animation_buttom_bar()
	{
		if (ani_buttom == null)
		{
			if (!isShowButtomBar)
			{
				ani_buttom = new TranslateAnimation(0, 0, titile_move_detal* Tile.scaledDensity, 0);
				ani_buttom.setAnimationListener(animationListener);
				ani_buttom.setDuration(1000);
				ani_buttom.setFillAfter(true);
				buttom.startAnimation(ani_buttom);
				
				
				ani_top= new TranslateAnimation(0, 0, -titile_move_detal* Tile.scaledDensity, 0);
				ani_top.setAnimationListener(animationListener);
				ani_top.setDuration(1000);
				ani_top.setFillAfter(true);
				top_button.startAnimation(ani_top);
			}
			else
			{
				ani_buttom = new TranslateAnimation(0, 0, 0, titile_move_detal* Tile.scaledDensity);
				ani_buttom.setAnimationListener(animationListener);
				ani_buttom.setDuration(1000);
				ani_buttom.setFillAfter(true);
				buttom.startAnimation(ani_buttom);
				
				
				ani_top= new TranslateAnimation(0, 0, 0, -titile_move_detal* Tile.scaledDensity);
				ani_top.setAnimationListener(animationListener);
				ani_top.setDuration(1000);
				ani_top.setFillAfter(true);
				top_button.startAnimation(ani_top);
			}
			isShowButtomBar = !isShowButtomBar;
		}

	}

	class MyanimationListener implements AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation)
		{
			ani_buttom = null;
			if (!isShowButtomBar)
			{

			}

		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{}

		@Override
		public void onAnimationStart(Animation animation)
		{}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		// TODO Auto-generated method stub
		animation_buttom_bar();
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY)
	{
		animation_buttom_bar();
		
		final int FLING_MIN_DISTANCE=100;//X或者y轴上移动的距离(像素)         
		final int FLING_MIN_VELOCITY=200;//x或者y轴上的移动速度(像素/秒)        
		if((e1.getX()-e2.getX())>FLING_MIN_DISTANCE && Math.abs(velocityX)>FLING_MIN_VELOCITY)       
		{
			fliper.setInAnimation(AnimationUtils.loadAnimation(
					TileDetail.this, R.anim.push_left_in));
			fliper.setOutAnimation(AnimationUtils.loadAnimation(
					TileDetail.this, R.anim.push_left_out));
			fliper.showPrevious();
			if(buttom_center.getText().equals("1/2"))
				buttom_center.setText("2/2");
			else
				buttom_center.setText("1/2");
		}
		else if((e2.getX()-e1.getX())>FLING_MIN_DISTANCE && Math.abs(velocityX)>FLING_MIN_VELOCITY)          
	   {
			fliper.setInAnimation(AnimationUtils.loadAnimation(TileDetail.this, R.anim.push_right_in));
			fliper.setOutAnimation(AnimationUtils.loadAnimation(TileDetail.this, R.anim.push_right_out));
			fliper.showNext();
			if(buttom_center.getText().equals("1/2"))
				buttom_center.setText("2/2");
			else
				buttom_center.setText("1/2");
		}
		return false;   
	}

	@Override
	public void onLongPress(MotionEvent e)
	{
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY)
	{
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		return false;
	}
}
