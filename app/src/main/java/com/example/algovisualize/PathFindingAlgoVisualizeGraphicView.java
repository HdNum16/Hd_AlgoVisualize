package com.example.algovisualize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PathFindingAlgoVisualizeGraphicView extends View implements PathFindingAlgoModelListener{

    private Paint mPaint = new Paint();
    private static final int StartX = 10;
    private static final int StartY = 10;
    private int NUM_OF_CELLS_WIDTH = 20;
    private int NUM_OF_CELLS_HEIGHT = 25;
    private int WIDTH, HEIGHT, CELL_EDGE;

    private Coord cur_touch_point = new Coord(-1,-1);

    private PathFindingAlgoModel pModel;
    private Vertex[][] pGraph;

    public void initpModel() {
        pModel = new PathFindingAlgoModel(NUM_OF_CELLS_WIDTH, NUM_OF_CELLS_HEIGHT);
        pGraph = pModel.getpGraph();
        pGraph[GlobalVar.DEFAULT_S.getY()][GlobalVar.DEFAULT_S.getX()].setStatus(GlobalVar.START);
        pGraph[GlobalVar.DEFAULT_T.getY()][GlobalVar.DEFAULT_T.getX()].setStatus(GlobalVar.TARGET);
        pModel.addListener(this);
    }

    public PathFindingAlgoModel getpModel() {
        return pModel;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.rgb(0,182,0));
        mPaint.setStrokeWidth(3);
    }

    public PathFindingAlgoVisualizeGraphicView(Context context) {
        this(context, null);
    }

    public PathFindingAlgoVisualizeGraphicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathFindingAlgoVisualizeGraphicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initpModel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        if (x > StartX && x < StartX + pModel.getWIDTH() * CELL_EDGE && y > StartY && y < StartY + pModel.getHEIGHT() * CELL_EDGE) {
            int new_x = (int) Math.floor((x - StartX) / CELL_EDGE);
            int new_y = (int) Math.floor((y - StartY) / CELL_EDGE);
            if (!(cur_touch_point.getX() == new_x && cur_touch_point.getY() == new_y)) {
                if (pGraph[new_y][new_x].getStatus() == GlobalVar.IKERU) pGraph[new_y][new_x].setStatus(GlobalVar.IKENAI);
                else if (pGraph[new_y][new_x].getStatus() == GlobalVar.IKENAI) pGraph[new_y][new_x].setStatus(GlobalVar.IKERU);
                cur_touch_point.setCoord(new_x, new_y);
            }
        }
        super.invalidate();
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        WIDTH = getWidth();
        HEIGHT = getHeight();
        CELL_EDGE = (int) (WIDTH - 2 * StartX) / NUM_OF_CELLS_WIDTH;

        mPaint.setColor(Color.rgb(0,182,0));
        for (int row = 0; row <= NUM_OF_CELLS_HEIGHT; row++) {
            canvas.drawLine(StartX, StartY + row * CELL_EDGE, NUM_OF_CELLS_WIDTH * CELL_EDGE, StartY + row * CELL_EDGE, mPaint);
        }
        for (int col = 0; col <= NUM_OF_CELLS_WIDTH; col++) {
            canvas.drawLine(StartX + col * CELL_EDGE, StartY, StartX + col * CELL_EDGE,NUM_OF_CELLS_HEIGHT * CELL_EDGE, mPaint);
        }

        DijsktraAlgo_GraphDraw(canvas);
    }

    private void DijsktraAlgo_GraphDraw(Canvas canvas) {
        for (int row = 0; row < NUM_OF_CELLS_HEIGHT; row++) {
            for (int col = 0; col < NUM_OF_CELLS_WIDTH; col++) {
                if (pGraph[row][col].getStatus() == GlobalVar.START) {
                    mPaint.setColor(Color.rgb(0,0,128));
                }
                else if (pGraph[row][col].getStatus() == GlobalVar.TARGET) {
                    mPaint.setColor(Color.rgb(255,0,255));
                }
                else if (pGraph[row][col].getStatus() == GlobalVar.IKENAI) {
                    mPaint.setColor(Color.rgb(192,192,192));
                }
                else if (pGraph[row][col].isShortestPath_flag()) {
                    mPaint.setColor(Color.rgb(15,150,105));
                }
                else if (pGraph[row][col].isChecked_flag()) {
                    mPaint.setColor(Color.rgb(155,100,255));
                }
                else if (pGraph[row][col].getStatus() == GlobalVar.IKERU) {
                    mPaint.setColor(Color.rgb(255,255,255));
                }
                canvas.drawRect(StartX + col * CELL_EDGE + 1, StartY + row * CELL_EDGE + 1, StartX + (col+1) * CELL_EDGE - 1, StartY + (row+1) * CELL_EDGE - 1, mPaint);
                if (pGraph[row][col].isChecked_flag()) {
                    mPaint.setColor(Color.rgb(0,0,05));
                    mPaint.setTextSize(40);
                    canvas.drawText(Integer.toString(pGraph[row][col].getNewWeight()),StartX + col * CELL_EDGE + 5,StartY + (row+1) * CELL_EDGE - 4,mPaint);
                }
            }
        }
    }

    @Override
    public void updated(PathFindingAlgoModel pModel) {
        super.invalidate();
    }
}
