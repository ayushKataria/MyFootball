package com.example.akat2.myfootball.competitionDetails.Fragments.compTable;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.SplashScreen.SplashScreen;
import com.example.akat2.myfootball.competitionDetails.competitionDetails_model.table_model;
import com.example.akat2.myfootball.utils.SvgDecoder;
import com.example.akat2.myfootball.utils.SvgDrawableTranscoder;
import com.example.akat2.myfootball.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;

/**
 * Created by akat2 on 10-10-2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView pos, name, wins, played, draws, losses, points, goalsDiff;
    private ImageView crest;

    public ViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        pos = (TextView) view.findViewById(R.id.pos);
        name = (TextView) view.findViewById(R.id.name);
        played = (TextView) view.findViewById(R.id.played);
        wins = (TextView) view.findViewById(R.id.wins);
        draws = (TextView) view.findViewById(R.id.draws);
        losses = (TextView) view.findViewById(R.id.losses);
        goalsDiff = (TextView) view.findViewById(R.id.goalDiff);
        points = (TextView) view.findViewById(R.id.points);
        crest = (ImageView) view.findViewById(R.id.crest);
    }

    public void bindTable(table_model tableModel){
        pos.setText(tableModel.getPosition());
        name.setText(tableModel.getTeamName());
        played.setText(tableModel.getPlayedGames());
        wins.setText(tableModel.getWins());
        draws.setText(tableModel.getDraws());
        losses.setText(tableModel.getLosses());
        goalsDiff.setText(tableModel.getGoalsDifference());
        points.setText(tableModel.getPoints());

        String badgeURL = tableModel.getCrestURL();
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
                        .placeholder(R.mipmap.ic_badge)
                        .error(R.mipmap.ic_badge)
                        .override(50,50)
                        .into(crest);
            } else if (badgeURL.substring(badgeURL.lastIndexOf('.') + 1).equals("png")) {
                Glide.with(context)
                        .load(badgeURL)
                        .placeholder(R.mipmap.ic_badge)
                        .error(R.mipmap.ic_badge)
                        .override(50,50)
                        .into(crest);
            }
        }
    }
}
