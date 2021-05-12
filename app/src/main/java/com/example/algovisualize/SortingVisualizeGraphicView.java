package com.example.algovisualize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class SortingVisualizeGraphicView extends View implements SortingAlgoModelListener {
    private Paint mPaint = new Paint();
    private static final int StartX = 40;
    private static final int StartY = 80;
    private float WIDTH;
    private float dx;

    private SortingAlgoModel sModel;
    int Length;
    ArrayList<Integer> Data;
    ArrayList<Integer> Data_color;

    private void initsModel() {
        sModel = new SortingAlgoModel(10,40,32);
        sModel.addListener(this);
        Length = sModel.getLength();
        Data = sModel.getData();
        Data_color = sModel.getData_color();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(30);
    }

    public SortingVisualizeGraphicView(Context context) {
        this(context, null);
    }

    public SortingVisualizeGraphicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortingVisualizeGraphicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initsModel();
    }

    public void setsModel(SortingAlgoModel sModel) {
        this.sModel = sModel;
        this.sModel.addListener(this);
        Length = this.sModel.getLength();
        Data = this.sModel.getData();
        Data_color = this.sModel.getData_color();
        this.sModel.fireUpdate();
    }

    public SortingAlgoModel getsModel() {
        return sModel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        WIDTH = (float) getWidth();
        dx = (WIDTH - 2 * StartX) / (Length - 1);
        mPaint.setStrokeWidth(dx * 3 / 4);

        for (int i = 0; i < Length; i++) {
            if (Data_color.get(i) == 0) {
                mPaint.setColor(Color.rgb(100,100,200));
            } else if (Data_color.get(i) == 1) {
                mPaint.setColor(Color.rgb(100,100,50));
            } else if (Data_color.get(i) == 2) {
                mPaint.setColor(Color.rgb(255,100,100));
            } else if (Data_color.get(i) == 3) {
                mPaint.setColor(Color.rgb(238,190,201));
            }

            canvas.drawLine(StartX + i * dx, StartY,StartX + i * dx,StartY +  Data.get(i) * 30, mPaint);
        }
    }

    @Override
    public void updated(SortingAlgoModel model) {
        super.invalidate();
    }
}
