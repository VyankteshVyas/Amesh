package com.example.vyanktesh.amesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vyanktesh.amesh.R;

public class Dialogboxforsearchenteries extends DialogFragment {
    Filename filename;
    public Dialogboxforsearchenteries newInstance(String message){
        Dialogboxforsearchenteries dialogboxforsearchenteries=new Dialogboxforsearchenteries();
        return dialogboxforsearchenteries;
    }
    EditText inputentries;
    TextView Dialogtitle;
    Button submitentry;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.searchenteries,container,false);
        inputentries=view.findViewById(R.id.inputentries);
        filename= (Filename) getActivity();
        Dialogtitle=view.findViewById(R.id.dialogtitle);
        submitentry=view.findViewById(R.id.submitentry);
        final String dialogtitle=getArguments().getString("dialogtitle");
        String editablehint=getArguments().getString("editablehint");
        Dialogtitle.setText(dialogtitle);
        inputentries.setHint(editablehint);
        if (Dialogtitle.equals("Password")){

            inputentries.setText(editablehint);
            inputentries.setEnabled(false);
            submitentry.setEnabled(false);
        }
        submitentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogtitle.equals("Amount")){
                    filename.respond(inputentries.getText().toString(),"","","Amount");
                }
                if (dialogtitle.equals("R.O.I")){
                    filename.respond(inputentries.getText().toString(),"","","R.O.I");
                }
                Dialogboxforsearchenteries.this.dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
