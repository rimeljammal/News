package com.example.news.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.news.R;
import com.example.news.fragments.LoginFragment;
import com.example.news.fragments.RegisterFragment;

public class AuthenticationActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, RegisterFragment.RegisterFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        addLoginFragment();
    }

    private void addLoginFragment() {
        LoginFragment fragment = LoginFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    @Override
    public void onRequestRegister() {

        RegisterFragment fragment = RegisterFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, RegisterFragment.TAG)
                .addToBackStack(RegisterFragment.TAG)
                .commit();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onRegisterSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterFailure() {

    }
}
