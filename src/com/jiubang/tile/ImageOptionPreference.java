package com.jiubang.tile;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * 图片选项，用于设置图片和边框
 * @author Winter Lau
 */
public class ImageOptionPreference extends Preference {

	private PreferenceActivity parent;
	private static ImageView preview_img;
	
	public ImageOptionPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ImageOptionPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageOptionPreference(Context context) {
		super(context);
	}
	
	void setActivity(PreferenceActivity parent) {
		this.parent = parent;
	}
	
	@Override
	public boolean isPersistent() {
		return false;
	}

	/**
	 * 修改图片
	 * @param newImage
	 * @return
	 */
	public static void ChangeGamePic(int newImage ){
		preview_img.setImageResource(newImage);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		SharedPreferences mPerferences = getContext().getSharedPreferences(
				"com.jiubang.tile_preferences", Context.MODE_PRIVATE);
		int backgroundPosition = mPerferences.getInt("background", R.drawable.bg_1);
		preview_img = (ImageView)view.findViewById(R.id.pref_current_img);
		preview_img.setImageResource(backgroundPosition);
	}	

    @Override
    protected void onClick() {
        super.onClick();
        }

}