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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateGame extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;

    Spinner spinnerTime, spinnerLocation, spinnerCost, spinnerPlaces, spinnerSkill;

    EditText editTextDate, editTextNumber, editTextName;

    Button buttonAddData;

    FirebaseAuth mAuth;

    DatabaseReference databaseGames, databaseGamesPrivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        mAuth = FirebaseAuth.getInstance();

        //Setting up the side menu

        menuDrawerLayout=findViewById(R.id.drawerMenu);
        menuToggle=new ActionBarDrawerToggle(CreateGame.this, menuDrawerLayout,R.string.open,R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Getting an instance of the two database tables

        databaseGames = FirebaseDatabase.getInstance().getReference("games");
        databaseGamesPrivate = FirebaseDatabase.getInstance().getReference("gamesPersonal");

        spinnerTime = findViewById(R.id.spinnerTime);
        editTextName = findViewById(R.id.editTextName);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        spinnerCost = findViewById(R.id.spinnerCost);
        spinnerPlaces = findViewById(R.id.spinnerPlaces);
        spinnerSkill = findViewById(R.id.spinnerSkill);
        buttonAddData = findViewById(R.id.buttonAddData);
        editTextDate = findViewById(R.id.editTextDate);
        editTextNumber = findViewById(R.id.editTextNumber);

        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGame();
            }
        });
    }

    //This method allows the data inputted to be selected and added to the database table through being passed into the game class

    private void addGame(){

        String time = spinnerTime.getSelectedItem().toString();
        String name = editTextName.getText().toString();
        String location = spinnerLocation.getSelectedItem().toString();
        String cost = spinnerCost.getSelectedItem().toString();
        String spaces = spinnerPlaces.getSelectedItem().toString();
        String date = editTextDate.getText().toString();
        String number = editTextNumber.getText().toString();
        String skill = spinnerSkill.getSelectedItem().toString();
        String reviewNumber = "0";

        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this,"You must enter a name ", Toast.LENGTH_SHORT).show();
        }else

            if(TextUtils.isEmpty(date))
        {
            Toast.makeText(this,"You must enter a date ", Toast.LENGTH_SHORT).show();
        } else

        if(TextUtils.isEmpty(number))
        {
            Toast.makeText(this,"You must enter a number ", Toast.LENGTH_SHORT).show();
        } else

        if(!TextUtils.isEmpty(reviewNumber)) {

            String id = databaseGames.push().getKey();
            String uid = mAuth.getCurrentUser().getUid();
            GameDB game = new GameDB(id, cost, location, time, spaces, date, number, skill, name, reviewNumber);

            databaseGames.child(id).setValue(game);
            databaseGamesPrivate.child(uid).child(id).setValue(game);

            Toast.makeText(this, "Game has been added", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, FindGame.class));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(menuToggle.onOptionsItemSelected(item)){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //the different options for a menu option being clicked

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
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return false;
    }
}