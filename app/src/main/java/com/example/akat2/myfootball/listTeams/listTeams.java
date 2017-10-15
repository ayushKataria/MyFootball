package com.example.akat2.myfootball.listTeams;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.fixturesList.fixturesList;
import com.example.akat2.myfootball.listTeams.listTeams_interface.listTeam_interface;
import com.example.akat2.myfootball.listTeams.listTeams_model.listTeams_model;
import com.example.akat2.myfootball.listTeams.listTeams_parser.listTeams_parser;
import com.example.akat2.myfootball.settings.settings;
import com.example.akat2.myfootball.teamDetails.teamDetails;
import com.example.akat2.myfootball.utils.BottomNavigationViewHelper;

import java.util.ArrayList;

public class listTeams extends AppCompatActivity implements listTeam_interface {

    Context context = this;
    ListView teamlv;
    BottomNavigationView bottomNavigationView;
    ArrayList<listTeams_model> listTeams_models = new ArrayList<>();
    static listTeams instance;
    String compId;
    int y, initialY, scrollingY, scrolledY;
    boolean isVisible = true;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        teamlv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        bottomNavigationView.setSelectedItemId(R.id.action_teams);

        teamlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, teamDetails.class);
                intent.putExtra("selfURL", listTeams_models.get(i).getSelfURL());
                intent.putExtra("compId", compId);
                intent.putExtra("teamName",listTeams_models.get(i).getName());
                startActivity(intent);
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
                                overridePendingTransition(R.anim.slide_from_right, R.anim.silde_to_left);
                                break;
                            case R.id.action_teams:
                                break;
                            case R.id.action_about:
                                intent = new Intent(context, settings.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slide_from_right, R.anim.silde_to_left);
                                break;
                        }
                    }
                },300);
                return true;
            }
        });

        //Hiding bottom navigation drawer
        /*teamlv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_down);

                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                y = (int) motionEvent.getRawY();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        initialY = (int) motionEvent.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        scrollingY = (int) motionEvent.getRawY();

                        switch (view.getId()) {

                            case R.id.teamlv:
                                if(isVisible && initialY > scrolledY) {
                                    bottomNavigationView.startAnimation(slideDown);
                                    slideDown.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            bottomNavigationView.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    isVisible = false;
                                } else if(!isVisible && initialY < scrolledY){
                                    bottomNavigationView.startAnimation(slideUp);
                                    slideUp.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            bottomNavigationView.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });
                                    isVisible = true;
                                }
                                break;
                        }
                        scrolledY = scrollingY;
                        break;

                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });*/

        listTeams_parser listTeamsParser = new listTeams_parser(context, compId);
        listTeamsParser.execute();

    }

    void init(){
        teamlv = (ListView) findViewById(R.id.teamlv);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        compId = getIntent().getStringExtra("compId");
        instance = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public static listTeams getInstance(){
        return instance;
    }

    @Override
    public void teamListRecieved(ArrayList<listTeams_model> listTeamsModels) {

        teamlv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        listTeams_models = listTeamsModels;
        teamlv.setAdapter(new listTeamsAdapter(context, R.layout.teamlv_item, listTeamsModels));
    }

    @Override
    public void teamListFailed(String message) {
        teamlv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
