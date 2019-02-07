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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PasswordChange extends DialogFragment {
    EditText OldPassword,NewPassword,ConfirmNewPAssword;
    Button submit;
    public PasswordChange newInstance(String message){
        PasswordChange passwordChange=new PasswordChange();
        return passwordChange;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.passwordchange,container,false);
        OldPassword=view.findViewById(R.id.OldPassword);
        NewPassword=view.findViewById(R.id.NewPassword);
        ConfirmNewPAssword=view.findViewById(R.id.ConfirmNewPassword);
        submit=view.findViewById(R.id.changePassword);
        String move=getArguments().getString("passwordchange");
        if (move.equals("userpasswordchange")){
            final String groupid=getArguments().getString("groupid");
            readdata(FirebaseDatabase.getInstance().getReference("Uspd").child(groupid), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    final String Oldpasswordstring=dataSnapshot.child("password").getValue(String.class);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!OldPassword.getText().toString().equals(Oldpasswordstring)) {
                                OldPassword.setError("Old Password Not Matches");
                                OldPassword.requestFocus();
                                return;
                            }
                            if (!NewPassword.getText().toString().equals(ConfirmNewPAssword.getText().toString())){
                                ConfirmNewPAssword.setError("Please Enter Correct Password");
                                ConfirmNewPAssword.requestFocus();
                                return;
                            }
                            FirebaseDatabase.getInstance().getReference("Uspd").child(groupid).child("password").setValue(NewPassword.getText().toString());
                            PasswordChange.this.dismiss();

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
        if (move.equals("adminpasswordchange")){
            final String adminid=getArguments().getString("adminid");
            readdata(FirebaseDatabase.getInstance().getReference("Uspd").child(adminid), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    final String Oldpasswordstring=dataSnapshot.child("password").getValue(String.class);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!OldPassword.getText().toString().equals(Oldpasswordstring)) {
                                OldPassword.setError("Old Password Not Matches");
                                OldPassword.requestFocus();
                                return;
                            }
                            if (!NewPassword.getText().toString().equals(ConfirmNewPAssword.getText().toString())){
                                ConfirmNewPAssword.setError("Please Enter Correct Password");
                                ConfirmNewPAssword.requestFocus();
                                return;
                            }
                            FirebaseDatabase.getInstance().getReference("Uspd").child(adminid).child("password").setValue(NewPassword.getText().toString());
                            PasswordChange.this.dismiss();

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
        return view;
    }
    public void readdata(final DatabaseReference reference, final OnGetDataListner listner){
        listner.onStart();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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
