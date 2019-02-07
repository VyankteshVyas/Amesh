package com.example.vyanktesh.amesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class Createnote extends DialogFragment {

    public Createnote newInstance(String message){
        Createnote createnote=new Createnote();
        return createnote;
    }
    EditText filename,notecontent;
    Button savenote;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.createnote,container,false);
        filename=rootview.findViewById(R.id.filename);
        notecontent=rootview.findViewById(R.id.notecontent);
        savenote=rootview.findViewById(R.id.savenote);
        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(filename.getText().toString())&&!TextUtils.isEmpty(notecontent.getText().toString())){
                    FirebaseDatabase.getInstance().getReference("Notes").child(filename.getText().toString()).setValue(notecontent.getText().toString());
                    Toast.makeText(getActivity(),"Note Saved Successfully",Toast.LENGTH_SHORT).show();
                    Createnote.this.dismiss();
                }else {Toast.makeText(getActivity(),"Above filelds are empty",Toast.LENGTH_SHORT).show();}
            }
        });
        return rootview;
    }
}
