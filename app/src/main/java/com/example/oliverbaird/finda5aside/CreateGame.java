package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateGame extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;

    Spinner spinnerTime;
    Spinner spinnerLocation;
    Spinner spinnerCost;
    Spinner spinnerPlaces;
    Button buttonAddData;

    DatabaseReference databaseGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);


        menuDrawerLayout=(DrawerLayout) findViewById(R.id.drawerMenu);
        menuToggle=new ActionBarDrawerToggle(CreateGame.this, menuDrawerLayout,R.string.open,R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseGames = FirebaseDatabase.getInstance().getReference("games");

        spinnerTime = (Spinner) findViewById(R.id.spinnerTime);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        spinnerCost = (Spinner) findViewById(R.id.spinnerCost);
        spinnerPlaces = (Spinner) findViewById(R.id.spinnerPlaces);
        buttonAddData = (Button) findViewById(R.id.buttonAddData);

        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGame();
            }
        });
    }

    private void addGame(){

        String time = spinnerTime.getSelectedItem().toString();
        String location = spinnerLocation.getSelectedItem().toString();
        String cost = spinnerCost.getSelectedItem().toString();
        String spaces = spinnerPlaces.getSelectedItem().toString();

        if(!TextUtils.isEmpty(time)){

            String id = databaseGames.push().getKey();

            GameDB game = new GameDB(id, time, location, cost, spaces);

            databaseGames.child(id).setValue(game);

            Toast.makeText(this,"Game has been added", Toast.LENGTH_SHORT).show();

        } else{

            Toast.makeText(this,"You must select a time", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        }

        return false;
    }
}