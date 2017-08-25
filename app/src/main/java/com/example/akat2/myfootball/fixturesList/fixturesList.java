package com.example.akat2.myfootball.fixturesList;

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
import android.widget.ListView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.fixturesList.fixturesList_interface.fixturesList_interface;
import com.example.akat2.myfootball.fixturesList.fixturesList_model.fixturesList_model;
import com.example.akat2.myfootball.fixturesList.fixturesList_parser.fixturesList_parser;
import com.example.akat2.myfootball.listCompetitions.listCompetitions;
import com.example.akat2.myfootball.settings.settings;
import com.github.badoualy.datepicker.DatePickerTimeline;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class fixturesList extends AppCompatActivity implements fixturesList_interface {

    static fixturesList l1;
    BottomNavigationView bottomNavigationView;
    Context context = this;
    ListView fixtureslv;
    int y, initialY, scrollingY, scrolledY;
    boolean isVisible = true;
    DatePickerTimeline pickerTimeline;
    ArrayList<fixturesList_model> fixturesListModelArrayList = new ArrayList<>();
    public static String dateStart, dateEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        bottomNavigationView.setSelectedItemId(R.id.action_fixtures);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                bottomNavigationView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch(item.getItemId()){
                            case R.id.action_fixtures:
                                break;
                            case R.id.action_teams:
                                Intent intent = new Intent(context, listCompetitions.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.action_about:
                                intent = new Intent(context, settings.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                },300);
                return true;
            }
        });

        //Hiding bottom navigation drawer
        fixtureslv.setOnTouchListener(new View.OnTouchListener() {
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

                            case R.id.fixtureslv:
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

        pickerTimeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                DecimalFormat format = new DecimalFormat("00");
                String twodigitMonth = format.format(month+1);
                String twoDigitDay = format.format(day);
                String selDate = year + "-" + twodigitMonth + "-" + twoDigitDay;
                ArrayList<fixturesList_model> fixturesList_models = new ArrayList<>();
                for(int i=0; i<fixturesListModelArrayList.size(); i++){
                    fixturesList_model fixturesModel = fixturesListModelArrayList.get(i);
                    String dateTime[] = fixturesModel.getDate().split("T");
                    if(dateTime[0].equals(selDate)){
                        fixturesList_models.add(fixturesModel);
                    }
                }

                fixtureslv.setAdapter(new fixturesListAdapter(context,R.layout.fixtureslv_item2,fixturesList_models));
            }
        });

        fixturesList_parser parser = new fixturesList_parser(context);
        parser.execute();

    }

    void init(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        fixtureslv = (ListView) findViewById(R.id.fixtureslv);
        pickerTimeline = (DatePickerTimeline) findViewById(R.id.datePicker);
        l1 = this;
    }

    @Override
    public void fixturesListRecieved(ArrayList<fixturesList_model> fixturesListModels) {
        String dStart[] = dateStart.split("-");
        String dEnd[] = dateEnd.split("-");
        pickerTimeline.setFirstVisibleDate(Integer.parseInt(dStart[0]),Integer.parseInt(dStart[1])-1,Integer.parseInt(dStart[2]));
        pickerTimeline.setLastVisibleDate(Integer.parseInt(dEnd[0]),Integer.parseInt(dEnd[1])-1,Integer.parseInt(dEnd[2]));
        pickerTimeline.setSelectedDate(Integer.parseInt(dStart[0]),Integer.parseInt(dStart[1])-1,Integer.parseInt(dStart[2]));

        fixturesListModelArrayList = fixturesListModels;
        ArrayList<fixturesList_model> fixturesList_models = new ArrayList<>();
        for(int i=0; i<fixturesListModels.size(); i++){
            fixturesList_model fixturesModel = fixturesListModels.get(i);
            String dateTime[] = fixturesModel.getDate().split("T");
            if(dateTime[0].equals(dateStart)){
                fixturesList_models.add(fixturesModel);
            }
        }

        fixtureslv.setAdapter(new fixturesListAdapter(context,R.layout.fixtureslv_item2,fixturesList_models));
    }

    @Override
    public void fixturesListFailed(String messsage) {
        Snackbar.make(findViewById(android.R.id.content),messsage,Snackbar.LENGTH_LONG).show();
    }

    public static fixturesList getInstance(){
        return l1;
    }
}
