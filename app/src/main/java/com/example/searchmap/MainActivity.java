package com.example.searchmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonBuildings;
    private Button buttonSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBuildings = (Button) findViewById(R.id.buttonBuildings);
        buttonSchedule = (Button) findViewById(R.id.buttonSchedule);

        buttonBuildings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openBuildingsActivity();
            }
        });

        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScheduleActivity();
            }
        });
    }
    public void openBuildingsActivity()
    {
        Intent intent = new Intent(this, BuildingsActivity.class);
        startActivity(intent);
    }

    public void openScheduleActivity()
    {
        Intent intent = new Intent(this, ClassSchedule.class);
        startActivity(intent);
    }
}
