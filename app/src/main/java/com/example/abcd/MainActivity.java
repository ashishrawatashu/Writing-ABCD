package com.example.abcd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener, View.OnClickListener  {

    Button nextLetterBT, clearLetterBT;
    SignaturePad signaturePad;
    TextView letterTV;
    int potion = 0, click=0;
    private RewardedVideoAd mRewardedVideoAd;
    AdView adView;
    CircleImageView imageView;
    String image;
    ImageView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        bannerAdd();
        //Picasso.with(this).load(Constants.image).into(imageView);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener((RewardedVideoAdListener) this);
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
        displayLettes(potion);
        signature();

    }

    private void init() {
        logOut = findViewById(R.id.logout);
        imageView = findViewById(R.id.image_view);
        adView = findViewById(R.id.adView);
        nextLetterBT = findViewById(R.id.nextLetterBT);
        clearLetterBT = findViewById(R.id.clearLetterBT);
        signaturePad = findViewById(R.id.signaturePad);
        letterTV = findViewById(R.id.letterTV);
        nextLetterBT.setOnClickListener(this);
        clearLetterBT.setOnClickListener(this);
        logOut.setOnClickListener(this);
        clearLetterBT.setEnabled(false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextLetterBT:
                if(click == 4){
                    if (mRewardedVideoAd.isLoaded()){
                        mRewardedVideoAd.show();
                        click = 0;
                    }
                }else{
                    click++ ;
                }
                if (!mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
                }
                potion = potion+1;
                displayLettes(potion);
                break;

            case R.id.clearLetterBT:
                signaturePad.clear();
                break;


           /* case R.id.logout:

                LoginManager.getInstance().logOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                googleSignInClient.signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);


                break;*/

        }
    }
    private void bannerAdd() {
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }


    private void displayLettes(int pos) {

        if (Constants.Type == "ABC") {
            List<String> ABC = Arrays.asList(getResources().getStringArray(R.array.ABC_array));
            if (pos==ABC.size()){
                onBackPressed();
            }else {
                signaturePad.clear();
                letterTV.setText(ABC.get(pos));
            }
        }else if (Constants.Type=="abc"){
            List<String> abc = Arrays.asList(getResources().getStringArray(R.array.abc_array));
            if (pos==abc.size()){
                onBackPressed();
            }else {
                signaturePad.clear();
                letterTV.setText(abc.get(pos));
            }
        }else if (Constants.Type=="123"){
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
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }


}
