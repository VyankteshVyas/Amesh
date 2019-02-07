package com.example.vyanktesh.amesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NewGroup extends Fragment {
    DatabaseReference databaseReference;
    Button saveNewGroup;
    Spinner parpe;
    EditText nameofgrip,numberOfGrip;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.newgroup,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        saveNewGroup=getActivity().findViewById(R.id.savenewgroup);
        nameofgrip=getActivity().findViewById(R.id.nameofGrip);
        parpe=getActivity().findViewById(R.id.parpe);
        numberOfGrip=getActivity().findViewById(R.id.numberofGrip);
        databaseReference= FirebaseDatabase.getInstance().getReference("groups");
        saveNewGroup=getActivity().findViewById(R.id.savenewgroup);
        saveNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(nameofgrip.getText())&&!TextUtils.isEmpty(numberOfGrip.getText())) {
                    NewgroupModel newgroupModel = new NewgroupModel(nameofgrip.getText().toString(), numberOfGrip.getText().toString(),parpe.getSelectedItem().toString());
                    databaseReference.child(numberOfGrip.getText().toString()).setValue(newgroupModel);
                    FragmentManager manager = getFragmentManager();
                    Log.d("dass",parpe.getSelectedItem().toString());
                    FragmentTransaction transaction = manager.beginTransaction();


                    manager.popBackStack();

                }else {
                    Toast.makeText(getActivity(),"Please Enter the above two fields",Toast.LENGTH_LONG).show();}

            }
        });
    }
}
