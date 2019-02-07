package com.example.vyanktesh.amesh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class Searchadapter extends RecyclerView.Adapter<Searchadapter.SearchViewHolder> {
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "MainActivity";
    List<TransactionentryModel> hurre;
    Context context;
    List<String> Tids;
    Dialog mydialog;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Investors");
    DatabaseReference PreviousMaturitydates=FirebaseDatabase.getInstance().getReference("PreMaturitydates");
    DatabaseReference TransactionEnteries=FirebaseDatabase.getInstance().getReference("Transaction Enteries");
    DatabaseReference MaturityDates=FirebaseDatabase.getInstance().getReference("Maturity Dates");


    public Searchadapter(List<TransactionentryModel> hurre,Context context,List<String> Tids) {
        this.hurre = hurre;
        this.context=context;
        this.Tids=Tids;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.newsinglerowofsearch,viewGroup,false);
        SearchViewHolder searchViewHolder=new SearchViewHolder(view);

        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder searchViewHolder, final int i) {
        searchViewHolder.a1.setText(hurre.get(i).entrydatieedit);
        searchViewHolder.a2.setText(hurre.get(i).maturitydateedit);
        searchViewHolder.a2.setOnClickListener(new View.OnClickListener() {
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
        searchViewHolder.a3.setText(hurre.get(i).investoredit);
        searchViewHolder.a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                profiledialog profiledialog=new profiledialog();
                Bundle args=new Bundle();
                args.putString("uidelo",hurre.get(i).investoruid);
                profiledialog.setArguments(args);
                profiledialog.show(fragmentManager,"ohno");

                }
        });
        searchViewHolder.a4.setText(hurre.get(i).recieveredit);
        searchViewHolder.a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                profiledialog profiledialog=new profiledialog();
                Bundle args=new Bundle();
                args.putString("uidelo",hurre.get(i).recieveruid);
                profiledialog.setArguments(args);
                profiledialog.show(fragmentManager,"ohno");
            }
        });
        searchViewHolder.a5.setText(hurre.get(i).amountedit);
        searchViewHolder.a6.setText(hurre.get(i).roi);
        searchViewHolder.a7.setText(hurre.get(i).tds);
        searchViewHolder.a8.setText(hurre.get(i).spinnermode);
        searchViewHolder.a9.setText(hurre.get(i).chequenoedit);
        searchViewHolder.a10.setText(hurre.get(i).chequedateedit);
//        if (hurre.get(i).status.equalsIgnoreCase("Active")){
//            searchViewHolder.toggleButton.setChecked(true);
//        }else searchViewHolder.toggleButton.setChecked(false);
//        searchViewHolder.toggleButton.setText(hurre.get(i).status);
        searchViewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TransactionEntryNew.class);
//                intent.putExtra("investornamehidden",hurre.get(i).investoredit);
//                intent.putExtra("recievernamehidden",hurre.get(i).recieveredit);
//                intent.putExtra("transactionidhidden",Tids.get(i));
//                intent.putExtra("maturitydateid",hurre.get(i).maturitydateid);
//                intent.putExtra("amountid",hurre.get(i).amountid);
//                intent.putExtra("roiid",hurre.get(i).roiid);
//                intent.putExtra("entryid",hurre.get(i).entryid);
//                intent.putExtra("investoruid",hurre.get(i).investoruid);
//                intent.putExtra("recieveruid",hurre.get(i).recieveruid);
//                intent.putExtra("maturitydatehidden",hurre.get(i).maturitydateedit);
//                intent.putExtra("entrydatehidden",hurre.get(i).entrydatieedit);
//                intent.putExtra("amounthidden",hurre.get(i).amountedit);
//                intent.putExtra("roihidden",hurre.get(i).roi);
                intent.putExtra("Tids",Tids.get(i));
                intent.putExtra("verify","2");
                context.startActivity(intent);
////                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
////                    @Override
////                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
////                        month = month + 1;
////                        Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
////
////                        String date = day + "-" + month + "-" + year;
////                        searchViewHolder.a2.setText(date);
////                    }
////                };
////                newdafor();
            }
        });
        searchViewHolder.Renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TransactionEntryNew.class);
//                intent.putExtra("investornamehidden",hurre.get(i).investoredit);
//                intent.putExtra("recievernamehidden",hurre.get(i).recieveredit);
//                intent.putExtra("transactionidhidden",Tids.get(i));
//
//                intent.putExtra("investoruid",hurre.get(i).investoruid);
//                intent.putExtra("recieveruid",hurre.get(i).recieveruid);
//                intent.putExtra("maturitydatehidden",hurre.get(i).maturitydateedit);
//                intent.putExtra("entrydatehidden",hurre.get(i).entrydatieedit);
//                intent.putExtra("amounthidden",hurre.get(i).amountedit);
//                intent.putExtra("roihidden",hurre.get(i).roi);
                intent.putExtra("verify","3");
                intent.putExtra("Tids",Tids.get(i));

//                intent.putExtra("hiddeninvestorgroupid",hurre.get(i).investorgroupuid);
//                intent.putExtra("hiddenrecievergroupid",hurre.get(i).recievergroupuid);
                context.startActivity(intent);
////                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
////                    @Override
////                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
////                        month = month + 1;
////                        Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
////
////                        String date = day + "-" + month + "-" + year;
////                        searchViewHolder.a2.setText(date);
////                    }
////                };
////                newdafor();
            }
        });
//        searchViewHolder.submitedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String matura=hurre.get(i).maturitydateedit;
////                PreviousMaturitydates.child(Tids.get(i).toString()).push().setValue(matura);
//                TransactionEnteries.child(Tids.get(i).toString()).child("status").setValue(searchViewHolder.toggleButton.getText().toString());
////                TransactionEnteries.child(Tids.get(i).toString()).child("maturitydateedit").setValue(searchViewHolder.a2.getText().toString());
////                MaturityDates.child(matura.toString()).child(hurre.get(i).maturitydateid).setValue(null);
////                String ama = MaturityDates.child(searchViewHolder.a2.getText().toString()).push().getKey();
////                MaturityDates.child(searchViewHolder.a2.getText().toString()).child(ama).setValue(Tids.get(i).toString());
////                TransactionEnteries.child(Tids.get(i).toString()).child("maturitydateid").setValue(ama);
////                Toast.makeText(context,"Transaction Updated",Toast.LENGTH_LONG).show();
//                searchViewHolder.submitedit.setEnabled(false);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return hurre.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
//        RelativeLayout uho;
        ToggleButton toggleButton;
        Button Renew,Edit;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            a1=itemView.findViewById(R.id.edf);
//            uho=itemView.findViewById(R.id.uho);
            a2=itemView.findViewById(R.id.mdf);
            a3=itemView.findViewById(R.id.invf);
            a4=itemView.findViewById(R.id.recf);
            a5=itemView.findViewById(R.id.amtf);
            a6=itemView.findViewById(R.id.roif);
            a7=itemView.findViewById(R.id.tdsf);
            a8=itemView.findViewById(R.id.modf);
            a9=itemView.findViewById(R.id.cnof);
            a10=itemView.findViewById(R.id.cdtf);
            Edit=itemView.findViewById(R.id.edit);
            Renew=itemView.findViewById(R.id.renew);
//            toggleButton=itemView.findViewById(R.id.togleactive);
//            EditMaturitydate=itemView.findViewById(R.id.madaedit);
//            submitedit=itemView.findViewById(R.id.submitedit);
        }
    }
    public void newdafor() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
