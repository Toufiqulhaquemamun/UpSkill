package com.elearningapp.tech5soft.upskill.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.elearningapp.tech5soft.upskill.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageViewlogo,imageViewtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageViewlogo = findViewById(R.id.image_app_logo);
        imageViewtxt=findViewById(R.id.image_logo_txt);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_animation);
        imageViewlogo.startAnimation(animation);
        //imageViewtxt.startAnimation(animation);

        Thread timer = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();                }
                    catch (InterruptedException e) {
                    e.printStackTrace();                }


            }
        };
        timer.start();

}
}
