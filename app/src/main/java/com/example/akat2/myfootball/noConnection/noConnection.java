package com.example.akat2.myfootball.noConnection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.utils.utils;

public class noConnection extends AppCompatActivity {

    Context context = this;
    SwipeRefreshLayout swipeRefreshLayout;
    Boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*getSupportActionBar().setDisplayOptions(getSupportActionBar().getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(getSupportActionBar().getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.mipmap.ic_refresh);
        imageView.setPadding(200,0,0,0);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        imageView.setLayoutParams(layoutParams);
        getSupportActionBar().setCustomView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setRefreshing(true);
                doUpdate();
            }
        });*/


        init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doUpdate();
            }
        });

    }

    void init(){
        connected = utils.isNetworkAvailable(context);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
    }

    void doUpdate(){
        connected = utils.isNetworkAvailable(context);
        if(connected){
            try {
                String clas = getIntent().getStringExtra("caller").replace(".class","");
                Intent intent = new Intent(context, Class.forName(clas));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } catch (ClassNotFoundException e) {
                Snackbar.make(findViewById(android.R.id.content), "Error", Snackbar.LENGTH_LONG).show();
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            swipeRefreshLayout.setRefreshing(true);
            doUpdate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
