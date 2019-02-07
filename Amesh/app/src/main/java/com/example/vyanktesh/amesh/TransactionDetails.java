package com.example.vyanktesh.amesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransactionDetails extends Fragment {
    TextView partyname,transactionstartdate,maturitydate,roi,amount,tds,intrestamont,intrestchequeno,chequedate;
    Button share,viewhistory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.transactiondetails, container, false);
        view.getRootView().setClickable(true);
        view.getRootView().setFocusable(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        partyname=getActivity().findViewById(R.id.Partadate);
        transactionstartdate=getActivity().findViewById(R.id.entradate);
        maturitydate=getActivity().findViewById(R.id.martadate);
        amount=getActivity().findViewById(R.id.amountaedit);
        roi=getActivity().findViewById(R.id.roiaedit);
        tds=getActivity().findViewById(R.id.tdsaedit);
        intrestamont=getActivity().findViewById(R.id.intrestamountaedit);
        intrestchequeno=getActivity().findViewById(R.id.intrestchequenoaedit);
        chequedate=getActivity().findViewById(R.id.chequedateaedit);
        share=getActivity().findViewById(R.id.sharea);
        viewhistory=getActivity().findViewById(R.id.viewhistoriya);
        final String tidofcleckeditem=getArguments().getString("tidofcleckeditem");
        final String partytype=getArguments().getString("partytype");
        readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(tidofcleckeditem), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                final TransactionentryModel transactionentryModel=dataSnapshot.getValue(TransactionentryModel.class);
                if (partytype.equals("Recievers")){
                    partyname.setText(transactionentryModel.investoredit);
                    Log.d("a1",transactionentryModel.investoredit);
                }else {
                    Log.d("a1",transactionentryModel.recieveredit);
                    partyname.setText(transactionentryModel.recieveredit);}
                transactionstartdate.setText(transactionentryModel.entrydatieedit);

                maturitydate.setText(transactionentryModel.maturitydateedit);
                amount.setText(transactionentryModel.amountedit);
                roi.setText(transactionentryModel.roi);
                tds.setText(transactionentryModel.tds);
                intrestamont.setText("Not availabe");
                intrestchequeno.setText(transactionentryModel.chequenoedit);
                chequedate.setText(transactionentryModel.chequedateedit);
                viewhistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager=getFragmentManager();
                        Viewhistory profilesharingdialog=new Viewhistory();
                        Bundle argo=new Bundle();
                argo.putString("tidi",tidofcleckeditem);
                profilesharingdialog.setArguments(argo);
                profilesharingdialog.show(fragmentManager,"fmi");
//                        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                Viewhistory profilesharingdialog=new Viewhistory();
//                Bundle argo=new Bundle();
//                argo.putString("tidi",Tids.get(i).toString());
//                profilesharingdialog.setArguments(argo);
//                profilesharingdialog.show(fragmentManager,"fmi");
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String senddata="Party Name   :   "+partyname.getText()+"\nStart Date   :   "+transactionentryModel.entrydatieedit+"\nMaturity Date"+transactionentryModel.maturitydateedit+"\nAmount"+transactionentryModel.amountedit+"\nR.O.I."+transactionentryModel.roi +"\nT.D.S."+transactionentryModel.tds +"\nInterest Amount"+transactionentryModel.intrestedit +"\nCheque Date"+transactionentryModel.chequedateedit;
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, senddata);
                        sendIntent.setType("text/plain");
                        getActivity().startActivity(sendIntent);

                    }
                });

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });


    }

    public void readdata(final DatabaseReference reference, final OnGetDataListner listner){
        listner.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listner.onSuccess(dataSnapshot);
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        listner.onSuccess(dataSnapshot);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        listner.onFailure();
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
