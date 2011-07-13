package com.jiubang.tile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jiubang.intface.IImageViewChang;
import com.jiubang.intface.ITileMotionCallBack;
import com.jiubang.util.MyCustomeView;
import com.jiubang.util.MyFrameLayout;
import com.jiubang.util.MyTileView;
import com.jiubang.util.Rotate3d;

public class Tile extends Activity implements IImageViewChang,ITileMotionCallBack {

	View button_buttom_center_detail;
	ImageView button_buttom_center=null;
	boolean isShow_button_buttom_center_detail=false;
	ImageView button_setting;
	boolean isBegin=true;
	ImageView button_about;
	ImageView button_more;
	ImageView button_full_down;
    View backgroundView;
    ImageView image_bg1;
    ImageView image_bg2;
    ImageView image_bg3;
    ImageView image_bg4;
	
	/** 右边*/
    MyFrameLayout scrollContent = null;

	public ScrollView scrollView = null;
	
	
	/*** 下边菜单栏目*/
	public View button_menu=null;
	/**查看鞋带细节*/
	ImageView button_tile = null;
	/**
	 * 菜单栏的转换键
	 */
	ImageView button_switch = null;
	
	public static final int mess_id_rotate = 1;
	public static final int mess_id_tile = 2;
	public static final int mess_id_button_menu=3;
	private myOnclick onclick = null;
	private myOntouch ontouch = null;

	/**
	 * 是否显示鞋子
	 */
	private boolean isShowShoes = true;
	/**
	 * 是否注册监听器
	 */
	public static boolean isRegister=false;
	/**
	 * 存储每个组建y的坐标
	 */
	public static float[] scroll_item_y = new float[36];
	public static final int[] tile_pic = { 
			R.drawable.criss2,
			R.drawable.doublebacklacing2, 
			R.drawable.ladderlacin2,
			R.drawable.sawtoothlacing2,
			R.drawable.spiderweblacing2, 
			R.drawable.straighteasylacing2,
			R.drawable.straighteuropeanlacing2, 
			R.drawable.straightbarlacing2,
			R.drawable.traintracktacing2,
			R.drawable.zipperlacin2,
			
			R.drawable.overunder2,
			R.drawable.hiking2, 
			R.drawable.shoeshop2,
			R.drawable.displayshoe2,
			R.drawable.bowtie2, 
			R.drawable.army2,
			R.drawable.doublehelix2, 
			R.drawable.doublecross2,
			R.drawable.hash2,
			R.drawable.lattice2,
			R.drawable.riddingboot2,
			R.drawable.onehand2, 
			R.drawable.segmented2,
			R.drawable.knottedsegment2,
			R.drawable.hiddenknot2, 
			R.drawable.loopback2,
			R.drawable.knotted2, 
			R.drawable.twistie2,
			R.drawable.roman2,
			R.drawable.hexagram2,
			R.drawable.pentagram2,
			R.drawable.asterisk2, 
			R.drawable.starburst2,
			R.drawable.supernova2,
			R.drawable.footbag2, 
			R.drawable.lock2
	};

	public static final int[] tile_method = { 
			R.drawable.criss,
			R.drawable.doublebacklacing,
			R.drawable.ladderlacin,
			R.drawable.sawtoothlacing,
			R.drawable.spiderweblacing, 
			R.drawable.straighteasylacing,
			R.drawable.straighteuropeanlacing, 
			R.drawable.straightbarlacing, 
			R.drawable.traintracktacing,
			R.drawable.zipperlacin,
			
			R.drawable.overunder,
			R.drawable.hiking,
			R.drawable.shoeshop,
			R.drawable.displayshoe,
			R.drawable.bowtie, 
			R.drawable.army,
			R.drawable.doublehelix, 
			R.drawable.doublecross, 
			R.drawable.hash,
			R.drawable.lattice ,
			R.drawable.riddingboot,
			R.drawable.onehand,
			R.drawable.segmented,
			R.drawable.knottedsegment,
			R.drawable.hiddenknot, 
			R.drawable.loopback,
			R.drawable.knotted, 
			R.drawable.twistie, 
			R.drawable.roman,
			R.drawable.hexagram ,
			R.drawable.pentagram,
			R.drawable.asterisk,
			R.drawable.starburst,
			R.drawable.supernova,
			R.drawable.footbag, 
			R.drawable.lock
	};

