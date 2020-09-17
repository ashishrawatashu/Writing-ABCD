package com.cynoteck.kidsFun2Write;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    TextToSpeech textToSpeech;
    TextView nextLetterBT, backLetterBT;
    Button clearLetterBT, homeBT;
    SignaturePad signaturePad;
    TextView letterTV;
    int potion = 0, click=0;
    private InterstitialAd mInterstitialAd;
    AdView adView;
    String key = "";
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2823662566409957/9158458774");
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
                    } else {
                        nextLetterBT.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }

            }
        });





        init();

        bannerAdd();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        Log.e("key",key);

        takeTimeToNextQuestion();
        signature();

    }

    private void init() {
        adView = findViewById(R.id.adView);
        nextLetterBT = findViewById(R.id.nextLetterBT);
        clearLetterBT = findViewById(R.id.clearLetterBT);
        signaturePad = findViewById(R.id.signaturePad);
        letterTV = findViewById(R.id.letterTV);
        homeBT=findViewById(R.id.homeBT);
        homeBT.setOnClickListener(this);
        backLetterBT = findViewById(R.id.backLetterBT);
        backLetterBT.setOnClickListener(this);
        nextLetterBT.setOnClickListener(this);
        clearLetterBT.setOnClickListener(this);
        clearLetterBT.setEnabled(false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextLetterBT:
                mediaPlayer.start();
                if(click == 4){
                    if (mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                        click = 0;
                    }
                }else{
                    click++ ;
                }
                if (!mInterstitialAd.isLoaded()) {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
                potion = potion+1;

                displayLettes(potion);

                break;

            case R.id.backLetterBT:
                mediaPlayer.start();
                if (potion==0){
                    Toast.makeText(this, "This is First letter", Toast.LENGTH_SHORT).show();
                }else {
                    potion = potion - 1;
                    displayLettes(potion);
                }
                break;

            case R.id.clearLetterBT:
                mediaPlayer.start();
                signaturePad.clear();
                break;

            case R.id.homeBT:
                mediaPlayer.start();
                onBackPressed();
                break;


        }
    }


    private void bannerAdd() {
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(String.valueOf(AdRequest.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE))
                .build();
        adView.loadAd(adRequest);
    }


    private void displayLettes(int pos) {

        if (key.equals("ABC")) {
            List<String> ABC = Arrays.asList(getResources().getStringArray(R.array.ABC_array));

            if (pos==ABC.size()){
                nextLetterBT.setVisibility(View.GONE);
                onBackPressed();
            }else {
                signaturePad.clear();
                letterTV.setText(ABC.get(pos));
                String text = letterTV.getText().toString();
                textToSpeech.setPitch(0.9f);
                textToSpeech.setSpeechRate(0.5f);
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                nextLetterBT.setVisibility(View.VISIBLE);
                if (potion==ABC.size()-1){
                    nextLetterBT.setVisibility(View.GONE);
                }else {
                    nextLetterBT.setVisibility(View.VISIBLE);
                    nextLetterBT.setText(ABC.get(pos+1));
                }
                if (potion==0){
                    backLetterBT.setVisibility(View.GONE);
                }else {
                    backLetterBT.setVisibility(View.VISIBLE);
                    backLetterBT.setText(ABC.get(pos - 1));
                }

            }
        }else if (key.equals("abc")){
            List<String> abc = Arrays.asList(getResources().getStringArray(R.array.abc_array));
            if (pos==abc.size()){
                nextLetterBT.setVisibility(View.GONE);
                onBackPressed();
            }else {
                signaturePad.clear();
                letterTV.setText(abc.get(pos));
                String text = letterTV.getText().toString();
                textToSpeech.setPitch(0.9f);
                textToSpeech.setSpeechRate(0.5f);
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                if (potion==abc.size()-1){
                    nextLetterBT.setVisibility(View.GONE);
                }else {
                    nextLetterBT.setVisibility(View.VISIBLE);
                    nextLetterBT.setText(abc.get(pos+1));
                }
                if (potion==0){
                    backLetterBT.setVisibility(View.GONE);
                }else {
                    backLetterBT.setVisibility(View.VISIBLE);
                    backLetterBT.setText(abc.get(pos - 1));
                }
            }
        }else if (key.equals("123")){
            ArrayList<Integer> numbers = new ArrayList<Integer>(100);
            for (int i = 1; i <= 100; i++)
            {
                numbers.add(i);

            }
            if (pos==numbers.size()){
                onBackPressed();
            }else {
                signaturePad.clear();
                letterTV.setText(""+numbers.get(pos));
                String text = letterTV.getText().toString();
                textToSpeech.setPitch(0.9f);
                textToSpeech.setSpeechRate(0.8f);
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                if (potion==numbers.size()-1){
                    nextLetterBT.setVisibility(View.GONE);
                }else {
                    nextLetterBT.setVisibility(View.VISIBLE);
                    nextLetterBT.setText(""+numbers.get(pos+1));
                }
                if (potion==0){
                    backLetterBT.setVisibility(View.GONE);
                }else {
                    backLetterBT.setVisibility(View.VISIBLE);
                    backLetterBT.setText(""+numbers.get(pos - 1));
                }
            }
        }
    }

    private void signature() {

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }
            @Override
            public void onSigned() {
                clearLetterBT.setEnabled(true);
            }
            @Override
            public void onClear() {
                clearLetterBT.setEnabled(false);

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void takeTimeToNextQuestion() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                displayLettes(potion);

            }
        },1000);
    }

}
