package com.example.vyanktesh.amesh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Rachistoryadapter extends RecyclerView.Adapter<Rachistoryadapter.ReachisViewholder> {
    List<TransactionentryModel> lisso=new ArrayList<>();
    public Rachistoryadapter(List<TransactionentryModel> lisso){
        this.lisso=lisso;
    }
    @NonNull
    @Override
    public ReachisViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.usersinglerow,viewGroup,false);
        Rachistoryadapter.ReachisViewholder earchViewHolder=new Rachistoryadapter.ReachisViewholder(view);
        return earchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReachisViewholder reachisViewholder, int i) {
        reachisViewholder.a1.setText(lisso.get(i).entrydatieedit);
        reachisViewholder.a2.setText(lisso.get(i).maturitydateedit);

        reachisViewholder.a3.setText(lisso.get(i).investoredit);

        reachisViewholder.a4.setText(lisso.get(i).recieveredit);

        reachisViewholder.a5.setText(lisso.get(i).amountedit);
        reachisViewholder.a6.setText(lisso.get(i).roi);
        reachisViewholder.a7.setText(lisso.get(i).tds);
        reachisViewholder.a8.setText(lisso.get(i).spinnermode);
        reachisViewholder.a9.setText(lisso.get(i).chequenoedit);
        reachisViewholder.a10.setText(lisso.get(i).chequedateedit);

    }

    @Override
    public int getItemCount() {
        return lisso.size();
    }

    public class ReachisViewholder extends RecyclerView.ViewHolder {
        TextView a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
        public ReachisViewholder(@NonNull View itemView) {
            super(itemView);
            a1=itemView.findViewById(R.id.edf);
            a2=itemView.findViewById(R.id.mdf);
            a3=itemView.findViewById(R.id.invf);
            a4=itemView.findViewById(R.id.recf);
            a5=itemView.findViewById(R.id.amtf);
            a6=itemView.findViewById(R.id.roif);
            a7=itemView.findViewById(R.id.tdsf);
            a8=itemView.findViewById(R.id.modf);
            a9=itemView.findViewById(R.id.cnof);
            a10=itemView.findViewById(R.id.cdtf);
        }
    }
}
