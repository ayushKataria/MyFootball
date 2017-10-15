package com.example.akat2.myfootball.listCompetitionsTeams;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akat2.myfootball.R;
import com.example.akat2.myfootball.listCompetitionsTeams.listCompetitions_model.listCompetitions_model;
import com.example.akat2.myfootball.utils.utils;

import java.util.ArrayList;

/**
 * Created by akat2 on 02-08-2017.
 */

public class listCompetitionsAdapter extends BaseAdapter {

    Context context;
    int resourceId;
    ArrayList<listCompetitions_model> listCompetitionsModels;

    public listCompetitionsAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<listCompetitions_model> listCompetitionsModels) {
        this.context = context;
        resourceId = resource;
        this.listCompetitionsModels = listCompetitionsModels;
    }

    @Override
    public int getCount() {
        return listCompetitionsModels.size();
    }

    @Override
    public Object getItem(int i) {
        return listCompetitionsModels.get(i);
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
        TextView caption = (TextView) convertView.findViewById(R.id.caption);
        TextView league = (TextView) convertView.findViewById(R.id.league);
        ImageView logo = (ImageView) convertView.findViewById(R.id.compLogo);
        int id = Integer.parseInt(listCompetitionsModels.get(i).getId());
        int logoId = utils.getCompetitionLogo(id);
        if(logoId==-1){
            league.setVisibility(View.VISIBLE);
            logo.setVisibility(View.GONE);
        }else{
            league.setVisibility(View.GONE);
            logo.setVisibility(View.VISIBLE);
            logo.setImageResource(logoId);
        }
        caption.setText(listCompetitionsModels.get(i).getCaption());
        league.setText(listCompetitionsModels.get(i).getLeague());

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