	public static final int[] tile_pic_big_show = { 
			R.drawable.criss_big_1,
			R.drawable.doublebacklacing_big_1, 
			R.drawable.ladderlacin_big_1,
			R.drawable.sawtooth_lacing_big_1,
			R.drawable.spiderweblacing_big_1,
			R.drawable.straighteasylacing_big_1,
			R.drawable.straighteuropeanlacing_big_1,
			R.drawable.straightbarlacing_big_1,
			R.drawable.traintracktacing_big_1, 
			R.drawable.zipperlacin_big_1,
			
			R.drawable.overunder_big_1,
			R.drawable.hiking_big_1, 
			R.drawable.shoeshop_big_1,
			R.drawable.displayshoe_big_1,
			R.drawable.bowtie_big_1,
			R.drawable.army_big_1,
			R.drawable.doublehelix_big_1,
			R.drawable.doublecross_big_1,
			R.drawable.hash_big_1, 
			R.drawable.lattice_big_1,
			R.drawable.riddingboot_big_1,
			R.drawable.onehand_big_1, 
			R.drawable.segmented_big_1,
			R.drawable.knottedsegment_big_1,
			R.drawable.hiddenknot_big_1,
			R.drawable.loopback_big_1,
			R.drawable.knotted_big_1,
			R.drawable.twistie_big_1,
			R.drawable.roman_big_1, 
			R.drawable.hexagram_big_1,
			R.drawable.pentagram_big_1,
			R.drawable.asterisk_big_1, 
			R.drawable.starburst_big_1,
			R.drawable.supernova_big_1,
			R.drawable.footbag_big_1,
			R.drawable.lock_big_1

	};

	public static final int[] tile_method_big = {
			R.drawable.criss_big_2,
			R.drawable.doublebacklacing_big_2,
			R.drawable.ladderlacin_big_2,
			R.drawable.sawtooth_lacing_big_2,
			R.drawable.spiderweblacing_big_2,
			R.drawable.straighteasylacing_big_2,
			R.drawable.straighteuropeanlacing_big_2,
			R.drawable.straightbarlacing_big_2,
			R.drawable.traintracktacing_big_2,
			R.drawable.zipperlacin_big_2,
			
			R.drawable.overunder_big_2,
			R.drawable.hiking_big_2,
			R.drawable.shoeshop_big_2,
			R.drawable.displayshoe_big_2,
			R.drawable.bowtie_big_2,
			R.drawable.army_big_2,
			R.drawable.doublehelix_big_2,
			R.drawable.doublecross_big_2,
			R.drawable.hash_big_2,
			R.drawable.lattice_big_2,
			R.drawable.riddingboot_big_2,
			R.drawable.onehand_big_2,
			R.drawable.segmented_big_2,
			R.drawable.knottedsegment_big_2,
			R.drawable.hiddenknot_big_2,
			R.drawable.loopback_big_2,
			R.drawable.knotted_big_2,
			R.drawable.twistie_big_2,
			R.drawable.roman_big_2,
			R.drawable.hexagram_big_2,
			R.drawable.pentagram_big_2,
			R.drawable.asterisk_big_2,
			R.drawable.starburst_big_2,
			R.drawable.supernova_big_2,
			R.drawable.footbag_big_2,
			R.drawable.lock_big_2
	};

	public static final int[] desc_res = { R.string.criss,
			R.string.doublebacklacing, R.string.ladderlacin,
		    R.string.sawtoothlacing,
			R.string.spiderweblacing, R.string.straighteasylacing,
			R.string.straighteuropeanlacing, 
			R.string.straightbarlacing,
			R.string.traintracktacing,
			R.string.zipperlacin ,
			R.string.overunder,R.string.hiking,
			R.string.shoeshop,R.string.displayshoe,
			R.string.displayshoe,R.string.bowtie,
			R.string.army,R.string.doublehelix,
			R.string.hash,R.string.lattice,
			R.string.riddingboot,R.string.onehand,
			R.string.segmented,R.string.knottedsegment,
			R.string.hiddenknot,R.string.loopback,
			R.string.knotted,R.string.twistie,
			R.string.roman,R.string.hexagram,
			R.string.pentagram,R.string.asterisk,
			R.string.starburst,R.string.supernova,
			R.string.footbag,R.string.lock};

