package com.elearningapp.tech5soft.upskill.CourseDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.elearningapp.tech5soft.upskill.R;

public class ResourceActivity extends AppCompatActivity {

    private TextView text_resource_1;
    private TextView text_resource_2;
    private TextView text_resource_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);


        initialize();

    }

    public void initialize()
    {
        getSupportActionBar().setTitle("Resources");


        text_resource_1 = findViewById(R.id.text_resource_1);
        text_resource_2 = findViewById(R.id.text_resource_2);
        text_resource_3 = findViewById(R.id.text_resource_3);


        text_resource_1.setText("https://material.io/");
        text_resource_2.setText("https://material.io/design/");
        text_resource_3.setText("https://material.io/develop/");
    }

}
