package com.jiubang.tile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TileDetailHelp extends Activity {
	public static final int[] detail_step={
		R.string.criss_detail, R.string.doublebacklacing_detail,
		R.string.ladderlacin_detail, R.string.sawtoothlacing_detail,
		R.string.spiderweblacing_detail, R.string.straighteasylacing_detail,
		R.string.straighteuropeanlacing_detail,
		R.string.straightbarlacing_detail, R.string.traintracktacing_detail,
		R.string.zipperlacin_detail,
		R.string.overunder_detail, R.string.hiking_detail,
		R.string.shoeshop_detail, R.string.displayshoe_detail,
		R.string.bowtie_detail, R.string.army_detail,
		R.string.doublehelix_detail, R.string.doublecross_detail,
		R.string.hash_detail, R.string.lattice_detail,
		R.string.riddingboot_detail, R.string.onehand_detail,
		R.string.segmented_detail, R.string.knottedsegment_detail,
		R.string.hiddenknot_detail, R.string.loopback_detail,
		R.string.knotted_detail, R.string.twistie_detail,
		R.string.roman_detail, R.string.hexagram_detail,
		R.string.pentagram_detail, R.string.asterisk_detail,
		R.string.starburst_detail, R.string.supernova_detail,
		R.string.footbag_detail, R.string.lock_detail
	};
	public static final String detail_help_index="detail_help_index";
	public static final String detail_help_title="detail_help_title";
	View top_left=null;
	View top_right=null;
	TextView top_name=null;
	TextView detail_help_text=null;
	private MyOnclick onclick = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detailhelp);
		int showIndex=getIntent().getIntExtra(detail_help_index, 0);
		top_name=(TextView)findViewById(R.id.top_name);
		top_name.setText(TileDetail.tile_name[showIndex]);
		detail_help_text=(TextView)findViewById(R.id.detail_help_text);
		detail_help_text.setText(detail_step[showIndex]);
		
		
		onclick=new MyOnclick();
		top_left=findViewById(R.id.top_left);
		top_left.setOnClickListener(onclick);
		
	}
	
	private class MyOnclick implements OnClickListener {

		@Override
		public void onClick(View v)
		{
			if (v == top_left)
			{
				finish();
			}
		}
	}
}
