package com.example.vyanktesh.amesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class profilesharingdialog extends DialogFragment {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PreMaturitydates");
    ListView listView;
    List<String> dates=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.profilesharingdialog,null);
        listView=rootview.findViewById(R.id.livi);
        String argi=getArguments().getString("tidi");
        databaseReference.child(argi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dates.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    dates.add(dataSnapshot1.getValue(String.class));
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dates);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootview;
    }
}
