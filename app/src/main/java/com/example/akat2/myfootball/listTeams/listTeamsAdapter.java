package com.example.akat2.myfootball.listTeams;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.akat2.myfootball.listTeams.listTeams_model.listTeams_model;
import com.example.akat2.myfootball.utils.SvgDecoder;
import com.example.akat2.myfootball.utils.SvgDrawableTranscoder;
import com.example.akat2.myfootball.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by akat2 on 04-08-2017.
 */

public class listTeamsAdapter extends BaseAdapter {

    Context context;
    int resourceId;
    ArrayList<listTeams_model> listTeamsModels;

    public listTeamsAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<listTeams_model> listTeamsModels) {
        this.context = context;
        resourceId = resource;
        this.listTeamsModels = listTeamsModels;
    }

    @Override
    public int getCount() {
        return listTeamsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return listTeamsModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
        }
        ImageView crest = (ImageView) convertView.findViewById(R.id.crest);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        name.setText(listTeamsModels.get(i).getName());
        String badgeURL = listTeamsModels.get(i).getCrestURL();
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
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
