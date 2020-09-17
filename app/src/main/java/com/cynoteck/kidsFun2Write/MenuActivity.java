package com.cynoteck.kidsFun2Write;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    TextToSpeech textToSpeech;
    Button write_ABC_BT, write_abc_BT, write_123_BT;
    AdView adView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mediaPlayer = MediaPlayer.create(this, R.raw.click);

        MobileAds.initialize(this, String.valueOf(R.string.app_id));
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }

            }
        });
        init();
        bannerAdd();
    }

    private void init() {

        write_123_BT = findViewById(R.id.write_123_BT);
        write_abc_BT = findViewById(R.id.write_abc_BT);
        write_ABC_BT = findViewById(R.id.write_ABC_BT);

        write_abc_BT.setOnClickListener(this);
        write_ABC_BT.setOnClickListener(this);
        write_123_BT.setOnClickListener(this);

    }

    private void bannerAdd() {
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE))
                .build();
        adView.loadAd(adRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.write_ABC_BT:
                mediaPlayer.start();
                Intent ABC_intent = new Intent(MenuActivity.this,MainActivity.class);
                ABC_intent.putExtra("key","ABC");
                startActivity(ABC_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;

            case R.id.write_abc_BT:
                mediaPlayer.start();
                Intent abc_intent = new Intent(MenuActivity.this,MainActivity.class);
                abc_intent.putExtra("key","abc");
                startActivity(abc_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;

            case R.id.write_123_BT:
                mediaPlayer.start();
                Intent num_intent = new Intent(MenuActivity.this,MainActivity.class);
                num_intent.putExtra("key","123");
                startActivity(num_intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;


        }

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
