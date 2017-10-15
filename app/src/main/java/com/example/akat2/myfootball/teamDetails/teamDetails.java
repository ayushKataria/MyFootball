package com.example.akat2.myfootball.teamDetails;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.SplashScreen.SplashScreen;
import com.example.akat2.myfootball.teamDetails.teamDetails_interface.teamDetails_interface;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.teamDetails_model;
import com.example.akat2.myfootball.teamDetails.teamDetails_parser.teamDetails_parser;
import com.example.akat2.myfootball.utils.SvgDecoder;
import com.example.akat2.myfootball.utils.SvgDrawableTranscoder;
import com.example.akat2.myfootball.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;

public class teamDetails extends AppCompatActivity implements teamDetails_interface {

    Context context = this;
    String teamURL;
    ViewPager viewPager;
    TabLayout tabLayout;
    private TabsPagerAdapter mAdapter;
    static teamDetails l1;
    TextView name;
    public static LinearLayout linearLayout;
    ImageView crest;
    public static String fixturesURL, playerURL, compId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("teamName"));

        init();

        tabLayout.addTab(tabLayout.newTab().setText("Fixtures"));
        tabLayout.addTab(tabLayout.newTab().setText("Table"));
        tabLayout.addTab(tabLayout.newTab().setText("Player"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        teamDetails_parser parser = new teamDetails_parser(context, getIntent().getStringExtra("selfURL"));
        parser.execute();
    }

    void init(){
        teamURL = getIntent().getStringExtra("selfURL");
        compId = getIntent().getStringExtra("compId");
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mAdapter=new TabsPagerAdapter(getSupportFragmentManager(), context);
       /* name =(TextView) findViewById(R.id.name);*/
        crest = (ImageView) findViewById(R.id.crest);
        linearLayout = (LinearLayout) findViewById(R.id.line1);
        l1 = this;
    }

    public static teamDetails getInstance(){
        return l1;
    }

    @Override
    public void teamDetailsSuccessful(teamDetails_model teamDetailsModel) {
        fixturesURL = teamDetailsModel.getFixturesURL();
        playerURL = teamDetailsModel.getPlayerURL();
//        name.setText(teamDetailsModel.getName());
        String badgeURL = teamDetailsModel.getCrestURL();
        if(SplashScreen.loadImages) {
            if (badgeURL.substring(badgeURL.lastIndexOf('.') + 1).equals("svg")) {

                GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(context)
                        .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                        .from(Uri.class)
                        .as(SVG.class)
                        .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                        .sourceEncoder(new StreamEncoder())
                        .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                        .decoder(new SvgDecoder())
                        .animate(android.R.anim.fade_in)
                        .listener(new SvgSoftwareLayerSetter<Uri>());

                Uri uri = Uri.parse(badgeURL);
                requestBuilder
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(crest);
            } else if (badgeURL.substring(badgeURL.lastIndexOf('.') + 1).equals("png")) {
                Glide.with(context).load(badgeURL).into(crest);
            }
        }
    }

    @Override
    public void teamDetailsFailed(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
