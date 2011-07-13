package com.jiubang.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3d extends Animation {   
    private float mFromDegree;   
    private float mToDegree;   
    private float mCenterX;   
    private float mCenterY;   
    private float mLeft;   
    private float mTop;   
    private Camera mCamera;     
    private MyCustomeView imageView=null;
    public Rotate3d(float fromDegree, float toDegree, float left, float top,   
            float centerX, float centerY) {   
        this.mFromDegree = fromDegree;   
        this.mToDegree = toDegree;   
        this.mLeft = left;   
        this.mTop = top;   
        this.mCenterX = centerX;   
        this.mCenterY = centerY;  
      
  
    }   
    public Rotate3d(float fromDegree, float toDegree, float left, float top,float centerX, float centerY,MyCustomeView imageview) {   
        this.mFromDegree = fromDegree;   
        this.mToDegree = toDegree;   
        this.mLeft = left;   
        this.mTop = top;   
        this.mCenterX = centerX;   
        this.mCenterY = centerY;   
        this.imageView=imageview;
    }   
  
    @Override   
    public void initialize(int width, int height, int parentWidth, int parentHeight) { 
       super.initialize(width, height, parentWidth, parentHeight);   
    	 mCamera = new Camera();   

    }   
  
    @Override   
    protected void applyTransformation(float interpolatedTime, Transformation t) {
    	final float FromDegree = mFromDegree;   
        float degrees = FromDegree + (mToDegree - mFromDegree)* interpolatedTime; 
        if (imageView != null && degrees >80)
        {
        	imageView.changeImageViewContentByShowIndex();
		}
        
        final Matrix matrix = t.getMatrix(); 
        	mCamera.save();  //这里很重要哦。   
            mCamera.translate(mCenterX, mCenterY, 0);   
            mCamera.rotateY(degrees);  
            mCamera.translate(-mCenterX, -mCenterY, 0);  
            mCamera.getMatrix(matrix);   
            mCamera.restore();  
    }   
}  
