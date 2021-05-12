package com.example.algovisualize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.file.Path;

public class MainActivity extends AppCompatActivity {

    Button SortingAlgoButton, PathFindingAlgoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SortingAlgoButton = (Button) findViewById(R.id.SortingAlgoVisualize);
        SortingAlgoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SortingAlgoVisualizeActivity.class);
                startActivity(intent);
            }
        });

        PathFindingAlgoButton = (Button) findViewById(R.id.PathFindingAlgoVisualize);
        PathFindingAlgoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PathFindingAlgoVisualizeActivity.class);
                startActivity(intent);
            }
        });
    }
}
