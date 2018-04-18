package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MyGamesDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;

    TextView textViewMyGamesDetailsLocation, textViewMyGamesDetailsSpaces,
            textViewMyGamesDetailsDate, textViewMyGamesDetailsTime;

    String locationDetail, spacesDetail, dateDetail, timeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games_details);

        menuDrawerLayout=findViewById(R.id.drawerMenu);
        menuToggle=new ActionBarDrawerToggle(MyGamesDetails.this, menuDrawerLayout,R.string.open,R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        textViewMyGamesDetailsLocation = findViewById(R.id.textViewMyGamesDetailsLocation);
        textViewMyGamesDetailsSpaces = findViewById(R.id.textViewMyGamesDetailsSpaces);
        textViewMyGamesDetailsDate = findViewById(R.id.textViewMyGamesDetailsDate);
        textViewMyGamesDetailsTime = findViewById(R.id.textViewMyGamesDetailsTime);

        //retrieving the game data from the previous activity

        Bundle detailBundle = getIntent().getExtras();

        if (detailBundle != null)

        {
            locationDetail = detailBundle.getString("location");
            spacesDetail = detailBundle.getString("spaces");
            dateDetail = detailBundle.getString("date");
            timeDetail = detailBundle.getString("time");

            textViewMyGamesDetailsLocation.setText(locationDetail);
            textViewMyGamesDetailsSpaces.setText(spacesDetail);
            textViewMyGamesDetailsDate.setText(dateDetail);
            textViewMyGamesDetailsTime.setText(timeDetail);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(menuToggle.onOptionsItemSelected(item)){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        int id = item.getItemId();

        if( id == R.id.home)
        {
            startActivity(new Intent(this, FindGame.class));
            finish();
        }

        if( id == R.id.addgame)
        {
            startActivity(new Intent(this, CreateGame.class));
            finish();
        }

        if( id == R.id.profile)
        {
            startActivity(new Intent(this, EditProfile.class));
            finish();
        }

        if( id == R.id.mygames)
        {
            startActivity(new Intent(this, MyGames.class));
            finish();
        }

        if( id == R.id.information)
        {
            startActivity(new Intent(this, Information.class));
            finish();
        }

        if( id == R.id.settings)
        {
            startActivity(new Intent(this, Settings.class));
            finish();
        }

        if( id == R.id.logOut)
        {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return false;
    }
}
