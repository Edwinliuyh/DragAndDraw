package com.bignerdranch.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by dell on 2016/3/22.
 */
public class BoxDrawingView extends View{
    private static final String TAG = "BoxDrawingView";
    private ArrayList<Box> mBoxen = new ArrayList<Box>();
    private Box mCurrentBox;
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    /**
     * 通过代码实例化视图
     */
    public BoxDrawingView(Context context) {
        this(context, null);
    }

    /**
     * 通过布局文件实例化视图
     */
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * Paint类方法可指定绘制图形的特征，如是否填充图形、使用什么字体、线条什么颜色等。
         */
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);//红色
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);//米白色
    }

    /**
     * Canvas类方法可决定绘制的位置及图形，例如线条、圆形、字词、矩形等。
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // 背景色为米白色
        canvas.drawPaint(mBackgroundPaint);

        // 针对矩形框数组中的每一个矩形框，通过其两点坐标，确定矩形框上下左右的位置
        for (Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
            //绘制红色的矩形框
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    /**
     * 添加拖曳生命周期方法 MotionEvent
     */
    public boolean onTouchEvent(MotionEvent event) {
        //X和Y坐标已经封装到PointF对象中
        PointF curr = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // ACTION_DOWN动作事件发生时，以事件原始坐标新建Box对象并赋值
                //然后将Box再添加到矩形框数组中
                mCurrentBox = new Box(curr);
                mBoxen.add(mCurrentBox);
                break;

            case MotionEvent.ACTION_MOVE:
                //ACTION_MOVE事件发生时，调用的invalidate()方法，重新绘制自己。
                // 这样，用户在屏幕上拖曳时就能实时看到矩形框。
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(curr);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                //清空mCurrentBox以结束屏幕绘制
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;
                break;
        }

        return true;
    }


}

