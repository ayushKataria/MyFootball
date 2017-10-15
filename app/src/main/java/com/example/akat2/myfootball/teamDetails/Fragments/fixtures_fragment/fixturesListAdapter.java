package com.example.akat2.myfootball.teamDetails.Fragments.fixtures_fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.teamDetails.teamDetails_model.fixtures_model;

import java.util.ArrayList;

/**
 * Created by akat2 on 05-08-2017.
 */

public class fixturesListAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    ArrayList<fixtures_model> fixturesModels;

    public fixturesListAdapter(Context context, ArrayList<fixtures_model> fixturesModels){
        this.context = context;
        this.fixturesModels = fixturesModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fixureslv_item, parent, false);
        return new ViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        fixtures_model fixturesModel = fixturesModels.get(holder.getAdapterPosition());
        holder.bindFixtures(fixturesModel);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return fixturesModels.size();
    }

}
