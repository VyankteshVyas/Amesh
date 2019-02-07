package com.example.vyanktesh.amesh;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Notes extends AppCompatActivity implements Filename {

    FloatingActionButton Addnote;
    EditText messageoflist;
    String filenamesent="";
    String formattedDate="";
    Toolbar toolo;
    Button Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolo=findViewById(R.id.toolb);
        Update=findViewById(R.id.Update);
        setSupportActionBar(toolo);
        getSupportActionBar().setTitle("Notes");
        String greet = "";
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);
        //  System.out.println("Current Date => " + formattedDate);         // proper format wala hai ye date ka
        DateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
        DateFormat hour = new SimpleDateFormat("kk");
        Date date = new Date();
        // System.out.println("Current time => " + dateFormat.format(date));   // proper format wala hai ye time ka
        String hr = hour.format(date);
        int h = Integer.parseInt(hr);
        if(h>=5 && h<12){
            greet = "Good Morning";
        }
        else if(h>=12 && h<17){
            greet = "Good Afternoon";
        }
        else{
            greet = "Good Evening";
        }
        final String[] lastlogin = new String[1];
        final String finalGreet = greet;
        readdata(FirebaseDatabase.getInstance().getReference("Uspd").child("7999325318"), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Uspd uspd=dataSnapshot.getValue(Uspd.class);
                lastlogin[0] =dataSnapshot.child("lastlogin").getValue(String.class);
                getSupportActionBar().setSubtitle(finalGreet +"  Last Login :"+ lastlogin[0]);//time
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

        Addnote=findViewById(R.id.addnote);
        Addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Createnote createnote=new Createnote();
                createnote.show(getSupportFragmentManager(),"createnotedialog");
            }
        });
        messageoflist=findViewById(R.id.messageoflist);

    }




    public void readdata (final DatabaseReference reference, final OnGetDataListner listner){
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




    @Override
    public void respond(final String s, String y, String z, String a) {
        filenamesent=s;
        readdata(FirebaseDatabase.getInstance().getReference("Notes").child(filenamesent), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                messageoflist.setText(dataSnapshot.getValue(String.class));
                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference("Notes").child(filenamesent).setValue(messageoflist.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Notes.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Notes.this,"Updated UnSuccessfully",Toast.LENGTH_SHORT).show();

                            }
                        });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.example,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FirebaseDatabase.getInstance().getReference("Uspd").child("7999325318").child("lastlogin").setValue(formattedDate);

        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(Notes.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return true;
    }
}
