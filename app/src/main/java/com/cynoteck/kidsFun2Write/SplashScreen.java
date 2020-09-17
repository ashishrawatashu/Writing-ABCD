package com.cynoteck.kidsFun2Write;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class SplashScreen extends AppCompatActivity {

    Animation animation;
    ImageView splash_logo;
    LinearLayout baloonsLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash_logo= findViewById(R.id.splashlogo);
        baloonsLL = findViewById(R.id.ballonsLL);

        animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.buttom_up);
        baloonsLL.setAnimation(animation);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreen.this,MenuActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);


                }
            },4000);
        }
    }

