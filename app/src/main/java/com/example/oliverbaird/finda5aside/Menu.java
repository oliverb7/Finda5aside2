package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {


    private ImageButton findGameImg;
    private ImageButton settingsImg;
    private ImageButton profileImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickFindGame();
        clickProfile();
        clickSettings();

    }

    public void clickFindGame() {

        findGameImg = (ImageButton) findViewById(R.id.findGameImg);
        findGameImg.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent findG = new Intent(Menu.this, FindGame.class);
                startActivity(findG);
            }
        });
    }

    public void clickProfile() {

        profileImg = (ImageButton) findViewById(R.id.profileImg);
        profileImg.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){

                Intent profileClick = new Intent(Menu.this, Profile.class);
                startActivity(profileClick);
            }
        });
    }

    public void clickSettings(){

        settingsImg = (ImageButton) findViewById(R.id.settingsImg);
        settingsImg.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){

                Intent settingsClick = new Intent(Menu.this, Settings.class);
                startActivity(settingsClick);
            }
        });
    }
}
