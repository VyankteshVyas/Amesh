package com.example.vyanktesh.amesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

public class profiledialog extends DialogFragment {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Profiles");
    Button share;
    TextView pnedit,paedit,pantdit,bantedit,banotedit,banatedit,bbtedit,ifstedit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.profiledialog,null);
        share=rootview.findViewById(R.id.sharu);
        pnedit=rootview.findViewById(R.id.pnedit);
        paedit=rootview.findViewById(R.id.paedit);
        pantdit=rootview.findViewById(R.id.pantdit);
        bantedit=rootview.findViewById(R.id.bantedit);
        banotedit=rootview.findViewById(R.id.banotedit);
        banatedit=rootview.findViewById(R.id.banatedit);
        bbtedit=rootview.findViewById(R.id.bbtedit);
        ifstedit=rootview.findViewById(R.id.ifstedit);
        String uid=getArguments().getString("uidelo");
        Log.d("usid",uid);

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MasterClass masterClass1=dataSnapshot.getValue(MasterClass.class);
                Log.d("maspar",""+dataSnapshot.child("partyName").getValue(String.class));
                pnedit.setText(dataSnapshot.child("partyName").getValue(String.class));
                paedit.setText(dataSnapshot.child("address").getValue(String.class));
                pantdit.setText(dataSnapshot.child("panNo").getValue(String.class));
                bantedit.setText(dataSnapshot.child("accountname").getValue(String.class));
                banotedit.setText(dataSnapshot.child("accountNumber").getValue(String.class));
                banatedit.setText(dataSnapshot.child("bankname").getValue(String.class));
                bbtedit.setText(dataSnapshot.child("bnakbranch").getValue(String.class));
                ifstedit.setText(dataSnapshot.child("ifsccode").getValue(String.class));
                final String senddata="Party Name:    "+dataSnapshot.child("partyName").getValue(String.class)+"\n"+"Address:     "+dataSnapshot.child("address").getValue(String.class)+"\n"+"PAN NO.:   "+dataSnapshot.child("panNo").getValue(String.class)+"\n"+"bank Account Name:   "+dataSnapshot.child("accountname").getValue(String.class)+"\n"+"Account Number:    "+dataSnapshot.child("accountNumber").getValue(String.class)+"\n"+"bank:     "+dataSnapshot.child("bankname").getValue(String.class)+"\n"+"bank branch:     "+dataSnapshot.child("bankbranch").getValue(String.class)+"\n"+"IFSC code:      "+dataSnapshot.child("ifsccode").getValue(String.class);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, senddata);
                        sendIntent.setType("text/plain");
                        getActivity().startActivity(sendIntent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
