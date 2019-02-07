package com.example.vyanktesh.amesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;

public class Modedialog extends DialogFragment {
    Filename filename;
    public Modedialog newInstance(String message){
        Modedialog modedialog=new Modedialog();
        return modedialog;
    }
    RadioButton checkbox1,checkbox2;
    RadioGroup radiogroupselection;
    Button submitselection;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.modedialog,container,false);
        filename= (Filename) getActivity();
        checkbox1=view.findViewById(R.id.checkbox1);
        checkbox2=view.findViewById(R.id.checkbox2);


        radiogroupselection=view.findViewById(R.id.radiogroupselection);
        submitselection=view.findViewById(R.id.submitselection);
        final String mode=getArguments().getString("Mode");
        submitselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkbox1id=String.valueOf(checkbox1.getId());
                String checkbox2id=String.valueOf(checkbox2.getId());
                if (mode.equals("searchmode")){
                    String selectedradiogroup=String.valueOf(radiogroupselection.getCheckedRadioButtonId());
                    if (selectedradiogroup.equals(checkbox1id)){
                        filename.respond(checkbox1.getText().toString(),"","","Mode");
                        Modedialog.this.dismiss();
                    }else {
                        filename.respond(checkbox2.getText().toString(),"","","Mode");
                        Modedialog.this.dismiss();
                    }
                }
            }
        });

        return view;
    }
}
