package com.b.om.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private TextView tvUserName,tvUserEmail;
    private Button btnLogOut;
    private ImageView ivUserPhoto;
    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intViews();
    }

    private void intViews() {

        ivUserPhoto = (ImageView) findViewById(R.id.iv_user_photo);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvUserEmail = (TextView) findViewById(R.id.tv_user_email);
        btnLogOut = (Button) findViewById(R.id.btn_logout);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (user != null){

            tvUserName.setText(user.getDisplayName());
            tvUserEmail.setText(user.getEmail());
            Picasso.with(this).load(user.getPhotoUrl()).into(ivUserPhoto);
        }


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               signOut();
            }
        });
    }

    private void signOut() {

        if (mGoogleApiClient.isConnected()){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            auth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this,connectionResult.getErrorMessage(),Toast.LENGTH_LONG).show();
    }
}
