package com.example.akat2.myfootball.teamDetails.Fragments.table_fragment;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
import com.example.akat2.myfootball.teamDetails.teamDetails_model.table_model;
import com.example.akat2.myfootball.utils.SvgDecoder;
import com.example.akat2.myfootball.utils.SvgDrawableTranscoder;
import com.example.akat2.myfootball.utils.SvgSoftwareLayerSetter;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by akat2 on 11-08-2017.
 */

public class tableListAdapter extends RecyclerView.Adapter<ViewHolder>{

    Context context;
    ArrayList<table_model> tableModels;

    public tableListAdapter(Context context, ArrayList<table_model> tableModels){
        this.context = context;
        this.tableModels = tableModels;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return tableModels.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_item, parent, false);

        return new ViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        table_model tableModel = tableModels.get(position);
        holder.bindTable(tableModel);

    }

}
