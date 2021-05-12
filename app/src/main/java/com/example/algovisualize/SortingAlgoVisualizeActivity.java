package com.example.algovisualize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SortingAlgoVisualizeActivity extends AppCompatActivity {

    SortingAlgoModel sModel;
    SortingVisualizeGraphicView sView;
    Button generateButton, sortingButton;
    Spinner algoSpinner;
    Thread subThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_algo_visualize);

        sView = (SortingVisualizeGraphicView) findViewById(R.id.SortingAlgoVisualize);
        sModel = sView.getsModel();

        algoSpinner = (Spinner) findViewById(R.id.SortingAlgoSpinner);

        generateButton = (Button) findViewById(R.id.GenerateButton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subThread != null && subThread.isAlive()) {
                    subThread.interrupt();
                    Toast.makeText(SortingAlgoVisualizeActivity.this, "Interrupted", Toast.LENGTH_SHORT).show();
                }
                sModel = new SortingAlgoModel(10,40,sModel.getLength());
                sView.setsModel(sModel);
            }
        });

        sortingButton = (Button) findViewById(R.id.SortingButton);
        sortingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SortingAlgo = algoSpinner.getSelectedItem().toString();
                if (subThread != null && subThread.isAlive()) {
                    subThread.interrupt();
                    Toast.makeText(SortingAlgoVisualizeActivity.this, "Interrupted", Toast.LENGTH_SHORT).show();
                }
                else if (SortingAlgo.compareTo("Bubble Sort") == 0) {
                    subThread = sModel.BubbleSortAlgoThread();
                    subThread.start();
                }
                else if (SortingAlgo.compareTo("Merge Sort") == 0) {
                    subThread = sModel.MergeSortAlgoThread();
                    subThread.start();
                }
                else if (SortingAlgo.compareTo("Quick Sort") == 0) {
                    subThread = sModel.QuickSortAlgoThread();
                    subThread.start();
                }
            }
        });
    }
}
