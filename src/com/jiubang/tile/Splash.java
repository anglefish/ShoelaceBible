package com.jiubang.tile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Splash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		setContentView(R.layout.splash);
		ImageView image=(ImageView)findViewById(R.id.splash_img);
		Animation ai=new AlphaAnimation(1.0f, 1.0f);
		ai.setDuration(1000);
		ai.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation)
			{
			// TODO Auto-generated method stub
			
			}
			
			@Override
			public void onAnimationRepeat(Animation animation)
			{
			// TODO Auto-generated method stub
			
			}
			
			@Override
			public void onAnimationEnd(Animation animation)
			{
			// TODO Auto-generated method stub
				Intent in=new Intent(Splash.this,Tile.class);
				startActivity(in);
				Splash.this.finish();
			}
		});
		ai.setFillAfter(true);
		image.setAnimation(ai);
		image.startAnimation(ai);
	}
}
