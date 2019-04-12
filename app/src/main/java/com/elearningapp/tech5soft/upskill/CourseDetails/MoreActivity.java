package com.elearningapp.tech5soft.upskill.CourseDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.R;

public class MoreActivity extends AppCompatActivity {

    private TextView text_about;
    private TextView text_qa;
    private TextView text_resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        getSupportActionBar().setTitle("More");

        initialize();
        onClick();

    }

    public void initialize()
    {
        text_about = findViewById(R.id.text_about);
        text_qa = findViewById(R.id.text_qa);
        text_resources = findViewById(R.id.text_resource);
    }

    public void onClick()
    {
        text_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myIntent = new Intent(MoreActivity.this, CourseInformationActivity.class);
//                startActivity(myIntent);
                Toast.makeText(getApplicationContext(),"Clicked Item",Toast.LENGTH_SHORT).show();
            }
        });

        text_qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MoreActivity.this, QAActivity.class);
                startActivity(myIntent);
            }
        });

        text_resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MoreActivity.this, ResourceActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
