package com.example.vyanktesh.amesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vyanktesh.amesh.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Readmessagedialog extends DialogFragment {
    RecyclerView rechistory;
    List<Groupadminmessangingmodel> groupadminmessangingmodels=new ArrayList<>();
    List<String> Tids=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.viewhistory,container,false);
        rechistory=view.findViewById(R.id.rechistory);
        String move=getArguments().getString("move");
        Log.d("indialog","readmessagedialog");
        if (move.equals("adminmessagecheck")){
            readdata(FirebaseDatabase.getInstance().getReference("Messages").child("Admin Messages").child("read0"), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        groupadminmessangingmodels.add(dataSnapshot1.getValue(Groupadminmessangingmodel.class));
                        Tids.add(dataSnapshot1.getKey());
                    }
                    rechistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rechistory.setAdapter(new Messageadapter(groupadminmessangingmodels,Tids));
                    FirebaseDatabase.getInstance().getReference("Messages").child("Admin Messages").child("read0").setValue(null);
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }
            });
        }
        if (move.equals("usermessagecheck")){
            String groupid=getArguments().getString("groupid");
            Query query=FirebaseDatabase.getInstance().getReference("Group Messages").child(groupid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        groupadminmessangingmodels.add(dataSnapshot1.getValue(Groupadminmessangingmodel.class));
                        Tids.add(dataSnapshot1.getKey());
                    }
                    rechistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rechistory.setAdapter(new Messageadapter(groupadminmessangingmodels,Tids));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return view;
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
