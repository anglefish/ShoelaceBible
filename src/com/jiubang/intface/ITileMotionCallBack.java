package com.jiubang.intface;

public interface ITileMotionCallBack {
	/**
	 * 设置MotionEvent在move时候转中的按钮
	 * @param index
	 */
	public void onItemPressed(int index);
	
	/**
	 * 当MotionEvent在Up的时候返回的值，确定那个按钮出发
	 * @param index
	 */
	public void onMotionUpItemSelected(int index);
	
	/*** 显示MenuBar*/
	public void animation_buttom_menu_show();
	/*** 隐藏MenuBar*/
	public void animation_buttom_menu_hidden();
	/**
	 * 是否让它显示之后不发送消息,
	 * @param allShow
	 */
	public void animation_buttom_menu_alwaysShow(boolean alwaysShow);
	
}
