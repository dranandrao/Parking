package com.example.pooja.parkingspot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)Button loginButton;
    @BindView(R.id.et_login)EditText email;
    @BindView(R.id.et_password)EditText password;
    private static final int PERMISSION_REQUEST = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(MainActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.e("PERMISSION", "onCreate: "+"Permission granted" );
                        startActivity(new Intent(MainActivity.this, MapsActivity.class));
                        finish();
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
                    }

                }
            }
        });
    }
    public boolean validate(){
        boolean valid = false;
        if(email.getText().toString().isEmpty()){
            email.setError("Email required");
        }else if(email.getText().toString().equalsIgnoreCase("user1@gmail.com")){
            valid = true;
        }else{
            email.setError("Enter valid email");
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Password required");
        }else if(password.getText().toString().equalsIgnoreCase("user1")){
            valid = true;
        }else {
            password.setError("enter valid password");

        }
        return valid;
    }
}
