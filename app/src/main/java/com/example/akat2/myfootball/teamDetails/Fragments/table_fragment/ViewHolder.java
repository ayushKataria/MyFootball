package com.example.akat2.myfootball.teamDetails.Fragments.table_fragment;

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
import com.example.akat2.myfootball.teamDetails.teamDetails_model.table_model;
import com.example.akat2.myfootball.utils.SvgDecoder;
import com.example.akat2.myfootball.utils.SvgDrawableTranscoder;
import com.example.akat2.myfootball.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;

/**
 * Created by akat2 on 19-08-2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView pos, name, wins, played, draws, losses, points, goalsDiff;

    public ViewHolder(Context context,View view) {
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
    }
}
