package com.example.vyanktesh.amesh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class UserPanel extends AppCompatActivity implements Filename, DatePickerDialog.OnDateSetListener{

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    final String[] partytype = new String[1];
    String daict=new String();
    DatabaseReference userlogin;
    RecyclerView recyclerView;
    List<TransactionentryModel> checktran=new ArrayList<>();
    List<String> checktranid=new ArrayList<>();
    TextView nocontent;
    CheckBox Partynamecheckbox,MaturityDatecheckbox;
    Button Search;
    ArrayList<String> usids=new ArrayList<>();
    ArrayList<String> usnames=new ArrayList<>();
    ArrayList<String> userstatus=new ArrayList<>();
    Button ChangePassword;
    Toolbar toolo;
    String userid="";
    String formattedDate="";
    List<TransactionentryModel> Transactioninv=new ArrayList<>();
    List<DataSnapshot> fullgroupdata=new ArrayList<>();
    List<String> uidis=new ArrayList<>();
    List<String> Tidids=new ArrayList<>();
    FloatingActionButton messagefaab,faab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        toolo=findViewById(R.id.toolb);
        Partynamecheckbox=findViewById(R.id.Partynamecheck);
        MaturityDatecheckbox=findViewById(R.id.Maturitydatecheck);
        Search=findViewById(R.id.Search);
        ChangePassword=findViewById(R.id.userchangePassword);
        setSupportActionBar(toolo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getSupportActionBar().setTitle("User Panel");
        recyclerView=findViewById(R.id.recylo);
        messagefaab=findViewById(R.id.messagefaab);
        faab=findViewById(R.id.faab);
        nocontent=findViewById(R.id.nocontent);


//        userlogin=FirebaseDatabase.getInstance().getReference("Allgroups").child(groumo.getText().toString()).child(youmono.getText().toString());
//        userid=getIntent().getStringExtra("userid");
        final String grouopid=getIntent().getStringExtra("grouopid");
        String lastlogin=getIntent().getStringExtra("lastlogin");
        userid=grouopid;
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordChange passwordChange=new PasswordChange();
                Bundle bundle=new Bundle();
                bundle.putString("passwordchange","userpasswordchange");
                bundle.putString("groupid",grouopid);
                passwordChange.setArguments(bundle);
                passwordChange.show(getSupportFragmentManager(),"dfidh");
            }
        });
        final String[] username = new String[1];
        readdata(FirebaseDatabase.getInstance().getReference("groups").child(userid), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                username[0] =dataSnapshot.child("groupname").getValue(String.class);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
        messagefaab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Messagedialog messagedialog=new Messagedialog();
                Bundle bundle=new Bundle();
                bundle.putString("move","fromuserpanel");
                bundle.putString("groupid",userid);
                bundle.putString("groupname",username[0]);
                messagedialog.setArguments(bundle);
                messagedialog.show(getSupportFragmentManager(),"uh");
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        faab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Messagerecyclerview messagerecyclerview=new Messagerecyclerview();
                Bundle bundle=new Bundle();
                bundle.putString("move","usermessagecheck");
                bundle.putString("groupid",userid);
                messagerecyclerview.setArguments(bundle);
                messagerecyclerview.show(getSupportFragmentManager(),"dif");
            }
        });


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
        getSupportActionBar().setSubtitle(greet+"  Last Login :"+lastlogin);//time



        readdata(FirebaseDatabase.getInstance().getReference("groups").child(userid), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                partytype[0] =dataSnapshot.child("partyType").getValue(String.class);
                if (partytype[0].equals("Investor")){
                    Query query=FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("investorgroupuid").equalTo(userid);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
                                Tidids.add(dataSnapshot1.getKey());
                            }
                            for (int i=0;i<Tidids.size();i++){
                                if (Transactioninv.get(i).hidden.equals("0")){
                                    Transactioninv.remove(i);
                                    Tidids.remove(i);
                                }
                            }

                            for (int i=0;i<Tidids.size();i++){
                                if (!usids.contains(Transactioninv.get(i).investoruid)){
                                    usids.add(Transactioninv.get(i).investoruid);
                                    usnames.add(Transactioninv.get(i).investoredit);
                                    userstatus.add("Investor");

                                }
                                if (!usids.contains(Transactioninv.get(i).recieveruid)){
                                    usids.add(Transactioninv.get(i).recieveruid);
                                    usnames.add(Transactioninv.get(i).recieveredit);
                                    userstatus.add("Reciever");
                                }
                            }
                            if (Tidids.size()==0){
                                nocontent.setText("you have no transactions");
                                recyclerView.setVisibility(View.INVISIBLE);
                            }else {
                                recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
                                recyclerView.setAdapter(new useradapter(Transactioninv,UserPanel.this,Tidids, partytype[0]));
                                Partynamecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked){
                                            group group=new group();
                                            Bundle bundle=new Bundle();
                                            bundle.putString("transactiontogroup","userpanelrecievercheckbox");
                                            bundle.putStringArrayList("usids",usids);
                                            bundle.putStringArrayList("usnames",usnames);
                                            bundle.putStringArrayList("userstatus",userstatus);
                                            group.setArguments(bundle);
                                            group.show(getSupportFragmentManager(),"ohk");
                                        }
                                    }
                                });
                                MaturityDatecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked){
                                            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                                    month = month + 1;
                                                    Log.d("dfid", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                                                    String date = day + "-" + month + "-" + year;
                                                    daict=date;

                                                }
                                            };
                                            newdafor();
                                        }
                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (partytype[0].equals("Reciever")){
                    Query query=FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("recievergroupuid").equalTo(userid);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
                                Tidids.add(dataSnapshot1.getKey());
                            }
                            for (int i=0;i<Tidids.size();i++){
                                if (Transactioninv.get(i).hidden.equals("0")){
                                    Transactioninv.remove(i);
                                    Tidids.remove(i);
                                }
                            }

                            for (int i=0;i<Tidids.size();i++){
                                if (!usids.contains(Transactioninv.get(i).investoruid)){
                                    usids.add(Transactioninv.get(i).investoruid);
                                    usnames.add(Transactioninv.get(i).investoredit);
                                    userstatus.add("Investor");

                                }
                                if (!usids.contains(Transactioninv.get(i).recieveruid)){
                                    usids.add(Transactioninv.get(i).recieveruid);
                                    usnames.add(Transactioninv.get(i).recieveredit);
                                    userstatus.add("Reciever");
                                }
                            }
                            if (Tidids.size()==0){
                                nocontent.setText("you have no transactions");
                                recyclerView.setVisibility(View.INVISIBLE);
                            }else {
                                recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
                                recyclerView.setAdapter(new useradapter(Transactioninv,UserPanel.this,Tidids, partytype[0]));
                                Partynamecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked){
                                            group group=new group();
                                            Bundle bundle=new Bundle();
                                            bundle.putString("transactiontogroup","userpanelrecievercheckbox");
                                            bundle.putStringArrayList("usids",usids);
                                            bundle.putStringArrayList("usnames",usnames);
                                            bundle.putStringArrayList("userstatus",userstatus);
                                            group.setArguments(bundle);
                                            group.show(getSupportFragmentManager(),"ohk");
                                        }
                                    }
                                });

                                MaturityDatecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked){
                                            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                                    month = month + 1;
                                                    Log.d("dfid", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                                                    String date = day + "-" + month + "-" + year;
                                                    daict=date;

                                                }
                                            };
                                            newdafor();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }



            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });


