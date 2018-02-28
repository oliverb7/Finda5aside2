package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.location.Location;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GamesDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_details);

        menuDrawerLayout = (DrawerLayout) findViewById(R.id.drawerMenu);
        menuToggle = new ActionBarDrawerToggle(GamesDetails.this, menuDrawerLayout, R.string.open, R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView textViewDetailsLocation = (TextView) findViewById(R.id.textViewDetailsLocation);
        TextView textViewDetailsCost = (TextView) findViewById(R.id.textViewDetailsCost);
        TextView textViewDetailsSpaces = (TextView) findViewById(R.id.textViewDetailsSpaces);
        TextView textViewDetailsDate = (TextView) findViewById(R.id.textViewDetailsDate);


        Bundle detailBundle = getIntent().getExtras();

        if(detailBundle != null)

        {
            String locationDetail = detailBundle.getString("location");
            String costDetail = detailBundle.getString("cost");
            String spacesDetail = detailBundle.getString("spaces");
            String dateDetail = detailBundle.getString("date");

            textViewDetailsLocation.setText(locationDetail);
            textViewDetailsCost.setText(costDetail);
            textViewDetailsSpaces.setText(spacesDetail);
            textViewDetailsDate.setText(dateDetail);
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
        }

        if( id == R.id.addgame)
        {
            startActivity(new Intent(this, CreateGame.class));
        }

        if( id == R.id.profile)
        {
            startActivity(new Intent(this, EditProfile.class));
        }

        if( id == R.id.settings)
        {
            Toast.makeText(this, "You have selected settings", Toast.LENGTH_SHORT).show();
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
