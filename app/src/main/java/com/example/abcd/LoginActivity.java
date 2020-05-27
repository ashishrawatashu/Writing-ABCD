package com.example.abcd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import org.json.JSONObject;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    SignInButton googleSignBT;
    private static int RC_SIGN_IN = 1;
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";
    String name_string ="", user_PhotoUrl="" , email_string ="";
    AccessTokenTracker fbTracker;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        facebookLogin();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void init() {

        googleSignBT=findViewById(R.id.googleSignBT);
        googleSignBT.setSize(SignInButton.SIZE_STANDARD);
        googleSignBT.setOnClickListener(LoginActivity.this);
        loginButton = findViewById(R.id.login_button);


    }

    private void facebookLogin() {

        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

        if (!loggedOut) {

            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());
            getUserProfile(AccessToken.getCurrentAccessToken());


        }

         fbTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {
                    Toast.makeText(getApplicationContext(),"User Logged Out.",Toast.LENGTH_LONG).show();
                }
            }

        };

        fbTracker.startTracking();
        loginButton.setPermissions(Arrays.asList(EMAIL, USER_POSTS));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                getUserProfile(AccessToken.getCurrentAccessToken());

                boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

                if (!loggedOut) {
                    // Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());
                    getUserProfile(AccessToken.getCurrentAccessToken());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("facebook_complete_data", object.toString() + " \n" + "other data: " + response);
                        try {

                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String id=object.getString("id");

                            name_string = first_name+" "+last_name;

                            if(object.has("email")){
                                email_string = object.getString("email");
                            }
                            else
                            {
                                email_string=id;
                            }

                            String user_PhotoUrl = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            Log.e("imagefb",user_PhotoUrl);
                            Constants.image = user_PhotoUrl;
                            login();

                        } catch (Exception e) {

                            Toast.makeText(LoginActivity.this,"getUserProfile "+e.getMessage().toString()+" "+ e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                            Log.e("errrfb",e.getMessage());
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.googleSignBT:
                signIn();

                break;
        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                Toast.makeText(LoginActivity.this, account.getDisplayName(), Toast.LENGTH_SHORT).show();
                 name_string = account.getDisplayName();
                if (account.getPhotoUrl() != null) {
                    user_PhotoUrl = account.getPhotoUrl().toString();
                    Constants.image= user_PhotoUrl;
                }
                else {
                    user_PhotoUrl = "";
                }
                email_string = account.getEmail();
                login();
                Log.e("google_login_email", "" + email_string + "  :  " + name_string + "  :  " + user_PhotoUrl + "  :  " + "google_login");
            } else
                Toast.makeText(this, "Something went wrong,Please Try Again", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
        }
    }

    private void login() {

        Toast.makeText(this, "Welcome"+" " +name_string, Toast.LENGTH_SHORT).show();
        Intent  intent = new Intent(LoginActivity.this,MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        fbTracker.stopTracking();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
}
