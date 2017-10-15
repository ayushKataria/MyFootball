package com.example.akat2.myfootball.listCompetitionsDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import com.example.akat2.myfootball.competitionDetails.CompetitionDetails;
import com.example.akat2.myfootball.fixturesList.fixturesList;
import com.example.akat2.myfootball.listCompetitionsDetails.listCompetitions_interface.listCompetitions_interface;
import com.example.akat2.myfootball.listCompetitionsDetails.listCompetitions_model.listCompetitions_model;
import com.example.akat2.myfootball.listCompetitionsDetails.listCompetitions_parser.listCompetitions_parser;
import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitionsTeams;
import com.example.akat2.myfootball.listTeams.listTeams;
import com.example.akat2.myfootball.settings.settings;
import com.example.akat2.myfootball.utils.BottomNavigationViewHelper;

import java.util.ArrayList;

public class listCompetitionsDetails extends AppCompatActivity implements listCompetitions_interface {

    Context context = this;
    ListView complv;
    BottomNavigationView bottomNavigationView;
    static listCompetitionsDetails l1;
    int y, initialY, scrollingY, scrolledY;
    boolean isVisible = true;
    ArrayList<listCompetitions_model> listCompetitions_models = new ArrayList<>();
    ProgressBar progressBar;
    String fromActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_competitions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        complv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        bottomNavigationView.setSelectedItemId(R.id.action_competition);

        complv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, CompetitionDetails.class);
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
                                overridePendingTransition(R.anim.slide_from_right, R.anim.silde_to_left);
                                break;
                            case R.id.action_competition:
                                break;
                            case R.id.action_teams:
                                intent = new Intent(context, listCompetitionsTeams.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slide_from_right, R.anim.silde_to_left);
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
        /*complv.setOnTouchListener(new View.OnTouchListener() {
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
        });*/


        listCompetitions_parser listCompetitions_parser = new listCompetitions_parser(context);
        listCompetitions_parser.execute();
    }

    void init(){
        complv = (ListView) findViewById(R.id.complv);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        l1 = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public static listCompetitionsDetails getInstance(){
        return l1;
    }

    @Override
    public void competitionListRecieved(ArrayList<listCompetitions_model> listCompetionsModels) {

        complv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        for(int i=0;i<listCompetionsModels.size();i++){
            listCompetitions_model listCompetitionsModel = listCompetionsModels.get(i);
            if(!(listCompetitionsModel.getCaption().equals("DFB-Pokal 2017/18")
                    ||listCompetitionsModel.getCaption().equals("Champions League 2017/18")
                    ||listCompetitionsModel.getCaption().equals("Australian A-League"))){
                listCompetitions_models.add(listCompetitionsModel);
            }
        }
        complv.setAdapter(new listCompetitionsAdapter(context, R.layout.complv_item, listCompetitions_models));
    }

    @Override
    public void competitionListFailed(String message) {
        complv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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
