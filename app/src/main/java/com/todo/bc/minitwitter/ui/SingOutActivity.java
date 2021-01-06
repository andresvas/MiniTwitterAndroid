package com.todo.bc.minitwitter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.todo.bc.minitwitter.R;
import com.todo.bc.minitwitter.common.Constants;
import com.todo.bc.minitwitter.common.SharePrefencesManager;
import com.todo.bc.minitwitter.model.RequestSignOut;
import com.todo.bc.minitwitter.model.ResponseAuth;
import com.todo.bc.minitwitter.retrofit.MiniTwitterCLient;
import com.todo.bc.minitwitter.retrofit.MiniTwitterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingOutActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSingOut;
    EditText editTextUserName, editTextEmail, editTextPassword;

    MiniTwitterCLient miniTwitterCLient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_out);

        getSupportActionBar().hide();



        retroditInit();
        findViews();
        events();


    }

    private void retroditInit() {

        miniTwitterCLient = MiniTwitterCLient.getInstance();

        miniTwitterService = miniTwitterCLient.getMiniTwitterService();
    }

    private void findViews() {
        editTextUserName = findViewById(R.id.editTextTextUser);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
    }

    private void events() {
        buttonSingOut = findViewById(R.id.buttonLogin);
        buttonSingOut.setOnClickListener(this);
    }


    private void goToSingUp() {
        String userName = editTextUserName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (userName.isEmpty()) {
            editTextUserName.setError("user requerido");
        } else if (email.isEmpty()) {
            editTextEmail.setError("Email requerido");
        } else  if (password.isEmpty()) {
            editTextPassword.setError("pass requerida");
        } else {

            RequestSignOut requestSignOut = new RequestSignOut(userName,email,password,"UDEMYANDROID");
            miniTwitterService.doSingOut(requestSignOut);


            Call<ResponseAuth> call = miniTwitterService.doSingOut(requestSignOut);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()) {

                        SharePrefencesManager.setSomeStringValue(Constants.PREF_TOKEN,response.body().getToken());

                        SharePrefencesManager.setSomeStringValue(Constants.PREF_USERNAME,response.body().getUsername());
                        SharePrefencesManager.setSomeStringValue(Constants.PREF_EMAIL,response.body().getEmail());
                        SharePrefencesManager.setSomeStringValue(Constants.PREF_PHOTOURL,response.body().getPhotoUrl());
                        SharePrefencesManager.setSomeBooleanValue(Constants.PREF_ACTIVE,response.body().getActive());
                        SharePrefencesManager.setSomeStringValue(Constants.PREF_DATE_CREATED,response.body().getCreated());


                        startActivity(new Intent(SingOutActivity.this, DashboardActivity.class));
                        finish();
                    } else  {
                        Toast.makeText(SingOutActivity.this, "Algo ha ido mal, revisa los datos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SingOutActivity.this, "Error connection, try again", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                goToSingUp();
                break;
        }
    }
}