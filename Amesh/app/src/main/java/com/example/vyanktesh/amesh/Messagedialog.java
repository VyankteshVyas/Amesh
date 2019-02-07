package com.example.vyanktesh.amesh;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class Messagedialog extends DialogFragment{
    public Messagedialog newInstance(String message){
        Messagedialog messagi=new Messagedialog();
        return messagi;
    }
    TextView Dialogtitle;
    EditText groupide,message;
    Button send;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.messagedialog,container,false);
        Dialogtitle=view.findViewById(R.id.dialogtitle);
        groupide=view.findViewById(R.id.groupid);
        message=view.findViewById(R.id.entermessage);
        send=view.findViewById(R.id.sendmessage);
        String move=getArguments().getString("move");
        if (move.equals("fromuserpanel")) {
            groupide.setVisibility(View.INVISIBLE);
            final String groupid=getArguments().getString("groupid");
            final String groupname=getArguments().getString("groupname");

            Dialogtitle.setText("Send App Message");
            message.setHint("Enter message");
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyforpush=FirebaseDatabase.getInstance().getReference("Messages").child("Admin Messages").child("read0").push().getKey();
                    FirebaseDatabase.getInstance().getReference("Messages").child("Admin Messages").child("read0").child(keyforpush).setValue(new Groupadminmessangingmodel(groupname,groupid,message.getText().toString()));
                    Messagedialog.this.dismiss();
                }
            });

        }
        if (move.equals("fromadminpanel")){
            Dialogtitle.setText("Send App Message");
            groupide.setHint("Enter group admin number");
            message.setHint("Enter message");
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Keyofpush=FirebaseDatabase.getInstance().getReference("Messages").child("Group Messages").child(groupide.getText().toString()).push().getKey();
                    FirebaseDatabase.getInstance().getReference("Messages").child("Group Messages").child(groupide.getText().toString()).child(Keyofpush).setValue(new Adminmessangingmodel("0",message.getText().toString()));
                    Messagedialog.this.dismiss();
                }
            });
        }
        return view;
    }
}
