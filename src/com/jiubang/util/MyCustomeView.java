package com.jiubang.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.jiubang.tile.R;
import com.jiubang.tile.Tile;

public class MyCustomeView extends View implements OnTouchListener {
	
	
	float height=0;
	public MyCustomeView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		paint.setAntiAlias(true);
		currentDrawable=context.getResources().getDrawable(R.drawable.criss2);	
	}
	public int getContentWidth()
	{
		return contentWidth;
	}

	public void setContentWidth(int contentWidth)
	{
		this.contentWidth = contentWidth;
	}

	public int getContentHeight()
	{
		return contentHeight;
	}

	public void setContentHeight(int contentHeight)
	{
		this.contentHeight = contentHeight;
	}

	/**
	 * 第一张
	 */
	public int drawable_one = 0;
	/**
	 * 第二张
	 */
	public int drawable_two = 0;

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	private boolean isSelected = false;
	/**
	 * 是否显示第一张
	 */
	private boolean isShowFirst = true;
	
	
	Paint paint = new Paint();
	
	/*** 宽度 */
	private int contentWidth = 0;
	/*** 高度 */
	private int contentHeight = 0;
	
	int pleft = 0;
	int pright = 0;
	int ptop = 0;
	int pbottom = 0;
	
	@Override
	public void setPadding(int left, int top, int right, int bottom)
	{
		
		super.setPadding(left, top, right, bottom);
		this.pleft = left;
		this.pright = right;
		this.ptop = top;
		this.pbottom = bottom;
	}
	@Override
	protected void onDraw(Canvas canvas)
	{	
		if(getAnimation()!=null)
		{
		
			if(isChangeView)
			{
				canvas.scale(-1, 1);
				canvas.translate(-getContentWidth(), 0);
			}
		}else
		{
			isChangeView=false;
		}
		currentDrawable.setBounds(new Rect(pleft, ptop, getContentWidth(), getContentHeight()));
		currentDrawable.draw(canvas);
		
		if (!isSelected)
		{
			paint.setColor(0xa0000000);
			Rect rect = new Rect(pleft+8, ptop+5, getContentWidth() - 8, getContentHeight() - 15);
			canvas.drawRect(rect, paint);
		}
	}
	
	private boolean isChangeView=false;
	public void changeImageViewContent()
	{
		if (isShowFirst)
		{
			setImageDrawable(getContext().getResources().getDrawable(drawable_two));
			isShowFirst = false;
		}
		else
		{
			setImageDrawable(getContext().getResources().getDrawable(drawable_one));
			isShowFirst = true;
		}
		
		
	}
	
	public int showIndex=1;
	public void changeImageViewContentByShowIndex()
	{
		if (showIndex==1)
		{
			setImageDrawable(getContext().getResources().getDrawable(drawable_one));
			
		}
		else
		{
			setImageDrawable(getContext().getResources().getDrawable(drawable_two));
		}
		isChangeView=true;
	}
	
	/**
	 * 当前需要画的图片
	 */
	private Drawable currentDrawable=null;
	public void setImageDrawable(Drawable drawable)
	{
		this.currentDrawable=drawable;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO Auto-generated method stub
		this.contentWidth = currentDrawable.getIntrinsicWidth();
		this.contentHeight = currentDrawable.getIntrinsicHeight();
		setMeasuredDimension(contentWidth, contentHeight);
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int bottom)
	{
		this.height=bottom;
		setScroll_Y(this.getId());
		super.onLayout(changed, left, top, right, bottom);
	}
	private void setScroll_Y(int id)
	{
		switch (id) {
		case R.id.sc_one:
			Tile.scroll_item_y[0] = height;
			break;
		case R.id.sc_two:
			Tile.scroll_item_y[1] = height;
			break;
		case R.id.sc_three:
			Tile.scroll_item_y[2] = height;
			break;
		case R.id.sc_four:
			Tile.scroll_item_y[3] = height;
			break;
		case R.id.sc_five:
			Tile.scroll_item_y[4] = height;
			break;
		case R.id.sc_six:
			Tile.scroll_item_y[5] = height;
			break;
		case R.id.sc_seven:
			Tile.scroll_item_y[6] = height;
			break;
		case R.id.sc_eight:
			Tile.scroll_item_y[7] = height;
			break;
		case R.id.sc_nine:
			Tile.scroll_item_y[8] = height;
			break;
		case R.id.sc_ten:
			Tile.scroll_item_y[9] = height;
			break;
		case R.id.sc_elevent:
			Tile.scroll_item_y[10] = height;
			break;
		case R.id.sc_twelve:
			Tile.scroll_item_y[11] = height;
			break;
		case R.id.sc_thirteen:
			Tile.scroll_item_y[12] = height;
			break;
		case R.id.sc_fourteen:
			Tile.scroll_item_y[13] = height;
			break;
		case R.id.sc_fifteen:
			Tile.scroll_item_y[14] = height;
			break;
		case R.id.sc_sixteen:
			Tile.scroll_item_y[15] = height;
			break;
		case R.id.sc_seventeen:
			Tile.scroll_item_y[16] = height;
			break;
		case R.id.sc_eighteen:
			Tile.scroll_item_y[17] = height;
			break;
		case R.id.sc_nineteen:
			Tile.scroll_item_y[18] = height;
			break;
		case R.id.sc_twenty:
			Tile.scroll_item_y[19] = height;
			break;
		case R.id.sc_twenty_one:
			Tile.scroll_item_y[20] = height;
			break;
		case R.id.sc_twenty_two:
			Tile.scroll_item_y[21] = height;
			break;
		case R.id.sc_twenty_three:
			Tile.scroll_item_y[22] = height;
			break;
		case R.id.sc_twenty_four:
			Tile.scroll_item_y[23] = height;
			break;
		case R.id.sc_twenty_five:
			Tile.scroll_item_y[24] = height;
			break;
		case R.id.sc_twenty_six:
			Tile.scroll_item_y[25] = height;
			break;
		case R.id.sc_twenty_seven:
			Tile.scroll_item_y[26] =height;
			break;
		case R.id.sc_twenty_eight:
			Tile.scroll_item_y[27] = height;
			break;
		case R.id.sc_twenty_nine:
			Tile.scroll_item_y[28] = height;
			break;
		case R.id.sc_thirty:
			Tile.scroll_item_y[29] = height;
			break;
		case R.id.sc_thirty_one:
			Tile.scroll_item_y[30] =height;
			break;
		case R.id.sc_thirty_two:
			Tile.scroll_item_y[31] = height;
			break;
		case R.id.sc_thirty_three:
			Tile.scroll_item_y[32] =height;
			break;
		case R.id.sc_thirty_four:
			Tile.scroll_item_y[33] = height;
			break;
		case R.id.sc_thirty_five:
			Tile.scroll_item_y[34] = height;
			break;
		case R.id.sc_thirty_six:
			Tile.scroll_item_y[35] = height;
			break;
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