//        if (userid.equals(grouopid)){
//            readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child("7869909404"), new OnGetDataListner() {
//                @Override
//                public void onSuccess(DataSnapshot dataSnapshot) {
//                    uidis.clear();
//                    Tidids.clear();
//                    fullgroupdata.clear();
//                    Transactioninv.clear();
//                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                        fullgroupdata.add(dataSnapshot1);
//                        uidis.add(dataSnapshot1.getKey());
//                    }
//                    for (int i=0;i<fullgroupdata.size();i++){
//                        for (DataSnapshot dataSnapshot2:fullgroupdata.get(i).getChildren()){
//                            Tidids.add(dataSnapshot2.getKey());
//                        }
//                    }
//                    for (int i = 0; i <= Tidids.size() - 1; i++) {
//                        readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tidids.get(i)), new OnGetDataListner() {
//                            @Override
//                            public void onSuccess(DataSnapshot dataSnapshot) {
//                                Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                Log.d("tranv", "" + Transactioninv.size());
//                                Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
//                                recyclerView.setAdapter(new useradapter(Transactioninv,UserPanel.this,Tidids,partytype));
//                            }
//
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onFailure() {
//
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onFailure() {
//
//                }
//            });
//        }else {
//            readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child("7869909404").child("7869909404"), new OnGetDataListner() {
//                @Override
//                public void onSuccess(DataSnapshot dataSnapshot) {
//                    uidis.clear();
//                    Tidids.clear();
//                    fullgroupdata.clear();
//                    Transactioninv.clear();
//                    for (DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
//                        Tidids.add(dataSnapshot2.getKey());
//                    }
//                    for (int i = 0; i <= Tidids.size() - 1; i++) {
//                        readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tidids.get(i)), new OnGetDataListner() {
//                            @Override
//                            public void onSuccess(DataSnapshot dataSnapshot) {
//                                Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                Log.d("tranv", "" + Transactioninv.size());
//                                Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
//                                recyclerView.setAdapter(new useradapter(Transactioninv,UserPanel.this,Tidids,partytype));
//                            }
//
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onFailure() {
//
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onFailure() {
//
//                }
//            });
//        }
//        sumi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tidls.clear();
//                Transactioninv.clear();
//                readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child(groumo.getText().toString()).child(youmono.getText().toString()), new OnGetDataListner() {
//                    @Override
//                    public void onSuccess(DataSnapshot dataSnapshot) {
//                        Tidls.clear();
//                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                            Tidls.add(dataSnapshot2.getKey());
//                        }
//                        Log.d("tidsize", "" + Tidls.size());
//                        for (int i = 0; i <= Tidls.size() - 1; i++) {
//                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tidls.get(i)), new OnGetDataListner() {
//                                @Override
//                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                    Log.d("tranv", "" + Transactioninv.size());
//                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                    recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
//                                    recyclerView.setAdapter(new Searchadapter(Transactioninv,UserPanel.this));
//                                }
//
//                                @Override
//                                public void onStart() {
//
//                                }
//
//                                @Override
//                                public void onFailure() {
//
//                                }
//                            });
//                        }
//                        Log.d("ter", "" + Transactioninv.size());
////
//                    }
//
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onFailure() {
//
//                    }
//                });
////                readdata(userlogin, new OnGetDataListner() {
////                    @Override
////                    public void onSuccess(DataSnapshot dataSnapshot) {
////                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
////                            Tidls.add(dataSnapshot1.getKey());
////                            Log.d("diwu",""+dataSnapshot1.getKey());
////                        }
////                        for (int i=0;i<=Tidls.size()-1;i++){
////                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tidls.get(i)), new OnGetDataListner() {
////                                @Override
////                                public void onSuccess(DataSnapshot dataSnapshot) {
////                                   Log.d("heio",""+dataSnapshot.getKey());
////                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
////                                    Log.d("eui",""+Transactioninv.size());
////                                    Log.d("ioeu",""+Transactioninv.get(0).maturitydateedit);
////                                    recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
////                                    recyclerView.setAdapter(new Searchadapter(Transactioninv,UserPanel.this));
////                                }
////
////                                @Override
////                                public void onStart() {
////
////                                }
////
////                                @Override
////                                public void onFailure() {
////
////                                }
////                            });
////                        }
////                    }
////
////                    @Override
////                    public void onStart() {
////
////                    }
////
////                    @Override
////                    public void onFailure() {
////
////                    }
////                });
//            }
//        });


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
        FirebaseDatabase.getInstance().getReference("Uspd").child(userid).child("lastlogin").setValue(formattedDate);
        FirebaseAuth.getInstance().signOut();
        Intent intent1=new Intent(UserPanel.this,MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent1);
        return true;
    }

    @Override
    public void respond(final String s, final String y, String z, String a) {

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Partynamecheckbox.isChecked()&&MaturityDatecheckbox.isChecked()){
                    if (y.equals("Reciever")){
                        checktranid.clear();
                        checktran.clear();
                        Log.d("ij","fdhu"+s+"djd");

                        for (int i=0;i<Transactioninv.size();i++){
                            if (Transactioninv.get(i).recieveruid.equals(s)&&Transactioninv.get(i).maturitydateedit.equals(daict)){
                                checktran.add(Transactioninv.get(i));
                                checktranid.add(Tidids.get(i));
                            }

                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
                        recyclerView.setAdapter(new useradapter(checktran,UserPanel.this,checktranid, partytype[0]));
                    }
                    if (y.equals("Investor")){
                        checktranid.clear();
                        checktran.clear();
                        Log.d("ij","fdhu"+s+"djdk");
                        for (int i=0;i<Transactioninv.size();i++){
                            if (Transactioninv.get(i).investoruid.equals(s)&&Transactioninv.get(i).maturitydateedit.equals(daict)){
                                checktran.add(Transactioninv.get(i));
                                checktranid.add(Tidids.get(i));
                            }



                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
                        recyclerView.setAdapter(new useradapter(checktran,UserPanel.this,checktranid, partytype[0]));
                    }
                }
                if (Partynamecheckbox.isChecked()){
                    if (y.equals("Reciever")){
           checktranid.clear();
           checktran.clear();
           Log.d("ij","fdhu"+s+"djd");

           for (int i=0;i<Transactioninv.size();i++){
               if (Transactioninv.get(i).recieveruid.equals(s)){
                   checktran.add(Transactioninv.get(i));
                   checktranid.add(Tidids.get(i));
               }

           }

           recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
           recyclerView.setAdapter(new useradapter(checktran,UserPanel.this,checktranid, partytype[0]));
       }
       if (y.equals("Investor")){
           checktranid.clear();
           checktran.clear();
           Log.d("ij","fdhu"+s+"djdk");
           for (int i=0;i<Transactioninv.size();i++){
               if (Transactioninv.get(i).investoruid.equals(s)){
                  checktran.add(Transactioninv.get(i));
                  checktranid.add(Tidids.get(i));
               }



           }
           recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
           recyclerView.setAdapter(new useradapter(checktran,UserPanel.this,checktranid, partytype[0]));
       }
                }
                if (MaturityDatecheckbox.isChecked()){
                    checktranid.clear();
                    checktran.clear();
                    for (int i=0;i<Transactioninv.size();i++){
                        if (Transactioninv.get(i).maturitydateedit.equals(daict)){
                            checktran.add(Transactioninv.get(i));
                            checktranid.add(Tidids.get(i));
                        }

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
                    recyclerView.setAdapter(new useradapter(checktran,UserPanel.this,checktranid, partytype[0]));
                }
            }
        });
//       if (y.equals("Reciever")){
//           checktranid.clear();
//           checktran.clear();
//           Log.d("ij","fdhu"+s+"djd");
//
//           for (int i=0;i<Transactioninv.size();i++){
//               if (Transactioninv.get(i).recieveruid.equals(s)){
//                   checktran.add(Transactioninv.get(i));
//                   checktranid.add(Tidids.get(i));
//               }
//
//           }
//
//           recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
//           recyclerView.setAdapter(new useradapter(checktran,UserPanel.this,checktranid, partytype[0]));
//       }
//       if (y.equals("Investor")){
//           checktranid.clear();
//           checktran.clear();
//           Log.d("ij","fdhu"+s+"djdk");
//           for (int i=0;i<Transactioninv.size();i++){
//               if (Transactioninv.get(i).investoruid.equals(s)){
//                  checktran.add(Transactioninv.get(i));
//                  checktranid.add(Tidids.get(i));
//               }
//
//
//
//           }
//           recyclerView.setLayoutManager(new LinearLayoutManager(UserPanel.this));
//           recyclerView.setAdapter(new useradapter(checktran,UserPanel.this,checktranid, partytype[0]));
//       }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
    public void newdafor() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                UserPanel.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