	public  Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg)
		{
			if (mess_id_rotate == msg.what)
			{
				rotate3d(msg.arg1, msg.arg2);
			}
			////else if(mess_id_button_menu_hidden==msg.what)
			///{
				////animation_buttom_menu_hidden();
			////}
		};
	};
	/**
	 * 选择的索引
	 */
	private int currentSelectedIndex = 0;
	public static  float scaledDensity = 0;

	private TextView desc = null;
	/**显示大图*/
	private ImageView show_big = null;
	
	/*** 顶部鞋带洞tileview*/
	private MyTileView myTile_view=null;
	/*** 屏幕宽度*/
	public static int SCREEN_WIDTH=320;
	/*** 屏幕高度*/
	public static int SCREEN_HEIGHT=480;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		scaledDensity = metrics.scaledDensity;
		 
		SCREEN_WIDTH=getWindowManager().getDefaultDisplay().getWidth();
		SCREEN_HEIGHT=getWindowManager().getDefaultDisplay().getHeight(); 
		
		setContentView(R.layout.tile);

		onclick = new myOnclick();
		ontouch = new myOntouch();
		
		
		button_buttom_center_detail=(View) findViewById(R.id.button_buttom_center_detail);
		button_buttom_center=(ImageView) findViewById(R.id.button_buttom_center);
		button_buttom_center.setOnClickListener(onclick);
		button_setting=(ImageView) findViewById(R.id.button_setting);
		button_setting.setOnClickListener(onclick);
		button_about=(ImageView) findViewById(R.id.button_about);
		button_about.setOnClickListener(onclick);
		button_more=(ImageView) findViewById(R.id.button_more);
		button_more.setOnClickListener(onclick);
		button_full_down=(ImageView) findViewById(R.id.button_full_down);
		button_full_down.setOnClickListener(onclick);
		
		button_setting.setClickable(false);
		button_full_down.setClickable(false);
		button_more.setClickable(false);
		button_about.setClickable(false);
		
		ani_button_buttom_center = new TranslateAnimation(0,-320* Tile.scaledDensity, 0,0);
		ani_button_buttom_center.setAnimationListener(myAnimationListener);
		ani_button_buttom_center.setDuration(tran_ani_time);
		ani_button_buttom_center.setFillAfter(true);
		button_buttom_center_detail.startAnimation(ani_button_buttom_center);
		
		show_big = (ImageView) findViewById(R.id.show);
		show_big.setImageDrawable(getResources().getDrawable(tile_method_big[0]));
		show_big.setOnClickListener(onclick);
		
		desc = (TextView) findViewById(R.id.desc);
		desc.setText(desc_res[0]);

		scrollView = (ScrollView) findViewById(R.id.scroll);
		myTile_view=(MyTileView)findViewById(R.id.mytileview);
		
		
		View ani_rotate=findViewById(R.id.ani_view_rotate);
		View ani_tran_left=findViewById(R.id.ani_view_tran_left);
		View ani_tran_right=findViewById(R.id.ani_view_tran_right);
		
		
		myTile_view.ani_rotate=ani_rotate;
		myTile_view.ani_tran_left=ani_tran_left;
		myTile_view.ani_tran_rigth=ani_tran_right;
		myTile_view.setCallBack(this);
		scrollContent = (MyFrameLayout) findViewById(R.id.scroll_content);
		scrollContent.setOnTouchListener(ontouch);
		
		initialMenuBar();
		initialScorllImage();
		caculateMenuBarPosition();
		SharedPreferences mPerferences = this.getSharedPreferences(
				"com.jiubang.tile_preferences", Context.MODE_PRIVATE);
		int backgroundPosition = mPerferences.getInt("background", R.drawable.bg_1);
		FrameLayout tile_background=(FrameLayout) findViewById(R.id.tile_background);
		tile_background.setBackgroundDrawable(this.getResources().getDrawable(backgroundPosition));
	}
	
	
	public void initialMenuBar()
	{
		button_menu=findViewById(R.id.buttom_menu);
		button_tile = (ImageView) button_menu.findViewById(R.id.button_tile);
		button_switch = (ImageView) button_menu.findViewById(R.id.button_switch);
		button_tile.setOnClickListener(onclick);
		button_switch.setOnClickListener(onclick);
	}
	
	
	public static float MenuBar_first_x=0;
	public static float MenuBar_second_x=0;
	public static float MenuBar_third_x=0;
	public static float MenuBar_y=0;
	public static float MenuBar_Item_Height=78;
	public static float MenuBar_Item_distance=40*40;
	public void caculateMenuBarPosition()
	{
		MenuBar_y = SCREEN_HEIGHT - MenuBar_Item_Height * scaledDensity / 2;
		float total_width = SCREEN_WIDTH - 60 * scaledDensity;
		float total_width_item = total_width / 6;
		MenuBar_first_x = 30 * scaledDensity + total_width_item;
		MenuBar_second_x=SCREEN_WIDTH-MenuBar_first_x;
}
	
	
	
	
	/**
	 * 设置滚动图片的每个图片滚动情况
	 */
	public void initialScorllImage()
	{
		for (int i = 0; i < scrollContent.getChildCount(); i++)
		{
			MyCustomeView imageView = (MyCustomeView) scrollContent.getChildAt(i);
		    imageView.drawable_one = tile_pic[i];
			imageView.drawable_two = tile_method[i];
			imageView.setImageDrawable(getResources().getDrawable(imageView.drawable_one));
			if (i == 0)
			{
				imageView.setSelected(true);
			}
		}
	}

	/**
	 * 改变滚动图标中的索引的图片
	 */
	@Override
	public void changeImage(int index)
	{
	// TODO Auto-generated method stub

	}
	public void rotateScrollItem()
	{
		isShowShoes = !isShowShoes;
		int message_arg2=isShowShoes ? 1 : 2;
		///*
		int scrollViewHeight=scrollView.getHeight();
		int scrollViewY=scrollView.getScrollY();
		int scrollViewTopIndex=0;
		for (int i = 0; i < scroll_item_y.length; i++)
		{
			if (scrollViewY < scroll_item_y[i])
			{
				scrollViewTopIndex = i;
				break;
			}
		}
		int height=(int)(scroll_item_y[1]-scroll_item_y[0]);
		int rotate3Dcount=scrollViewHeight/height+1;
		int beforeindex=0;
		while(beforeindex<scrollViewTopIndex){
			MyCustomeView v = (MyCustomeView) scrollContent.getChildAt(beforeindex);
			v.showIndex = message_arg2;
			v.changeImageViewContentByShowIndex();
			if(beforeindex==currentSelectedIndex){
				changeBigImage_animation();
			}
			beforeindex++;
			
		}
		int after=scrollViewTopIndex+rotate3Dcount+1;
		while(after< scrollContent.getChildCount()){
			MyCustomeView v = (MyCustomeView) scrollContent.getChildAt(after);
			v.showIndex = message_arg2;
			v.changeImageViewContentByShowIndex();
			if(after==currentSelectedIndex){
				changeBigImage_animation();
			}
			after++;
		}
		int index=scrollViewTopIndex;
		for (int i = 0; i <=rotate3Dcount&&index<scrollContent.getChildCount(); i++)
		{
			Message message = Message.obtain();
			message.what = mess_id_rotate;
			message.arg1 =index ;
			message.arg2 =message_arg2;
			hander.sendMessageDelayed(message, i * 100);
			if(index==currentSelectedIndex){
				changeBigImage_animation();
			}
			index++;
		}
	}

	public void rotate3d(int RotateIndex, int showIndex)
	{
		if (scrollContent.getChildCount() > RotateIndex)
		{
			MyCustomeView v = (MyCustomeView) scrollContent.getChildAt(RotateIndex);
			v.showIndex = showIndex;
			Rotate3d rotate = new Rotate3d(-0, 180, 0, RotateIndex * 20, v.getWidth() / 2, v.getWidth() / 2, v);
			rotate.setDuration(1000);
			v.startAnimation(rotate);
		}
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
             showDialog(DIALOG2);
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getY()>(SCREEN_HEIGHT-button_menu.getHeight()))
		{
			if(!isShowButtom_Meun)
			{
				animation_buttom_menu_show();
			}
			return true;
		}else
		{
			return super.onTouchEvent(event);
		}
		
	}
	private class myOntouch implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if (event.getAction() == MotionEvent.ACTION_UP)
			{
				/**
				 * y的绝对坐标
				 */
				int abs_y = (int) event.getY();
				for (int i = 0; i < scroll_item_y.length; i++)
				{
					if (abs_y < scroll_item_y[i])
					{
						currentSelectedIndex = i;
						onScroll_itemSelected(i);
						break;
					}
				}

			}
			return true;
		}
	}

	private void onScroll_itemSelected(int index)
	{
		for (int i = 0; i < scrollContent.getChildCount(); i++)
		{
			MyCustomeView imageView = (MyCustomeView) scrollContent.getChildAt(i);
			imageView.setSelected(false);
		}

		MyCustomeView imageView = (MyCustomeView) scrollContent.getChildAt(index);
		imageView.setSelected(true);
		scrollContent.changeChildDrawOrder(index);
		scrollContent.invalidate();
		changeBigImage(index);
		changeDescWord(index);

	}

	/**
	 * 换大图
	 * 
	 * @param index
	 */
	private void changeBigImage(int index)
	{
		if (!isShowShoes)
		{
			show_big.setImageDrawable(getResources().getDrawable(tile_pic_big_show[index]));
		}
		else
		{
			show_big.setImageDrawable(getResources().getDrawable(tile_method_big[index]));
		}
	}
	
	private void changeBigImage_animation()
	{
		if(alpha==null)
		{
			alpha=new AlphaAnimation(1.0f, 0.0f);
			alpha.setAnimationListener(myAnimationListener);
			alpha.setDuration(alpha_time);
			show_big.startAnimation(alpha);
		}
	}
	
	private void changeBigImage()
	{
		if (isShowShoes==false)
		{
			show_big.setImageDrawable(getResources().getDrawable(tile_pic_big_show[currentSelectedIndex]));
		}
		else
		{
			show_big.setImageDrawable(getResources().getDrawable(tile_method_big[currentSelectedIndex]));
		}
		
	}
	private Animation alpha=null;
	private int alphaStep=0;
	private MyAnimatorListener myAnimationListener=new MyAnimatorListener();
	private static final int alpha_time=800;
	private class MyAnimatorListener implements AnimationListener
	{
		@Override
		public void onAnimationEnd(Animation animation)
		{
			if(animation==alpha)
			{
				alphaStep++;
				if(alphaStep==1)
				{
					alpha=new AlphaAnimation(0.0f, 1.0f);
					alpha.setAnimationListener(myAnimationListener);
					alpha.setDuration(alpha_time);
					show_big.startAnimation(alpha);
					changeBigImage();
				}else
				{
					alpha=null;
					alphaStep=0;
				}
			}
			if(animation==ani_button_buttom_center)
			{
				ani_button_buttom_center=null;
				if(isBegin==true){
					button_buttom_center_detail.setVisibility(View.VISIBLE);
					isBegin=false;
					}
			}
			
		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{
			
		}

		@Override
		public void onAnimationStart(Animation animation)
		{
			
		}
		
	}
	/**
	 * 换描述
	 * @param index
	 */
	private void changeDescWord(int index)
	{
		if (desc != null)
			desc.setText(desc_res[index]);
	}

	private class myOnclick implements OnClickListener {

		@Override
		public void onClick(View v)
		{
				if(v==button_setting){
					 startActivityForResult(new Intent(Tile.this, Setting.class), REQ_SYSTEM_SETTINGS);   
					
				}
				if(v==button_about){
					showDialog(DIALOG1);
				}
				if(v==button_more){
						Uri uri = Uri.parse("market://search?q=pub:\"A.O.I Dev Team\"");
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(it);
				}
				if(v==button_full_down){
					Uri uri = Uri.parse("http://www.aoidev.com");
					Intent it = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(it);
					}
				if(v==show_big){
					go2detail();
					}

					if (v == button_switch)
					{
						rotateScrollItem();
					}
					if (v == button_tile)
					{
						go2detail();
					}
					if(v==button_buttom_center){
						onClickbutton_buttom_center();
					}
		}
	}

	public void go2detail()
	{
		Intent in = new Intent(this, TileDetail.class);
		in.putExtra(TileDetail.show_index, currentSelectedIndex);
		startActivity(in);
	}
	
	Animation ani_button_buttom_center= null;
	private boolean isShowButtom_Meun=true;
	private static int tran_ani_time=800;

	public void animation_buttom_menu_hidden()
	{
	}
	
	public void onButtonPressed(int index)
	{
		 
	}
   

	@Override
	public void onItemPressed(int index)
	{
		
		if(isShowButtom_Meun)
		{
			 button_tile.setPressed(false);
			 button_switch.setPressed(false);
			 if(index==0)
			 {
				 button_tile.setPressed(true);
			 }else if(index==1)
			 {
				 button_switch.setPressed(true);
			 }
		}else
		{
			animation_buttom_menu_show();
		}
		
	}


	@Override
	public void onMotionUpItemSelected(int index)
	{
		
			if(index==0)
			 {
				go2detail();
			 }else if(index==1)
			 {
				rotateScrollItem();
			 }else if(index==2)
			 {
			 }
		 button_tile.setPressed(false);
		 button_switch.setPressed(false);
	}


	/**
	 * 是否一直显示底部菜单栏
	 */
	private boolean isAlwaysShowMeun=false;
	@Override
	public void animation_buttom_menu_alwaysShow(boolean alwaysShow)
	{
		// TODO Auto-generated method stub
		this.isAlwaysShowMeun=alwaysShow;
	}
	   private static final int REQ_SYSTEM_SETTINGS = 0;
	   
	   protected void onActivityResult(int requestCode,int resultCode,Intent data){
	 	  if(requestCode == REQ_SYSTEM_SETTINGS)   
	        {
	         if(resultCode==RESULT_CANCELED){
	    			
	    		}
	    		else if(resultCode==RESULT_OK){
	    			SharedPreferences mPerferences = this.getSharedPreferences(
	    					"com.jiubang.tile_preferences", Context.MODE_PRIVATE);
	    			int backgroundPosition = mPerferences.getInt("background", R.drawable.bg_1);
	    			FrameLayout tile_background=(FrameLayout) findViewById(R.id.tile_background);
	    			tile_background.setBackgroundDrawable(this.getResources().getDrawable(backgroundPosition));
	    		}
	        }

	}
	   
	   public void onClickbutton_buttom_center()
		{
			if (ani_button_buttom_center == null)
			{
				isShow_button_buttom_center_detail = !isShow_button_buttom_center_detail;
				if (isShow_button_buttom_center_detail)
				{
					button_setting.setClickable(true);
					button_full_down.setClickable(true);
					button_more.setClickable(true);
					button_about.setClickable(true);
					///显示菜单
					ani_button_buttom_center = new TranslateAnimation(320* Tile.scaledDensity,0,0, 0);
					ani_button_buttom_center.setAnimationListener(myAnimationListener);
					ani_button_buttom_center.setDuration(300);
					ani_button_buttom_center.setFillAfter(true);
					button_buttom_center_detail.startAnimation(ani_button_buttom_center);
				}
				else
				{
					button_setting.setClickable(false);
					button_full_down.setClickable(false);
					button_more.setClickable(false);
					button_about.setClickable(false);
					
					ani_button_buttom_center = new TranslateAnimation(0,-320* Tile.scaledDensity, 0,0);
					ani_button_buttom_center.setAnimationListener(myAnimationListener);
					ani_button_buttom_center.setDuration(300);
					ani_button_buttom_center.setFillAfter(true);
					button_buttom_center_detail.startAnimation(ani_button_buttom_center);
				}
			}
		}
	   
	   private static final int DIALOG1 = 1;
	   private static final int DIALOG2 = 2;
		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case DIALOG1:
				return buildDialog1(Tile.this);
			case DIALOG2:
				return buildDialog2(Tile.this);

			}
			return null;
		}
	   private Dialog buildDialog1(Context context) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("About");
			builder.setMessage(R.string.about_text);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							
						}
					});
			return builder.create();
		}
	   private Dialog buildDialog2(Context context) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			
			builder.setTitle("Exit");
			builder.setMessage("Are you sure exit ?");
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							finish();
						}
					});
			builder.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			});
			return builder.create();
		}


	@Override
	public void animation_buttom_menu_show() {
		// TODO Auto-generated method stub
		
	}
}
