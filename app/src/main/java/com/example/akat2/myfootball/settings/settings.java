package com.example.akat2.myfootball.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.SplashScreen.SplashScreen;
import com.example.akat2.myfootball.fixturesList.fixturesList;
import com.example.akat2.myfootball.listCompetitions.listCompetitions;

public class settings extends AppCompatActivity {

    Context context = this;
    Switch aSwitch;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        bottomNavigationView.setSelectedItemId(R.id.action_about);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(b){
                    SplashScreen.loadImages = false;
                }else{
                    SplashScreen.loadImages = true;
                }
                editor.putBoolean("loadImages",SplashScreen.loadImages);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                bottomNavigationView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch(item.getItemId()){
                            case R.id.action_fixtures:
                                Intent intent = new Intent(context, fixturesList.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.action_teams:
                                intent = new Intent(context, listCompetitions.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.action_about:
                                break;

                        }
                    }
                },300);
                return true;
            }
        });
    }

    void init(){
        aSwitch = (Switch) findViewById(R.id.loadImages);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

}
