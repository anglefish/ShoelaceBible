package com.jiubang.tile;

import com.jiubang.util.MyTileView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.KeyEvent;

import com.jiubang.intface.IMyDialogListener;

public class Setting extends PreferenceActivity {
    /** Called when the activity is first created. */
	static String sensor_checkBox="sensor_checkBox";
	String background="background";
	int REQUEST_BACKGROUND=1;
	int backgroundPosition=0;
	public static boolean isSensro=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       addPreferencesFromResource(R.xml.setting);
    }
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference)
	{
		// TODO Auto-generated method stub
		String key = preference.getKey();

		if (sensor_checkBox.equals(key)) {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);   
			Boolean isOpenSensor = settings.getBoolean(sensor_checkBox, true);  
            if(isOpenSensor==true)
            	MyTileView.sensorRegisterListener();
            else{
            	MyTileView.sensorUnregisterListener();
            }
		} 
		if (background.equals(key)) {
			changBackgroundDialog dialog = new changBackgroundDialog(Setting.this, new DialogListener(),Setting.this);
			dialog.show();
		} 
		
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
    public static boolean get(Context context) {
    	return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(sensor_checkBox, isSensro);
    }
    
   public boolean onKeyDown(int keyCode,KeyEvent event){
    	 if(keyCode == KeyEvent.KEYCODE_BACK){
    		 setResult(RESULT_OK);
            }
    	  return super.onKeyDown(keyCode, event);
    }
    
    class DialogListener implements IMyDialogListener{
    	public void onOkClick(int position) {
    		// TODO Auto-generated method stub
    		backgroundPosition=position;
    		ImageOptionPreference.ChangeGamePic(backgroundPosition);
    		SharedPreferences mPerferences = PreferenceManager   
	        .getDefaultSharedPreferences(Setting.this); 
			SharedPreferences.Editor mEditor = mPerferences.edit();  
			mEditor.putInt("background", backgroundPosition);   
			mEditor.commit();   
    		
    	}
    }
}

