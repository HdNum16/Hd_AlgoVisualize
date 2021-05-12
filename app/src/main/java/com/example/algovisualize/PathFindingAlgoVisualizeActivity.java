package com.example.algovisualize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class PathFindingAlgoVisualizeActivity extends AppCompatActivity {

    Button startButton;
    Button clearButton;
    Spinner algoSpinner;
    PathFindingAlgoModel pModel;
    PathFindingAlgoVisualizeGraphicView pView;
    Thread subThread;
    Handler handler = new Handler();
    boolean stop_flag = false;

    private void PathFindingAlgo_Run(final String PathFindingAlgo) {
        stop_flag = false;
        pModel.initPathFindingAlgo();
        subThread = new Thread() {
            @Override
            public void run() {
                for (int count = -1; count < pModel.getWIDTH() * pModel.getHEIGHT(); count++) { // don't know why handler skip the first value of count (in this case -1)
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int step = pModel.PathFindingAlgo_1Step(PathFindingAlgo);
                            if (step == 1) {
                                stop_flag = true;
                                pModel.PathFindingAlgo_getShortestPath();
                            }
                            else if (step == 2) {
                                stop_flag = true;
                            }
                        }
                    });
                    if (stop_flag) {
                        subThread.interrupt();
                        break;
                    }
                }
            }
        };
        subThread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finding_algo_visualize);

        pView = (PathFindingAlgoVisualizeGraphicView) findViewById(R.id.PathFindingAlgoVisualize);
        pModel = pView.getpModel();

        algoSpinner = (Spinner) findViewById(R.id.PathFindingAlgoSpinner);

        startButton = (Button) findViewById(R.id.PathFindingStartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PathFindingAlgo = algoSpinner.getSelectedItem().toString();
                if (subThread != null && subThread.isAlive()) {
                    stop_flag = true;
                    subThread.interrupt();
                }
                else {
                    PathFindingAlgo_Run(PathFindingAlgo);
                }
            }
        });

        clearButton = (Button) findViewById(R.id.PathFindingAlgoClearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subThread == null || !subThread.isAlive()) {
                    pView.initpModel();
                    pModel = pView.getpModel();
                    pModel.fireUpdate();
                }
            }
        });
    }
}
