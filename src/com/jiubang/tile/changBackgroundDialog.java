package com.jiubang.tile;

import com.jiubang.tile.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.jiubang.intface.IMyDialogListener;;

public class changBackgroundDialog extends Dialog implements OnClickListener{
	private Context context_activity;
	private IMyDialogListener listener;
		int confirmPosition;
		
		 ImageView image_bg1;
		 ImageView image_bg2;
		 ImageView image_bg3;
		 ImageView image_bg4;
		 private Integer[] mThumbIds={
		    		R.drawable.bg_1,R.drawable.bg_2,
		    		R.drawable.bg_3,R.drawable.bg_4
		    };

	public changBackgroundDialog(Context context, IMyDialogListener listener,Context context_activity) {
        super(context);
        this.listener = listener;
        this.context_activity=context_activity;
    }
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        confirmPosition=R.drawable.bg_1;
        setTitle("Select Background");
        setContentView(R.layout.select_background); 
        image_bg1=(ImageView) findViewById(R.id.image_bg1);
        image_bg2=(ImageView) findViewById(R.id.image_bg2);
        image_bg3=(ImageView) findViewById(R.id.image_bg3);
        image_bg4=(ImageView) findViewById(R.id.image_bg4);
        image_bg1.setOnClickListener(this);
        image_bg2.setOnClickListener(this);
        image_bg3.setOnClickListener(this);
        image_bg4.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
        case R.id.image_bg1:
        	listener.onOkClick(mThumbIds[0]);
             dismiss();
             break;
        case R.id.image_bg2:
        	listener.onOkClick(mThumbIds[1]);
             dismiss();
             break;
        case R.id.image_bg3:
        	listener.onOkClick(mThumbIds[2]);
             dismiss();
             break;
        case R.id.image_bg4:
        	listener.onOkClick(mThumbIds[3]);
             dismiss();
             break;
             }
		
		
	}

}

