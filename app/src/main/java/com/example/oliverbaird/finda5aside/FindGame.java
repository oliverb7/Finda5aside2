package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FindGame extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;

    DatabaseReference databaseGames;

    ListView listviewGames;

    List<GameDB> gameDBList;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        mAuth = FirebaseAuth.getInstance();

        menuDrawerLayout=findViewById(R.id.drawerMenu);
        menuToggle=new ActionBarDrawerToggle(FindGame.this, menuDrawerLayout,R.string.open,R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //reading in public games
        databaseGames = FirebaseDatabase.getInstance().getReference("games");

        listviewGames = findViewById(R.id.listviewGames);

        gameDBList = new ArrayList<>();

        listviewGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                GameDB game = gameDBList.get(i);

                String location = game.getGameLocation();
                String cost = game.getGameCost();
                String spaces = game.getGameSpaces();
                String date = game.getGameDate();
                String skill = game.getSkill();
                String number = game.getGameNumber();
                String name = game.getName();
                String time =  game.getGameTime();
                String id = game.getGameID();

                Intent intent1 = new Intent(getApplicationContext(), GamesDetails.class);
                intent1.putExtra("location", location);
                intent1.putExtra("cost", cost);
                intent1.putExtra("spaces", spaces);
                intent1.putExtra("date", date);
                intent1.putExtra("skill", skill);
                intent1.putExtra("number", number);
                intent1.putExtra("name", name);
                intent1.putExtra("time", time);
                intent1.putExtra("id", id);


                startActivity(intent1);

            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        databaseGames.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gameDBList.clear();

                for(DataSnapshot gameSnapshot: dataSnapshot.getChildren()){
                    GameDB game = gameSnapshot.getValue(GameDB.class);

                    gameDBList.add(game);
                }

                GamesList adapter = new GamesList(FindGame.this, gameDBList);
                listviewGames.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Collections.reverse(gameDBList);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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