package com.example.vyanktesh.amesh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ProgrammingViewHolder> {




    Context context;
    List<NewgroupModel> listofgroupis;

    public GroupAdapter(Context context, List<NewgroupModel> listofgroupis) {
        this.context = context;
        this.listofgroupis = listofgroupis;
    }


    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.singlerowingrouplist,viewGroup,false);
        return new ProgrammingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder programmingViewHolder, int i) {
        programmingViewHolder.groupadminnumber.setText(listofgroupis.get(i).getGroupadminmobileNumber());
        programmingViewHolder.groupadminnamei.setText(listofgroupis.get(i).getGroupname());

    }

    @Override
    public int getItemCount() {
        Log.d("group size",""+listofgroupis.size());
        return listofgroupis.size();

    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groupadminnamei,groupadminnumber;
        public ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            groupadminnamei=itemView.findViewById(R.id.groupadminnamei);
            groupadminnumber=itemView.findViewById(R.id.groupadminnumber);
        }

        @Override
        public void onClick(View v) {
            group group=new group();
            MasterClass masterClass=new MasterClass();
            group.passgroupname(listofgroupis.get(getLayoutPosition()).getGroupname());

        }
    }
}
