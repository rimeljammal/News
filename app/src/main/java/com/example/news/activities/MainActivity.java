package com.example.news.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.fragments.ArticlesFragment;
import com.example.news.fragments.FavoriteFragment;
import com.example.news.fragments.GuestArticlesFragment;
import com.example.news.local.storage.LocalStorageManager;
import com.example.news.models.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ArticlesFragment.OnFragmentInteractionListener, FavoriteFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addArticlesFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                addArticlesFragment();
                break;

            case R.id.nav_favorites:
                showFavoritesFragment();
                break;

            case R.id.nav_logout:
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        LocalStorageManager.getInstance(this).deleteUser();
        Intent intent = new Intent(this, GuestActivity.class);
        startActivity(intent);
        finish();
    }

    private void addArticlesFragment() {
        setTitle("Home");
        ArticlesFragment fragment = ArticlesFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, fragment)
                .commit();
    }

    private void showFavoritesFragment() {
        setTitle(getString(R.string.fav_title));
        FavoriteFragment fragment = FavoriteFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container_main, fragment).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
