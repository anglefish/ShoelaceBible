package com.jiubang.util;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


public class MyFrameLayout extends FrameLayout {

	private int child_offset = 30;

	
	private ArrayList<Integer> child_draw_list=new ArrayList<Integer>();
	private ArrayList<Integer> child_draw_list_temp=new ArrayList<Integer>();
	public static final int child_count=35;
	
	public MyFrameLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		setChildrenDrawingOrderEnabled(true);
		
		
		for (int i = child_count; i >= 0; i--)
		{
			child_draw_list.add(i);
		}
		
		child_draw_list_temp.addAll(child_draw_list);
	}
	
	
	
	@Override
	protected int getChildDrawingOrder(int childCount, int i)
	{
		return child_draw_list_temp.get(i);

	}
	
	public void changeChildDrawOrder(int index)
	{
		child_draw_list_temp.clear();
		child_draw_list_temp.addAll(child_draw_list);
		
		int child_draw_index=child_draw_list_temp.indexOf(index);
		Integer inte=child_draw_list_temp.remove(child_draw_index);
		ArrayList<Integer> temp_header=new ArrayList<Integer>();
		ArrayList<Integer> temp_tail=new ArrayList<Integer>();
		
		for (int i = 0; i < child_draw_index; i++)
		{
			temp_header.add(child_draw_list_temp.get(i));
		}
		
		
		for (int i =child_draw_index; i <child_draw_list_temp.size(); i++)
		{
			temp_tail.add(child_draw_list_temp.get(i));
		}
		
		Collections.reverse(temp_tail);
		
		child_draw_list_temp.clear();
		
		child_draw_list_temp.addAll(temp_header);
		child_draw_list_temp.addAll(temp_tail);
		child_draw_list_temp.add(inte);
		
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int child_count = getChildCount();
		int child_width = 0;
		int child_height = 0;
		if (child_count > 0)
		{
			View child = getChildAt(0);
			child_width = child.getMeasuredWidth();
			child_height = child.getMeasuredHeight() - child_offset;
		}
		setMeasuredDimension(child_width, child_height * child_count+30);

	}

	String log = "layout";

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int bottom)
	{

		int child_size = getChildCount();
		int l = 0;
		int t = 0;
		int r = 0;
		int b = 0;
		for (int i = 0; i < child_size; i++)
		{

			View child = this.getChildAt(i);
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight() - child_offset;
			t = height * i;
			r = l + width;
			b = t + height+30;

			child.layout(l, t, r, b);
		}
	}

}
