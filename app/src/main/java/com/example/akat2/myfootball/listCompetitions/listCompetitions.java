package com.example.akat2.myfootball.listCompetitions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.fixturesList.fixturesList;
import com.example.akat2.myfootball.listCompetitions.listCompetitions_interface.listCompetitions_interface;
import com.example.akat2.myfootball.listCompetitions.listCompetitions_model.listCompetitions_model;
import com.example.akat2.myfootball.listCompetitions.listCompetitions_parser.listCompetitions_parser;
import com.example.akat2.myfootball.listTeams.listTeams;

import java.util.ArrayList;

public class listCompetitions extends AppCompatActivity implements listCompetitions_interface {

    Context context = this;
    ListView complv;
    BottomNavigationView bottomNavigationView;
    static listCompetitions l1;
    int y, initialY, scrollingY, scrolledY;
    boolean isVisible = true;
    ArrayList<listCompetitions_model> listCompetitions_models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_competitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        bottomNavigationView.setSelectedItemId(R.id.action_teams);

        complv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, listTeams.class);
                intent.putExtra("compId", listCompetitions_models.get(i).getId());
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
                                break;
                            case R.id.action_teams:
                                break;
                        }
                    }
                },300);
                return true;
            }
        });

        //Hiding bottom navigation drawer
        complv.setOnTouchListener(new View.OnTouchListener() {
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

                            case R.id.complv:
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
        });


        listCompetitions_parser listCompetitions_parser = new listCompetitions_parser(context);
        listCompetitions_parser.execute();
    }

    void init(){
        complv = (ListView) findViewById(R.id.complv);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        l1 = this;
    }

    public static listCompetitions getInstance(){
        return l1;
    }

    @Override
    public void competitionListRecieved(ArrayList<listCompetitions_model> listCompetionsModels) {

        listCompetitions_models = listCompetionsModels;
        complv.setAdapter(new listCompetitionsAdapter(context, R.layout.complv_item, listCompetionsModels));
    }

    @Override
    public void competitionListFailed(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_competitions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
