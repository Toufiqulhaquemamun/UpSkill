package com.elearningapp.tech5soft.upskill.CourseDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.elearningapp.tech5soft.upskill.R;

public class QAActivity extends AppCompatActivity {

    private TextView text_question;
    private TextView text_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);

        initialize();
    }

    public void initialize()
    {
        getSupportActionBar().setTitle("Question & Answer");

        text_question = findViewById(R.id.text_question);
        text_answer = findViewById(R.id.text_answer);

        text_question.setText("TextSize not responsive !");
        text_answer.setText(" android:textSize=\"30sp\"");
    }
}
