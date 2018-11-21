package com.example.pooja.parkingspot;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pooja.parkingspot.RetroFitInterfaces.APIInterface;
import com.example.pooja.parkingspot.modles.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.et_login)
    EditText email;
    @BindView(R.id.et_password)
    EditText password;
    private static final int PERMISSION_REQUEST = 13;
    private APIInterface apiInterface;
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        apiInterface = ((MyApplication) this.getApplication()).getApiInterface();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsynctaskRunner runner = new AsynctaskRunner();
                runner.execute();
            }
        });
    }

    public void validate() {
        if (email.getText().toString().isEmpty()) {
            email.setError("Email required");
        }

        if (password.getText().toString().isEmpty()) {
            password.setError("Password required");
        }

        apiInterface.userAuthentication(email.getText().toString(), password.getText().toString()).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                UserData userData = response.body();
                Log.v(TAG, userData.toString());
                ((MyApplication) MainActivity.this.getApplication()).setUserData(userData);
                Log.v(TAG, "onResponse call");
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                call.cancel();
                Log.v(TAG, "onFailure call");
                t.printStackTrace();
            }
        });
    }

    private class AsynctaskRunner extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            UserData userData = ((MyApplication) MainActivity.this.getApplication()).getUserData();
            if (userData != null) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("PERMISSION", "onCreate: " + "Permission granted");
                    if (userData.getRole().equalsIgnoreCase(getResources().getString(R.string.user_role))) {
                        startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, AllParkingListActivity.class));
                    }
                    finish();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
                }

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "", getResources().getString(R.string.login));
            validate();
        }
    }

}
