package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class FindGame extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;
    private List<Game> myGames = new ArrayList<Game>();
//    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        populateGamesList();
        populateListView();
//        onStart();

        menuDrawerLayout=(DrawerLayout) findViewById(R.id.drawerMenu);
        menuToggle=new ActionBarDrawerToggle(FindGame.this, menuDrawerLayout,R.string.open,R.string.close);
        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    public void onStart(){
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if(currentUser == null){
//
//            startActivity(new Intent(this, SignUpActivity.class));
//            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
//            finish();
//
//        }
//
//    }
//
//    private void sendToSignUp(){
//
//        startActivity(new Intent(this, SignUpActivity.class));
//        Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
//        finish();
//
//    }

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
            startActivity(new Intent(this, SignUpActivity.class));
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show();
            finish();
        }

        return false;
    }

    private void populateGamesList() {

        myGames.add(new Game("Oliver Baird", "Saturday 10am", R.drawable.oliver,"5aside at Civil"));
        myGames.add(new Game("Adam Ewart", "Friday 9pm", R.drawable.adam, "5aside at PEC"));
    }

    private void populateListView() {
        ArrayAdapter<Game> adapterGames = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.gamesListView);
        list.setAdapter(adapterGames);
    }

    private class MyListAdapter extends ArrayAdapter<Game> {
        public MyListAdapter() {
            super(FindGame.this, R.layout.game_view, myGames);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //making sure there is a view to show

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.game_view, parent, false);
            }

            //populating the UI design

            //find the game to show

            Game currentGame = myGames.get(position);

            //fill the image

            ImageView imageViewGame = (ImageView) itemView.findViewById(R.id.item_icon);
            imageViewGame.setImageResource(currentGame.getIconID());

            //fill the username

            TextView textViewGameUsername = (TextView) itemView.findViewById(R.id.item_txtUsername);
            textViewGameUsername.setText(currentGame.username());

            //fill the time

            TextView textViewGameTime = (TextView) itemView.findViewById(R.id.item_txtTime);
            textViewGameTime.setText(currentGame.time());

            //fill the description

            TextView textViewGameDescription = (TextView) itemView.findViewById(R.id.item_txtDescription);
            textViewGameDescription.setText(currentGame.getDescription());

            return itemView;
        }
    }
}