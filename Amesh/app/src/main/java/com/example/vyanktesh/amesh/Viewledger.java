package com.example.vyanktesh.amesh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Viewledger extends AppCompatActivity {

    Toolbar toolo;
    String formattedDate="";


    RecyclerView ledgerrecyclerview;
    List<TransactionentryModel> dataSnapshotList=new ArrayList<>();
    List<String> Tids=new ArrayList<>();
    DatabaseReference Transactionenteries= FirebaseDatabase.getInstance().getReference("Transaction Enteries");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewledger);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolo=findViewById(R.id.toolb);
        ledgerrecyclerview=findViewById(R.id.ledgerrecyclerview);
        setSupportActionBar(toolo);
        getSupportActionBar().setTitle("Ledger");
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
        readdata(Transactionenteries, new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                dataSnapshotList.clear();
                Tids.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Tids.add(dataSnapshot1.getKey());
                    dataSnapshotList.add(dataSnapshot1.getValue(TransactionentryModel.class));
                }
                ledgerrecyclerview.setLayoutManager(new LinearLayoutManager(Viewledger.this));
                ledgerrecyclerview.setAdapter(new Searchadapter(dataSnapshotList,Viewledger.this,Tids));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

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
        Intent intent=new Intent(Viewledger.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return true;
    }
}
