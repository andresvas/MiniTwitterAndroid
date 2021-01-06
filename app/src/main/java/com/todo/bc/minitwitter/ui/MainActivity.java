package com.todo.bc.minitwitter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.todo.bc.minitwitter.R;
import com.todo.bc.minitwitter.common.Constants;
import com.todo.bc.minitwitter.common.SharePrefencesManager;
import com.todo.bc.minitwitter.model.RequestLogin;
import com.todo.bc.minitwitter.model.ResponseAuth;
import com.todo.bc.minitwitter.retrofit.MiniTwitterCLient;
import com.todo.bc.minitwitter.retrofit.MiniTwitterService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    TextView textViewSingOut;

    EditText editTextEmail, editTextPassword;

    MiniTwitterCLient miniTwitterCLient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }

        findViews();
        retrofitInit();


        textViewSingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SingOutActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError("email requerido");
        } else if (pass.isEmpty()) {
            editTextPassword.setError("pass requerida");
        } else {
            RequestLogin requestLogin = new RequestLogin(email,pass);

            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Session iniciada ", Toast.LENGTH_SHORT).show();
                        SharePrefencesManager.setSomeStringValue(Constants.PREF_TOKEN,response.body().getToken());

                        SharePrefencesManager.setSomeStringValue(Constants.PREF_USERNAME,response.body().getUsername());
                        SharePrefencesManager.setSomeStringValue(Constants.PREF_EMAIL,response.body().getEmail());
                        SharePrefencesManager.setSomeStringValue(Constants.PREF_PHOTOURL,response.body().getPhotoUrl());
                        SharePrefencesManager.setSomeBooleanValue(Constants.PREF_ACTIVE,response.body().getActive());
                        SharePrefencesManager.setSomeStringValue(Constants.PREF_DATE_CREATED,response.body().getCreated());

                        startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        // destroy activity
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Error auth, possible pass or email is incorret", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error sevrices Auth - retrofit", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void retrofitInit() {
        miniTwitterCLient = MiniTwitterCLient.getInstance();
        miniTwitterService = miniTwitterCLient.getMiniTwitterService();
    }

    private void findViews() {
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSingOut = findViewById(R.id.textViewCreateAccount);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
    }
}