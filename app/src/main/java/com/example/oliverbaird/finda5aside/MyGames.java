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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyGames extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;

    DatabaseReference databaseGamesPrivate;
    ListView listviewGamesPrivate;
    List<GameDB> gameDBListPrivate;
    FirebaseAuth mAuth;

    String location, spaces, date, time, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);

        mAuth = FirebaseAuth.getInstance();

        //getting the user currently signed in

        String uid = mAuth.getCurrentUser().getUid();

        menuDrawerLayout= findViewById(R.id.drawerMenu);
        menuToggle=new ActionBarDrawerToggle(MyGames.this, menuDrawerLayout,R.string.open,R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //reading in private games from the specific user logged in

        databaseGamesPrivate = FirebaseDatabase.getInstance().getReference("gamesPersonal").child(uid);

        listviewGamesPrivate = findViewById(R.id.listviewGamesPrivate);

        gameDBListPrivate = new ArrayList<>();

        listviewGamesPrivate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                GameDB game = gameDBListPrivate.get(i);

                location = game.getGameLocation();
                spaces = game.getGameSpaces();
                date = game.getGameDate();
                time = game.getGameTime();
                id = game.getGameID();

                Intent intent1 = new Intent(getApplicationContext(), MyGamesDetails.class);

                intent1.putExtra("location", location);
                intent1.putExtra("spaces", spaces);
                intent1.putExtra("date", date);
                intent1.putExtra("time", time);
                intent1.putExtra("id", id);

                startActivity(intent1);

            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        databaseGamesPrivate.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gameDBListPrivate.clear();

                for(DataSnapshot gameSnapshot: dataSnapshot.getChildren()){
                    GameDB game = gameSnapshot.getValue(GameDB.class);
                    gameDBListPrivate.add(game);
                }

                GamesList adapter = new GamesList(MyGames.this, gameDBListPrivate);
                listviewGamesPrivate.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Collections.reverse(gameDBListPrivate);
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
