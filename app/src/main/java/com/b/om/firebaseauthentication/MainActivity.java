package com.b.om.firebaseauthentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {


    private TextView tvUserName,tvUserEmail;
    private Button btnLogOut;
    private ImageView ivUserPhoto;

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

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null){

            tvUserName.setText(user.getDisplayName());
            tvUserEmail.setText(user.getEmail());
            Picasso.with(this).load(user.getPhotoUrl()).into(ivUserPhoto);
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}
