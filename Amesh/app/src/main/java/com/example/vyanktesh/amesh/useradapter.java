package com.example.vyanktesh.amesh;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class useradapter extends RecyclerView.Adapter<useradapter.UserViewHolder> {

    List<TransactionentryModel> hurre;
    Context context;
    String partytype;
    List<String> Tids;
    public useradapter(List<TransactionentryModel> hurre, Context context, List<String> Tids,String partytype){
        this.hurre = hurre;
        this.context=context;
        this.Tids=Tids;
        this.partytype=partytype;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.newusersinglerow,viewGroup,false);
        UserViewHolder userViewHolder=new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, final int i) {

        if (partytype.equals("Reciever")){
            userViewHolder.Partyname.setText(hurre.get(i).investoredit);
        }else {

            userViewHolder.Partyname.setText(hurre.get(i).recieveredit);
        }
        userViewHolder.Partyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (partytype.equals("Reciever")){
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    profiledialog profiledialog=new profiledialog();
                    Bundle args=new Bundle();
                    args.putString("uidelo",hurre.get(i).investoruid);
                    profiledialog.setArguments(args);
                    profiledialog.show(fragmentManager,"ohno");
                }else {
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    profiledialog profiledialog=new profiledialog();
                    Bundle args=new Bundle();
                    args.putString("uidelo",hurre.get(i).recieveruid);
                    profiledialog.setArguments(args);
                    profiledialog.show(fragmentManager,"ohno");
                }
            }
        });
        userViewHolder.Maturitydate.setText(hurre.get(i).maturitydateedit);
        userViewHolder.Amount.setText(hurre.get(i).amountedit);
        userViewHolder.Amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();

                TransactionDetails transactionDetails=new TransactionDetails();
                transaction.add(R.id.userpanelrellayout,transactionDetails,"Itshalwa");
                Bundle args=new Bundle();
                args.putString("tidofcleckeditem",Tids.get(i));
//                Log.d("btidsvslue",Tids.get(i));
                args.putString("partytype",partytype);
                transactionDetails.setArguments(args);
                transaction.addToBackStack("userpaneltotransactiondetails");

                transaction.commit();
            }
        });
        userViewHolder.Rateofintrest.setText(hurre.get(i).roi);
        if (hurre.size()%2==0){
            if (i%2==0){
                userViewHolder.newuserSinglerow.setCardBackgroundColor(Color.parseColor("#6ab919"));
            }else userViewHolder.newuserSinglerow.setCardBackgroundColor(Color.parseColor("#6ab919"));
        }else {
            if (i%2==0){
                userViewHolder.newuserSinglerow.setCardBackgroundColor(Color.parseColor("#6ab919"));
            }else userViewHolder.newuserSinglerow.setCardBackgroundColor(Color.parseColor("#6ab919"));
        }
        userViewHolder.newuserSinglerow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                Viewhistory profilesharingdialog=new Viewhistory();
                Bundle argo=new Bundle();
                argo.putString("tidi",Tids.get(i).toString());
                profilesharingdialog.setArguments(argo);
                profilesharingdialog.show(fragmentManager,"fmi");
            }
        });
//        userViewHolder.a1.setText(hurre.get(i).entrydatieedit);
//        userViewHolder.a2.setText(hurre.get(i).maturitydateedit);
//        userViewHolder.a2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                Viewhistory profilesharingdialog=new Viewhistory();
//                Bundle argo=new Bundle();
//                argo.putString("tidi",Tids.get(i).toString());
//                profilesharingdialog.setArguments(argo);
//                profilesharingdialog.show(fragmentManager,"fmi");
//            }
//        });
//        userViewHolder.a3.setText(hurre.get(i).investoredit);
//        userViewHolder.a3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                profiledialog profiledialog=new profiledialog();
//                Bundle args=new Bundle();
//                args.putString("uidelo",hurre.get(i).investoruid);
//                profiledialog.setArguments(args);
//                profiledialog.show(fragmentManager,"ohno");
//
//            }
//        });
//        userViewHolder.a4.setText(hurre.get(i).recieveredit);
//        userViewHolder.a4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                profiledialog profiledialog=new profiledialog();
//                Bundle args=new Bundle();
//                args.putString("uidelo",hurre.get(i).recieveruid);
//                profiledialog.setArguments(args);
//                profiledialog.show(fragmentManager,"ohno");
//            }
//        });
//        userViewHolder.a5.setText(hurre.get(i).amountedit);
//        userViewHolder.a6.setText(hurre.get(i).roi);
//        userViewHolder.a7.setText(hurre.get(i).tds);
//        userViewHolder.a8.setText(hurre.get(i).spinnermode);
//        userViewHolder.a9.setText(hurre.get(i).chequenoedit);
//        userViewHolder.a10.setText(hurre.get(i).chequedateedit);
    }

    @Override
    public int getItemCount() {
        return hurre.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView Partyname,Maturitydate,Rateofintrest,Amount;
        CardView newuserSinglerow;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            Partyname=itemView.findViewById(R.id.parto);
            newuserSinglerow=itemView.findViewById(R.id.card_view);
            Maturitydate=itemView.findViewById(R.id.mda);
            Rateofintrest=itemView.findViewById(R.id.roli);
            Amount=itemView.findViewById(R.id.amo);
        }
    }
}
