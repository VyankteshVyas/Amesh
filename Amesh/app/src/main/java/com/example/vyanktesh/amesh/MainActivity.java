package com.example.vyanktesh.amesh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private String mVerificationId;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    EditText usid, paswd,admo_id,admopasskey,password;
    Button userpassub,validate;
    String passwordi = "hai";
    TextView adminopen;
    final String[] mobile = {""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mAuth = FirebaseAuth.getInstance();
        usid = findViewById(R.id.user_id);
        paswd = findViewById(R.id.passkey);
        userpassub = findViewById(R.id.userpassub);
        password=findViewById(R.id.password);
        validate = findViewById(R.id.validateotp);
//
//
////        userpassub.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                try{
////                    Log.d("upparweala","Inside try1");
////                    databaseReference = FirebaseDatabase.getInstance().getReference("Uspd");
////                    Log.d("uedfd", "7865" + databaseReference.child(usid.getText().toString()));
////                    databaseReference.child(usid.getText().toString()).addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(DataSnapshot dataSnapshot) {
////                            try {
////                                if (dataSnapshot.child("password").getValue(String.class).equals(paswd.getText().toString())) {
////                                    Toast.makeText(MainActivity.this, "Userlogin Valid", Toast.LENGTH_SHORT).show();
////                                    Intent intent = new Intent(MainActivity.this, UserPanel.class);
////                                    intent.putExtra("userid", usid.getText().toString());
////                                    intent.putExtra("grouopid", dataSnapshot.child("groupid").getValue(String.class));
////                                    startActivity(intent);
////                                }
////                            }
////                            catch(NullPointerException e) {
////                                Log.d("upparneala","Inside try1");
////                                Toast.makeText(MainActivity.this, "Invalid Userid and Password", Toast.LENGTH_LONG).show();
////                            }
////                        }
////
////                        @Override
////                        public void onCancelled(DatabaseError databaseError) {
////
////                        }
////                    });
////                }catch(NullPointerException e)
////                {
////                    Toast.makeText(MainActivity.this,"Invalid userid and password",Toast.LENGTH_SHORT).show();
////                }
////
////
////
//////        movetoadmin=findViewById(R.id.adminpanel);
//////        movetouser=findViewById(R.id.userpanel);
//////        movetoadmin.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////                Intent adminintent=new Intent(MainActivity.this,AdminPanel.class);
//////                startActivity(adminintent);
//////            }
//////        });
//////        movetouser.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////                Intent intent=new Intent(MainActivity.this,UserPanel.class);
//////                startActivity(intent);
//////            }
//////        });
////            }
////        });
//
////                usid.setVisibility(View.INVISIBLE);
////                paswd.setVisibility(View.INVISIBLE);
////                admo_id.setVisibility(View.VISIBLE);
////                admopasskey.setVisibility(View.VISIBLE);
//                userpassub.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        FirebaseDatabase.getInstance().getReference("Admin").child(usid.getText().toString()).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                String datasnapshotvalue=dataSnapshot.getValue(String.class);
//                                try{
//                                    if (dataSnapshot.getValue(String.class).isEmpty()){
//
//                                    }else {
//                                        try{
//                                            if (dataSnapshot.getValue().toString().equals(paswd.getText().toString())){
//                                                FragmentManager manager=getSupportFragmentManager();
//                                                admindialog admindialog=new admindialog();
//                                                admindialog.show(manager,"momos");
//                                            }
//                                        }catch(NullPointerException e) {
//                                            Toast.makeText(MainActivity.this, "Invalid Userid and Password", Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                    Log.d("datasnapshotvalue",""+dataSnapshot.getValue(String.class));
//                                }catch(NullPointerException e) {
//                                    Log.d("upparneala","Inside try1");
//                                    try{
//                                        Log.d("upparweala","Inside try1");
//                                        databaseReference = FirebaseDatabase.getInstance().getReference("Uspd");
//                                        Log.d("uedfd", "7865" + databaseReference.child(usid.getText().toString()));
//                                        databaseReference.child(usid.getText().toString()).addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                                try {
//                                                    if (dataSnapshot.child("password").getValue(String.class).equals(paswd.getText().toString())) {
//                                                        Toast.makeText(MainActivity.this, "Userlogin Valid", Toast.LENGTH_SHORT).show();
//                                                        Intent intent = new Intent(MainActivity.this, UserPanel.class);
//                                                        intent.putExtra("userid", usid.getText().toString());
//                                                        intent.putExtra("grouopid", dataSnapshot.child("groupid").getValue(String.class));
//                                                        intent.putExtra("partytype",dataSnapshot.child("type").getValue(String.class));
//                                                        startActivity(intent);
//                                                    }
//                                                }
//                                                catch(NullPointerException e) {
//                                                    Log.d("upparneala","Inside try1");
////                                                    Toast.makeText(MainActivity.this, "Invalid Userid and Password", Toast.LENGTH_LONG).show();
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onCancelled(DatabaseError databaseError) {
//
//                                            }
//                                        });
//                                    }catch(NullPointerException f)
//                                    {
//                                        Toast.makeText(MainActivity.this,"Invalid userid and password",Toast.LENGTH_SHORT).show();
//                                    }
//                                    Toast.makeText(MainActivity.this, "Invalid Userid and Password", Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//                    }
//                });

        userpassub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("enteringuserpasssub","entered");
                mobile[0] = usid.getText().toString().trim();


                Log.d("passwordin",passwordi);
                FirebaseDatabase.getInstance().getReference("Uspd").child(mobile[0]).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("dfh",passwordi);
                        if(mobile[0].isEmpty() || mobile[0].length() < 10){
                            usid.setError("Enter a valid mobile");
                            usid.requestFocus();
                            return;
                        }
                        if (dataSnapshot.getValue()!=null){
                            passwordi=dataSnapshot.getValue().toString();
                            if (!password.getText().toString().equals(passwordi)){
                                password.setError("Enter a valid password");
                                Log.d("passwordvalue","ohk");
                                Log.d("difhdi",passwordi);
                                password.requestFocus();

                                return;
                            }
                            sendVerificationCode(mobile[0]);
                            usid.setVisibility(View.INVISIBLE);
                            password.setVisibility(View.INVISIBLE);
                            userpassub.setVisibility(View.INVISIBLE);
                            validate.setVisibility(View.VISIBLE);
                            paswd.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(MainActivity.this,"Invalid User ID and password",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });
                Log.d("passwordoivalue",passwordi);


            }
        });
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = paswd.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    paswd.setError("Enter valid code");
                    paswd.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });

    }
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                paswd.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;

        }
    };


    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            if (mobile[0].equals("7999325318")) {
                                FragmentManager manager=getSupportFragmentManager();
                                                admindialog admindialog=new admindialog();
                                                admindialog.show(manager,"momos");
                            } else {
                                readdata(FirebaseDatabase.getInstance().getReference("Uspd").child(mobile[0]), new OnGetDataListner() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        Intent intent = new Intent(MainActivity.this, UserPanel.class);
//                                        intent.putExtra("userid", "7869909404");
                                        intent.putExtra("grouopid", mobile[0]);
//                                        intent.putExtra("partytype", dataSnapshot.child("type").getValue(String.class));
                                        intent.putExtra("lastlogin",dataSnapshot.child("lastlogin").getValue(String.class));
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
                                Intent intent = new Intent(MainActivity.this, UserPanel.class);

                            }
                        }
                        }



                            });


    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
//
//            Intent intent = new Intent(this, AdminPanel.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
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
