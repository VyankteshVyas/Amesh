package com.example.vyanktesh.amesh;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Viewhistory extends DialogFragment {
    RecyclerView rechistory;
    List<TransactionentryModel> listofit=new ArrayList<>();
    DatabaseReference rachidata= FirebaseDatabase.getInstance().getReference("PreMaturitydates");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.viewhistory,null);
        rechistory=root.findViewById(R.id.rechistory);
        String tranid=getArguments().getString("tidi");
        readdata(rachidata.child(tranid), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    listofit.add(dataSnapshot1.getValue(TransactionentryModel.class));
                }
                rechistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                rechistory.setAdapter(new Rachistoryadapter(listofit));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
        return root;
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
