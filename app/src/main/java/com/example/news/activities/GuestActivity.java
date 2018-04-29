package com.example.news.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.news.R;
import com.example.news.fragments.GuestArticlesFragment;
import com.example.news.local.storage.LocalStorageManager;
import com.example.news.models.User;

public class GuestActivity extends AppCompatActivity implements GuestArticlesFragment.OnFragmentInteractionListener {

    private LocalStorageManager localStorageManager;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        addArticlesFragment();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.auth:
                goToAuth();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addArticlesFragment() {
        GuestArticlesFragment fragment = GuestArticlesFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_guest, fragment)
                .commit();
    }


    public void goToAuth()    {
        Intent intent;

        localStorageManager = LocalStorageManager.getInstance(getApplicationContext());
        User user = localStorageManager.getUser();
        if(user == null)  {
            intent = new Intent(this, AuthenticationActivity.class);
        }   else    {
            token = user.getToken();
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}