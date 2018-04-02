package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Information extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;

    private TextView basicLabel, hostLabel, playerLabel;

    private ViewPager mainPage;

    private PageViewAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        basicLabel = findViewById(R.id.basicLabel);
        hostLabel = findViewById(R.id.hostLabel);
        playerLabel = findViewById(R.id.playerLabel);

        mainPage = findViewById(R.id.mainPager);

        mPageAdapter = new PageViewAdapter(getSupportFragmentManager());
        mainPage.setAdapter(mPageAdapter);

        mainPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                changeTabs(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        menuDrawerLayout=findViewById(R.id.drawerMenu);
        menuToggle=new ActionBarDrawerToggle(Information.this, menuDrawerLayout,R.string.open,R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void changeTabs(int position){

        if(position == 0){

            basicLabel.setTextColor(getColor(R.color.textTabBright));
            basicLabel.setTextSize(22);

            hostLabel.setTextColor(getColor(R.color.textTabLight));
            hostLabel.setTextSize(16);

            playerLabel.setTextColor(getColor(R.color.textTabLight));
            playerLabel.setTextSize(16);
        }

        if(position == 1){

            basicLabel.setTextColor(getColor(R.color.textTabLight));
            basicLabel.setTextSize(16);

            hostLabel.setTextColor(getColor(R.color.textTabBright));
            hostLabel.setTextSize(22);

            playerLabel.setTextColor(getColor(R.color.textTabLight));
            playerLabel.setTextSize(16);
        }

        if(position == 2){

            basicLabel.setTextColor(getColor(R.color.textTabLight));
            basicLabel.setTextSize(16);

            hostLabel.setTextColor(getColor(R.color.textTabLight));
            hostLabel.setTextSize(16);

            playerLabel.setTextColor(getColor(R.color.textTabBright));
            playerLabel.setTextSize(22);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(menuToggle.onOptionsItemSelected(item)){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //adding functions for if a list item is selected

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        int id = item.getItemId();

        if( id == R.id.home)
        {
            startActivity(new Intent(this, FindGame.class));
        }

        if( id == R.id.addgame)
        {
            startActivity(new Intent(this, CreateGame.class));
        }

        if( id == R.id.profile)
        {
            startActivity(new Intent(this, EditProfile.class));
        }

        if( id == R.id.mygames)
        {
            startActivity(new Intent(this, MyGames.class));
        }

        if( id == R.id.information)
        {
            startActivity(new Intent(this, Information.class));
        }

        if( id == R.id.settings)
        {
            startActivity(new Intent(this, Settings.class));
        }

        if( id == R.id.logOut)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
            finish();
        }

        return false;
    }
}
