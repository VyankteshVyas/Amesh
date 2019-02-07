package com.example.vyanktesh.amesh;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

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

public class AdminPanel extends AppCompatActivity implements Filename{
    public String valueofGroup;
//    Toolbar toolo;

    String formattedDate="";
    android.support.v7.widget.AppCompatTextView txtpassward;
    android.support.v7.widget.AppCompatTextView lastlogini;
    DatabaseReference groups;
    ImageButton transactionentry,masterentery,viewLedger,search,messagebox,logout,addnote;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        lastlogini=findViewById(R.id.lastlogin);
        txtpassward=findViewById(R.id.txtpassward);
        txtpassward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordChange passwordChange=new PasswordChange();
                Bundle bundle=new Bundle();
                bundle.putString("passwordchange","adminpasswordchange");
                bundle.putString("adminid","7999325318");
                passwordChange.setArguments(bundle);
                passwordChange.show(getSupportFragmentManager(),"dfidh");
            }
        });
//        toolo=findViewById(R.id.toolb);
//        setSupportActionBar(toolo);
//        getSupportActionBar().setTitle("Admin Panel");
//       final group gripi=new group();


        transactionentry=findViewById(R.id.transactionentry);
        viewLedger=findViewById(R.id.viewledger);
        masterentery=findViewById(R.id.masterentry);
        addnote=findViewById(R.id.addnote);
        search=findViewById(R.id.search);
        messagebox=findViewById(R.id.messagebox);
        logout=findViewById(R.id.logout);
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
        final String finalGreet1 = greet;
        readdata(FirebaseDatabase.getInstance().getReference("Uspd").child("7999325318"), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Uspd uspd=dataSnapshot.getValue(Uspd.class);
                lastlogin[0] =dataSnapshot.child("lastlogin").getValue(String.class);
                lastlogini.setText(finalGreet1 +"  Last Login :"+ lastlogin[0]);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Uspd").child("7999325318").child("lastlogin").setValue(formattedDate);

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(AdminPanel.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPanel.this,Notes.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPanel.this,Searchactivity.class);
                startActivity(intent);
            }
        });
        masterentery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();


                MasterClass masterClass=new MasterClass();
                Bundle argo=new Bundle();
                argo.putString("move","admintonewgroup");
                masterClass.setArguments(argo);
                transaction.add(R.id.rellayout,masterClass,"Itshalwa");
                transaction.addToBackStack("admintomasterclass");
                transaction.commit();
//                Intent intent=new Intent(AdminPanel.this,Masterentry.class);
//                intent.putExtra("move","admintogrouop");
//                startActivity(intent);
////
            }
        });
        transactionentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPanel.this,TransactionEntryNew.class);
                intent.putExtra("verify","0");
                startActivity(intent);
            }
        });

        viewLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPanel.this,Viewledger.class);
                startActivity(intent);
            }
        });
        messagebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminPanel.this,Message.class);
                startActivity(intent);
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
    public void respond(String s, String y, String z, String a) {

    }
}
