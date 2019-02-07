package com.example.vyanktesh.amesh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.SyncStateContract;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Searchactivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,Filename {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "MainActivity";
    int i;String formattedDate="";
    Toolbar toolo;

    CheckBox investorcheckbox,recievercheckbox,maturitydatecheckbox,entrydatecheckbox,Amountcheckbox,ROIcheckbox,ModecheckBox,StatuscheckBox;


    String partyname="";
    String partyid="";
    String partygroupid="";
    String investorcheckboxselectiongroupid="";
    String investorcheckboxselectionuserid="";
    String recievercheckboxselectiongroupid="";
    String recievercheckboxselectionuserid="";
    String maturitydatecheckboxselection="";
    String entrydatecheckboxselection="";
    String Amountcheckboxselection="";
    String ROIcheckboxselection="";
    String Modecheckboxselection="";
    ProgressBar progibar;
    String currentDateString;
    AutoCompleteTextView searchi;
    DatabaseReference Investors,Recievers;
    RecyclerView reciv;
    Spinner searchwaySpinner;
    TextView selectfielddata;
    TextView enddate;
    Button seli,show,search;
    List<String> investorgroupid=new ArrayList<>();
    List<String> investoruserid=new ArrayList<>();
    List<String> investori=new ArrayList<>();
    List<String> recievergroupid=new ArrayList<>();
    List<String> recieveruserid=new ArrayList<>();
    List<String> recieveri=new ArrayList<>();
    List<String> Tids=new ArrayList<>();

    List<TransactionentryModel> Transactioninv=new ArrayList<>();
    String[] databasevariables ={"investoredit","recieveredit","entrydatieedit","maturitydateedit","amountedit","roi","spinnermode","status"};
    String[] Priority=new String[9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchactivity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        toolo=findViewById(R.id.toolb);
        setSupportActionBar(toolo);

        getSupportActionBar().setTitle("Transaction Entry");
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

        final List<CheckBox> checkBoxes=new ArrayList<>();




        investorcheckbox=findViewById(R.id.investorcheckBox);
        recievercheckbox=findViewById(R.id.revievercheckBox2);
        entrydatecheckbox=findViewById(R.id.EntrydatecheckBox7);
        maturitydatecheckbox=findViewById(R.id.MaturitydatecheckBox6);
        Amountcheckbox=findViewById(R.id.Amountcheckbox);
        ROIcheckbox=findViewById(R.id.ROIcheckbox);
        StatuscheckBox=findViewById(R.id.StatuscheckBox);
        ModecheckBox=findViewById(R.id.ModecheckBox);


        selectfielddata=findViewById(R.id.selectfielddata);
        reciv=findViewById(R.id.reciv);
//        seli=findViewById(R.id.seli);
//        show=findViewById(R.id.show);
        search=findViewById(R.id.search);
//        searchi=findViewById(R.id.searchbar);
//        searchwaySpinner=findViewById(R.id.searchwayspin);
        progibar=findViewById(R.id.progibar);
        progibar.setIndeterminate(true);
        progibar.setVisibility(View.INVISIBLE);
        Investors= FirebaseDatabase.getInstance().getReference("Investors");
        toolo=findViewById(R.id.toolb);
        setSupportActionBar(toolo);
        getSupportActionBar().setTitle("Search Deals");
        Recievers=FirebaseDatabase.getInstance().getReference("Recievers");


        checkBoxes.add(investorcheckbox);
        checkBoxes.add(recievercheckbox);
        checkBoxes.add(entrydatecheckbox);
        checkBoxes.add(maturitydatecheckbox);
        checkBoxes.add(Amountcheckbox);
        checkBoxes.add(ROIcheckbox);
        checkBoxes.add(ModecheckBox);
//        searchwaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItem = parent.getItemAtPosition(position).toString();
//                if (selectedItem.equals("Investor")){
//                    Log.d("haa","hoo");
//                    progibar.setVisibility(View.VISIBLE);
//                    readdata(Investors, new OnGetDataListner() {
//                        @Override
//                        public void onSuccess(DataSnapshot dataSnapshot) {
//                            Log.d("dssd",""+dataSnapshot.getChildrenCount());
//                            investori.clear();
//                            investorgroupid.clear();
//                            investoruserid.clear();
//                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                                InvestorMode invo=dataSnapshot1.getValue(InvestorMode.class);
//                                Log.d("fdd",dataSnapshot1.getKey());
//                                investori.add(invo.userName);
//                                Log.d("invosize",""+investori.size());
//                                investoruserid.add(dataSnapshot1.getKey());
//                                investorgroupid.add(invo.Groupid);
//                            }
//                            progibar.setVisibility(View.INVISIBLE);
//                            show.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onStart() {
//
//                        }
//
//                        @Override
//                        public void onFailure() {
//
//                        }
//                    });
//                    Log.d("bhrr","dfd");
//                    Log.d("xdjfk",""+investori.size());
//                    ArrayAdapter investoradapter=new ArrayAdapter(Searchactivity.this,android.R.layout.simple_list_item_1,investori);
//                    Log.d("insa",""+investori.size());
//                    searchi.setAdapter(investoradapter);
//                    searchi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//                    show.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(searchi.getText().toString())&&investori.contains(searchi.getText().toString())) {
//                                Transactioninv.clear();
//
//                                String inveg = investorgroupid.get(investori.indexOf(searchi.getText().toString()));
//                                String usid = investoruserid.get(investori.indexOf(searchi.getText().toString()));
//                                readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child(inveg).child(usid), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        Tids.clear();
//                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                            Tids.add(dataSnapshot2.getKey());
//                                        }
//                                        Log.d("tidsize", "" + Tids.size());
//                                        for (i = 0; i <= Tids.size() - 1; i++) {
//                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                    Log.d("tranv", "" + Transactioninv.size());
//                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }
//                                        Log.d("ter", "" + Transactioninv.size());
////
//                                    }
//
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure() {
//
//                                    }
//                                });
//
//                            }else {
//                                Toast.makeText(Searchactivity.this,"Select from options",Toast.LENGTH_LONG).show();}
//                        }
//                    });
//
//                }
//
//
//
//
//
//                if (selectedItem.equals("Reciever")){
//                    Log.d("haa","hoo");
//                    progibar.setVisibility(View.VISIBLE);
//                    readdata(Recievers, new OnGetDataListner() {
//                        @Override
//                        public void onSuccess(DataSnapshot dataSnapshot) {
//                            Log.d("dssd",""+dataSnapshot.getChildrenCount());
//                            investori.clear();
//                            investorgroupid.clear();
//                            investoruserid.clear();
//                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                                InvestorMode invo=dataSnapshot1.getValue(InvestorMode.class);
//                                Log.d("fdd",dataSnapshot1.getKey());
//                                investori.add(invo.userName);
//                                Log.d("invosize",""+investori.size());
//                                investoruserid.add(dataSnapshot1.getKey());
//                                investorgroupid.add(invo.Groupid);
//                            }
//                            progibar.setVisibility(View.INVISIBLE);
//                            show.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onStart() {
//
//                        }
//
//                        @Override
//                        public void onFailure() {
//
//                        }
//                    });
//                    Log.d("bhrr","dfd");
//                    Log.d("xdjfk",""+investori.size());
//                    ArrayAdapter investoradapter=new ArrayAdapter(Searchactivity.this,android.R.layout.simple_list_item_1,investori);
//                    Log.d("insa",""+investori.size());
//                    searchi.setAdapter(investoradapter);
//                    searchi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//                    show.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d("oneline","abe");
//                            if (!TextUtils.isEmpty(searchi.getText().toString())&&investori.contains(searchi.getText().toString())) {
//                                Transactioninv.clear();
//                                String inveg = investorgroupid.get(investori.indexOf(searchi.getText().toString()));
//                                String usid = investoruserid.get(investori.indexOf(searchi.getText().toString()));
//                                Log.d("twoline","abee");
//                                readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child(inveg).child(usid), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        Tids.clear();
//                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                            Log.d("three",""+dataSnapshot2.getKey());
//                                            Tids.add(dataSnapshot2.getKey());
//                                        }
//                                        Log.d("tidsize", "" + Tids.size());
//                                        for (i = 0; i <= Tids.size() - 1; i++) {
//                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                    Log.d("tranv", "" + Transactioninv.size());
//                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }
//                                        Log.d("ter", "" + Transactioninv.size());
////
//                                    }
//
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure() {
//
//                                    }
//                                });
//
//                            }
//                        }
//                    });
//
//                }
//
//
//
//
//
//
//
//                if (selectedItem.equals("Entry Date")){
//                    show.setVisibility(View.VISIBLE);
//                    searchi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                    month = month + 1;
//                                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                                    String date = day + "-" + month + "-" + year;
//                                    searchi.setText(date);
//                                }
//                            };
//                            newdafor();
//                        }
//                    });
//                    show.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(searchi.getText().toString())) {
//                                Transactioninv.clear();
//
//                                readdata(FirebaseDatabase.getInstance().getReference("Entry Dates").child(searchi.getText().toString()), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        Tids.clear();
//                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                            Tids.add(dataSnapshot2.getValue(String.class));
//                                        }
//                                        Log.d("tidsize", "" + Tids.size());
//                                        for (i = 0; i <= Tids.size() - 1; i++) {
//                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                    Log.d("tranv", "" + Transactioninv.size());
//                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure() {
//
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
//
//
//
//
//
//
//
//
//
//                if (selectedItem.equals("Maturity Date")){
//                    searchi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                    month = month + 1;
//                                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                                    String date = day + "-" + month + "-" + year;
//                                    searchi.setText(date);
//                                }
//                            };
//                            newdafor();
//                        }
//                    });
//                    show.setVisibility(View.VISIBLE);
//                    show.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(searchi.getText().toString())) {
//                                Transactioninv.clear();
//                                readdata(FirebaseDatabase.getInstance().getReference("Maturity Dates").child(searchi.getText().toString()), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        Tids.clear();
//                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                            Tids.add(dataSnapshot2.getValue(String.class));
//                                        }
//                                        Log.d("tidsize", "" + Tids.size());
//                                        for (i = 0; i <= Tids.size() - 1; i++) {
//                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                    Log.d("tranv", "" + Transactioninv.size());
//                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure() {
//
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
//
//
//
//
//
//                if (selectedItem.equals("Amount")){
//                    searchi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//                    show.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(searchi.getText().toString())) {
//                                Transactioninv.clear();
//                                readdata(FirebaseDatabase.getInstance().getReference("Amount Enteries").child(searchi.getText().toString()), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        Tids.clear();
//                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                            Tids.add(dataSnapshot2.getValue(String.class));
//                                        }
//                                        Log.d("tidsize", "" + Tids.size());
//                                        for (i = 0; i <= Tids.size() - 1; i++) {
//                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                    Log.d("tranv", "" + Transactioninv.size());
//                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure() {
//
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
//
//
//                if (selectedItem.equals("Rate of Intrest")){
//                    searchi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//                    show.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(searchi.getText().toString())) {
//                                Transactioninv.clear();
//                                readdata(FirebaseDatabase.getInstance().getReference("Rate Of Intrests").child(searchi.getText().toString()), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        Tids.clear();
//                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                            Tids.add(dataSnapshot2.getValue(String.class));
//                                        }
//                                        Log.d("tidsize", "" + Tids.size());
//                                        for (i = 0; i <= Tids.size() - 1; i++) {
//                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                    Log.d("tranv", "" + Transactioninv.size());
//                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure() {
//
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
//
//
//
//
//
//
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });












//        reciv.setLayoutManager(new LinearLayoutManager(this));
//        reciv.setAdapter(new Searchadapter());
//        seli.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (searchwaySpinner.getSelectedItem().toString().equals("Investor")){
////                    Log.d("haa","hoo");
////                    progibar.setVisibility(View.VISIBLE);
////                    readdata(Investors, new OnGetDataListner() {
////                        @Override
////                        public void onSuccess(DataSnapshot dataSnapshot) {
////                            Log.d("dssd",""+dataSnapshot.getChildrenCount());
////                            investori.clear();
////                            investorgroupid.clear();
////                            investoruserid.clear();
////                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
////                                InvestorMode invo=dataSnapshot1.getValue(InvestorMode.class);
////                                Log.d("fdd",dataSnapshot1.getKey());
////                                investori.add(invo.userName);
////                                Log.d("invosize",""+investori.size());
////                                investoruserid.add(dataSnapshot1.getKey());
////                                investorgroupid.add(invo.Groupid);
////                            }
////                            progibar.setVisibility(View.INVISIBLE);
////                            show.setVisibility(View.VISIBLE);
////                        }
////
////                        @Override
////                        public void onStart() {
////
////                        }
////
////                        @Override
////                        public void onFailure() {
////
////                        }
////                    });
////                    Log.d("bhrr","dfd");
////                    Log.d("xdjfk",""+investori.size());
////                    ArrayAdapter investoradapter=new ArrayAdapter(Searchactivity.this,android.R.layout.simple_list_item_1,investori);
////                    Log.d("insa",""+investori.size());
////                    searchi.setAdapter(investoradapter);
////                    show.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            if (!TextUtils.isEmpty(searchi.getText().toString())&&investori.contains(searchi.getText().toString())) {
////                                Transactioninv.clear();
////
////                                String inveg = investorgroupid.get(investori.indexOf(searchi.getText().toString()));
////                                String usid = investoruserid.get(investori.indexOf(searchi.getText().toString()));
////                                readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child(inveg).child(usid), new OnGetDataListner() {
////                                    @Override
////                                    public void onSuccess(DataSnapshot dataSnapshot) {
////                                        Tids.clear();
////                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
////                                            Tids.add(dataSnapshot2.getKey());
////                                        }
////                                        Log.d("tidsize", "" + Tids.size());
////                                        for (i = 0; i <= Tids.size() - 1; i++) {
////                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
////                                                @Override
////                                                public void onSuccess(DataSnapshot dataSnapshot) {
////                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
////                                                    Log.d("tranv", "" + Transactioninv.size());
////                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
////                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
////                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
////                                                }
////
////                                                @Override
////                                                public void onStart() {
////
////                                                }
////
////                                                @Override
////                                                public void onFailure() {
////
////                                                }
////                                            });
////                                        }
////                                        Log.d("ter", "" + Transactioninv.size());
//////
////                                    }
////
////                                    @Override
////                                    public void onStart() {
////
////                                    }
////
////                                    @Override
////                                    public void onFailure() {
////
////                                    }
////                                });
////
////                            }else {
////                                Toast.makeText(Searchactivity.this,"Select from options",Toast.LENGTH_LONG).show();}
////                        }
////                    });
////
////                }
////                if (searchwaySpinner.getSelectedItem().toString().equals("Reciever")){
////                    Log.d("haa","hoo");
////                    progibar.setVisibility(View.VISIBLE);
////                    readdata(Recievers, new OnGetDataListner() {
////                        @Override
////                        public void onSuccess(DataSnapshot dataSnapshot) {
////                            Log.d("dssd",""+dataSnapshot.getChildrenCount());
////                            investori.clear();
////                            investorgroupid.clear();
////                            investoruserid.clear();
////                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
////                                InvestorMode invo=dataSnapshot1.getValue(InvestorMode.class);
////                                Log.d("fdd",dataSnapshot1.getKey());
////                                investori.add(invo.userName);
////                                Log.d("invosize",""+investori.size());
////                                investoruserid.add(dataSnapshot1.getKey());
////                                investorgroupid.add(invo.Groupid);
////                            }
////                            progibar.setVisibility(View.INVISIBLE);
////                            show.setVisibility(View.VISIBLE);
////                        }
////
////                        @Override
////                        public void onStart() {
////
////                        }
////
////                        @Override
////                        public void onFailure() {
////
////                        }
////                    });
////                    Log.d("bhrr","dfd");
////                    Log.d("xdjfk",""+investori.size());
////                    ArrayAdapter investoradapter=new ArrayAdapter(Searchactivity.this,android.R.layout.simple_list_item_1,investori);
////                    Log.d("insa",""+investori.size());
////                    searchi.setAdapter(investoradapter);
////                    show.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            Log.d("oneline","abe");
////                            if (!TextUtils.isEmpty(searchi.getText().toString())&&investori.contains(searchi.getText().toString())) {
////                                Transactioninv.clear();
////                                String inveg = investorgroupid.get(investori.indexOf(searchi.getText().toString()));
////                                String usid = investoruserid.get(investori.indexOf(searchi.getText().toString()));
////                                Log.d("twoline","abee");
////                                readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child(inveg).child(usid), new OnGetDataListner() {
////                                    @Override
////                                    public void onSuccess(DataSnapshot dataSnapshot) {
////                                        Tids.clear();
////                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
////                                            Log.d("three",""+dataSnapshot2.getKey());
////                                            Tids.add(dataSnapshot2.getKey());
////                                        }
////                                        Log.d("tidsize", "" + Tids.size());
////                                        for (i = 0; i <= Tids.size() - 1; i++) {
////                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
////                                                @Override
////                                                public void onSuccess(DataSnapshot dataSnapshot) {
////                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
////                                                    Log.d("tranv", "" + Transactioninv.size());
////                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
////                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
////                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
////                                                }
////
////                                                @Override
////                                                public void onStart() {
////
////                                                }
////
////                                                @Override
////                                                public void onFailure() {
////
////                                                }
////                                            });
////                                        }
////                                        Log.d("ter", "" + Transactioninv.size());
//////
////                                    }
////
////                                    @Override
////                                    public void onStart() {
////
////                                    }
////
////                                    @Override
////                                    public void onFailure() {
////
////                                    }
////                                });
////
////                            }
////                        }
////                    });
////
////                }
////                if (searchwaySpinner.getSelectedItem().toString().equals("Entry Date")){
////                    show.setVisibility(View.VISIBLE);
////                    searchi.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
////                                @Override
////                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
////                                    month = month + 1;
////                                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
////
////                                    String date = day + "-" + month + "-" + year;
////                                    searchi.setText(date);
////                                }
////                            };
////                            newdafor();
////                        }
////                    });
////                    show.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            if (!TextUtils.isEmpty(searchi.getText().toString())) {
////                                Transactioninv.clear();
////
////                                readdata(FirebaseDatabase.getInstance().getReference("Entry Dates").child(searchi.getText().toString()), new OnGetDataListner() {
////                                    @Override
////                                    public void onSuccess(DataSnapshot dataSnapshot) {
////                                        Tids.clear();
////                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
////                                            Tids.add(dataSnapshot2.getValue(String.class));
////                                        }
////                                        Log.d("tidsize", "" + Tids.size());
////                                        for (i = 0; i <= Tids.size() - 1; i++) {
////                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
////                                                @Override
////                                                public void onSuccess(DataSnapshot dataSnapshot) {
////                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
////                                                    Log.d("tranv", "" + Transactioninv.size());
////                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
////                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
////                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
////                                                }
////
////                                                @Override
////                                                public void onStart() {
////
////                                                }
////
////                                                @Override
////                                                public void onFailure() {
////
////                                                }
////                                            });
////                                        }
////                                    }
////
////                                    @Override
////                                    public void onStart() {
////
////                                    }
////
////                                    @Override
////                                    public void onFailure() {
////
////                                    }
////                                });
////                            }
////                        }
////                    });
////                }
//                if (searchwaySpinner.getSelectedItem().toString().equals("Maturity Date")){
//                    searchi.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                    month = month + 1;
//                                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//                                    String date = day + "-" + month + "-" + year;
//                                    searchi.setText(date);
//                                }
//                            };
//                            newdafor();
//                        }
//                    });
//                    show.setVisibility(View.VISIBLE);
//                    show.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(searchi.getText().toString())) {
//                                Transactioninv.clear();
//                                readdata(FirebaseDatabase.getInstance().getReference("Maturity Dates").child(searchi.getText().toString()), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        Tids.clear();
//                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                            Tids.add(dataSnapshot2.getValue(String.class));
//                                        }
//                                        Log.d("tidsize", "" + Tids.size());
//                                        for (i = 0; i <= Tids.size() - 1; i++) {
//                                            readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                    Log.d("tranv", "" + Transactioninv.size());
//                                                    Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                    reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                    reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure() {
//
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
//                if (searchwaySpinner.getSelectedItem().toString().equals("Mode")){}
//            }
//        });


























         investorcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                 group group=new group();
                 Bundle bundle=new Bundle();
                 bundle.putString("transactiontogroup","investorsearch");
                 group.setArguments(bundle);
                 group.show(getSupportFragmentManager(),"ohk");
                 Log.d("bhrr","dfd");
                 Log.d("xdjfk",""+investori.size());
                 Log.d("insa",""+investori.size());

                 }
         });
         ModecheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                     Modedialog modedialog=new Modedialog();
                     Bundle bundle=new Bundle();
                     bundle.putString("Mode","searchmode");
                     modedialog.setArguments(bundle);
                     modedialog.show(getSupportFragmentManager(),"ohkto");

                 }
             }
         });
         Amountcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                     Dialogboxforsearchenteries dialogboxforsearchenteries=new Dialogboxforsearchenteries();
                     Bundle bundle=new Bundle();
                     bundle.putString("dialogtitle","Amount");
                     bundle.putString("editablehint","EnterAmount");
                     dialogboxforsearchenteries.setArguments(bundle);
                     dialogboxforsearchenteries.show(getSupportFragmentManager(),"dknih");
                 }
             }
         });
         ROIcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                     Dialogboxforsearchenteries dialogboxforsearchenteries=new Dialogboxforsearchenteries();
                     Bundle bundle=new Bundle();
                     bundle.putString("dialogtitle","R.O.I");
                     bundle.putString("editablehint","Enter R.O.I");
                     dialogboxforsearchenteries.setArguments(bundle);
                     dialogboxforsearchenteries.show(getSupportFragmentManager(),"dknih");
                 }
             }
         });
        recievercheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                group group=new group();
                Bundle bundle=new Bundle();
                bundle.putString("transactiontogroup","recieversearch");
                group.setArguments(bundle);
                group.show(getSupportFragmentManager(),"ohk");
                Log.d("bhrr","dfd");
                Log.d("xdjfk",""+investori.size());
                Log.d("insa",""+recieveri.size());

            }
        });
        maturitydatecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                                String date = day + "-" + month + "-" + year;
                                selectfielddata.setText(date);
                                maturitydatecheckboxselection=date;
                                Priority[3]=date;
                            }
                        };
                        newdafor();

            }
        });
        entrydatecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                                String date = day + "-" + month + "-" + year;
                                selectfielddata.setText(date);
                                entrydatecheckboxselection=date;
                                Priority[2]=date;
                            }
                        };
                        newdafor();

            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (investorcheckbox.isChecked() || recievercheckbox.isChecked() || entrydatecheckbox.isChecked() || maturitydatecheckbox.isChecked()){
                    if (investorcheckbox.isChecked() && !investorcheckboxselectionuserid.equals("")){
                        Transactioninv.clear();
                        Tids.clear();
                        Query query = FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("investoruid").equalTo(investorcheckboxselectionuserid);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
                                    Tids.add(dataSnapshot1.getKey());

                                }
                                String caso="";
                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")&&entrydatecheckbox.isChecked()&&maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")&&!entrydatecheckboxselection.equals("")){
                                    caso="0";
                                }
                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")&&entrydatecheckbox.isChecked()&&!entrydatecheckboxselection.equals("")){
                                    caso="1";
                                }
                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")&&maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")){
                                    caso="2";
                                }
                                if (entrydatecheckbox.isChecked()&&maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")&&!entrydatecheckboxselection.equals("")){
                                    caso="3";
                                }
                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")){
                                    caso="4";
                                }
                                if (entrydatecheckbox.isChecked()&&!entrydatecheckboxselection.equals("")){
                                    caso="5";
                                }
                                if (maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")){
                                    caso="6";
                                }



                                switch (caso){
                                    case "0":
                                        for (int i=0;i<Tids.size();i++){
                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection)&&Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection)&&Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
                                                Transactioninv.remove(i);
                                                Tids.remove(i);
                                            }
                                        }
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        break;
                                    case "1":
                                        for (int i=0;i<Tids.size();i++){
                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection)&&Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
                                                Transactioninv.remove(i);
                                                Tids.remove(i);
                                            }
                                        }
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        break;
                                    case "2":
                                        for (int i=0;i<Tids.size();i++){
                                            if (!(Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection)&&Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
                                                Transactioninv.remove(i);
                                                Tids.remove(i);
                                            }
                                        }
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        break;
                                    case "3":
                                        for (int i=0;i<Tids.size();i++){
                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection)&&Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection))){
                                                Transactioninv.remove(i);
                                                Tids.remove(i);
                                            }
                                        }
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        break;
                                    case "4":
                                        for (int i=0;i<Tids.size();i++){
                                            if (!(Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
                                                Transactioninv.remove(i);
                                                Tids.remove(i);
                                            }
                                        }
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        break;
                                    case "5":
                                        for (int i=0;i<Tids.size();i++){
                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection))){
                                                Transactioninv.remove(i);
                                                Tids.remove(i);
                                            }
                                        }
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        break;
                                    case "6":
                                        for (int i=0;i<Tids.size();i++){
                                            if (!(Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection))){
                                                Transactioninv.remove(i);
                                                Tids.remove(i);
                                            }
                                        }
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        break;
                                    default:
                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }else {
                        if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")){
                            Transactioninv.clear();
                            Tids.clear();
                            Query query = FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("recieveruid").equalTo(recievercheckboxselectionuserid);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
                                        Tids.add(dataSnapshot1.getKey());

                                    }
                                    String caso="";
                                    if (entrydatecheckbox.isChecked()&&maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")&&!entrydatecheckboxselection.equals("")){
                                        caso="0";
                                    }
                                    if (entrydatecheckbox.isChecked()&&!entrydatecheckboxselection.equals("")){
                                        caso="1";
                                    }
                                    if (maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")){
                                        caso="2";
                                    }
                                    switch (caso){
                                        case "0":
                                            for (int i=0;i<Tids.size();i++){
                                                if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection)&&Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection))){
                                                    Transactioninv.remove(i);
                                                    Tids.remove(i);
                                                }
                                            }
                                            reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                            reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                            break;
                                        case "1":
                                            for (int i=0;i<Tids.size();i++){
                                                if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection))){
                                                    Transactioninv.remove(i);
                                                    Tids.remove(i);
                                                }
                                            }
                                            reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                            reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                            break;
                                        case "2":
                                            for (int i=0;i<Tids.size();i++){
                                                if (!(Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection))){
                                                    Transactioninv.remove(i);
                                                    Tids.remove(i);
                                                }
                                            }
                                            reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                            reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                            break;

                                        default:
                                            reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                            reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));

                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }else {
                            if (entrydatecheckbox.isChecked()&&!entrydatecheckboxselection.equals("")){
                                Transactioninv.clear();
                                Tids.clear();
                                Query query = FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("entrydatieedit").equalTo(entrydatecheckboxselection);
                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
                                            Tids.add(dataSnapshot1.getKey());

                                        }
                                        String caso="";
                                        if (maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")){
                                            caso="0";
                                        }switch (caso){
                                            case "0":
                                                for (int i=0;i<Tids.size();i++){
                                                    if (!(Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection))){
                                                        Transactioninv.remove(i);
                                                        Tids.remove(i);
                                                    }
                                                }
                                                reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                                reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                                break;

                                            default:
                                                reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                                reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }else {
                                if (maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")){
                                    Transactioninv.clear();
                                    Tids.clear();
                                    Query query = FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("maturitydateedit").equalTo(maturitydatecheckboxselection);
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
                                                Tids.add(dataSnapshot1.getKey());

                                            }
                                            reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
                                            reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(Searchactivity.this,"Unable to update Profile",Toast.LENGTH_SHORT).show();

                }
            }
        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (investorcheckbox.isChecked() || recievercheckbox.isChecked() || entrydatecheckbox.isChecked() || maturitydatecheckbox.isChecked()) {
//                    if (investorcheckbox.isChecked() && !investorcheckboxselectionuserid.equals("")) {
//                        Transactioninv.clear();
//                        Tids.clear();
//                        Query query = FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("investoruid").equalTo(investorcheckboxselectionuserid);
//                        query.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                    Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
//                                    Tids.add(dataSnapshot1.getKey());
//
//                                }
//                                String caso="";
//                                if (investorcheckbox.isChecked()){
//
//                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")&&entrydatecheckbox.isChecked()&&maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")&&!entrydatecheckboxselection.equals("")){
//                                    caso="0";
//                                }
//                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")&&entrydatecheckbox.isChecked()&&!entrydatecheckboxselection.equals("")){
//                                    caso="1";
//                                }
//                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")&&maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")){
//                                    caso="2";
//                                }
//                                if (entrydatecheckbox.isChecked()&&maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")&&!entrydatecheckboxselection.equals("")){
//                                    caso="3";
//                                }
//                                if (recievercheckbox.isChecked()&&!recievercheckboxselectionuserid.equals("")){
//                                    caso="4";
//                                }
//                                if (entrydatecheckbox.isChecked()&&!entrydatecheckboxselection.equals("")){
//                                    caso="5";
//                                }
//                                if (maturitydatecheckbox.isChecked()&&!maturitydatecheckboxselection.equals("")){
//                                    caso="6";
//                                }
//                                switch (caso){
//                                    case "0":
//                                        for (int i=0;i<Tids.size();i++){
//                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection)&&Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection)&&Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
//                                                Transactioninv.remove(i);
//                                                Tids.remove(i);
//                                            }
//                                        }
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                        break;
//                                    case "1":
//                                        for (int i=0;i<Tids.size();i++){
//                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection)&&Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
//                                                Transactioninv.remove(i);
//                                                Tids.remove(i);
//                                            }
//                                        }
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                        break;
//                                    case "2":
//                                        for (int i=0;i<Tids.size();i++){
//                                            if (!(Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection)&&Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
//                                                Transactioninv.remove(i);
//                                                Tids.remove(i);
//                                            }
//                                        }
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                        break;
//                                    case "3":
//                                        for (int i=0;i<Tids.size();i++){
//                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection)&&Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection))){
//                                                Transactioninv.remove(i);
//                                                Tids.remove(i);
//                                            }
//                                        }
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                        break;
//                                    case "4":
//                                        for (int i=0;i<Tids.size();i++){
//                                            if (!(Transactioninv.get(i).recieveruid.equals(recievercheckboxselectionuserid))){
//                                                Transactioninv.remove(i);
//                                                Tids.remove(i);
//                                            }
//                                        }
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                        break;
//                                    case "5":
//                                        for (int i=0;i<Tids.size();i++){
//                                            if (!(Transactioninv.get(i).entrydatieedit.equals(entrydatecheckboxselection))){
//                                                Transactioninv.remove(i);
//                                                Tids.remove(i);
//                                            }
//                                        }
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                        break;
//                                    case "6":
//                                        for (int i=0;i<Tids.size();i++){
//                                            if (!(Transactioninv.get(i).maturitydateedit.equals(maturitydatecheckboxselection))){
//                                                Transactioninv.remove(i);
//                                                Tids.remove(i);
//                                            }
//                                        }
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                        break;
//                                    default:
//                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//
//                                }
//
//
//                            }else{
//                                if (recievercheckbox.isChecked()){
//                                    Transactioninv.clear();
//                                    String inveg="";
//                                    String usid="";
//                                    Log.d("twoline","abee");
//                                    readdata(FirebaseDatabase.getInstance().getReference("Allgroups").child(inveg).child(usid), new OnGetDataListner() {
//                                        @Override
//                                        public void onSuccess(DataSnapshot dataSnapshot) {
//                                            Tids.clear();
//                                            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                                Log.d("three",""+dataSnapshot2.getKey());
//                                                Tids.add(dataSnapshot2.getKey());
//                                            }
//                                            Log.d("tidsize", "" + Tids.size());
//                                            for (i = 0; i <= Tids.size() - 1; i++) {
//                                                readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                    @Override
//                                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                                        Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                        Log.d("tranv", "" + Transactioninv.size());
//                                                        Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                        reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                        reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                    }
//
//                                                    @Override
//                                                    public void onStart() {
//
//                                                    }
//
//                                                    @Override
//                                                    public void onFailure() {
//
//                                                    }
//                                                });
//                                            }
//                                            Log.d("ter", "" + Transactioninv.size());
////
//                                        }
//
//                                        @Override
//                                        public void onStart() {
//
//                                        }
//
//                                        @Override
//                                        public void onFailure() {
//
//                                        }
//                                    });
//                                }else{
//                                    if (maturitydatecheckbox.isChecked()){
//                                        if (!maturitydatecheckboxselection.equals("")) {
//                                            Transactioninv.clear();
//                                            readdata(FirebaseDatabase.getInstance().getReference("Maturity Dates").child(maturitydatecheckboxselection), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Tids.clear();
//                                                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                                        Tids.add(dataSnapshot2.getValue(String.class));
//                                                    }
//                                                    Log.d("tidsize", "" + Tids.size());
//                                                    for (i = 0; i <= Tids.size() - 1; i++) {
//                                                        readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                            @Override
//                                                            public void onSuccess(DataSnapshot dataSnapshot) {
//                                                                Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                                Log.d("tranv", "" + Transactioninv.size());
//                                                                Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                                reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                                reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                            }
//
//                                                            @Override
//                                                            public void onStart() {
//
//                                                            }
//
//                                                            @Override
//                                                            public void onFailure() {
//
//                                                            }
//                                                        });
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }else {Toast.makeText(Searchactivity.this,"Please select maturity date",Toast.LENGTH_LONG).show();}
//                                    }else {
//                                        if (!entrydatecheckboxselection.equals("")) {
//                                            Transactioninv.clear();
//
//                                            readdata(FirebaseDatabase.getInstance().getReference("Entry Dates").child(entrydatecheckboxselection), new OnGetDataListner() {
//                                                @Override
//                                                public void onSuccess(DataSnapshot dataSnapshot) {
//                                                    Tids.clear();
//                                                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                                        Tids.add(dataSnapshot2.getValue(String.class));
//                                                    }
//                                                    Log.d("tidsize", "" + Tids.size());
//                                                    for (i = 0; i <= Tids.size() - 1; i++) {
//                                                        readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(Tids.get(i)), new OnGetDataListner() {
//                                                            @Override
//                                                            public void onSuccess(DataSnapshot dataSnapshot) {
//                                                                Transactioninv.add(dataSnapshot.getValue(TransactionentryModel.class));
//                                                                Log.d("tranv", "" + Transactioninv.size());
//                                                                Log.d("dfjdfi", Transactioninv.get(0).getInvestoredit());
//                                                                reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                                                                reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//                                                            }
//
//                                                            @Override
//                                                            public void onStart() {
//
//                                                            }
//
//                                                            @Override
//                                                            public void onFailure() {
//
//                                                            }
//                                                        });
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onStart() {
//
//                                                }
//
//                                                @Override
//                                                public void onFailure() {
//
//                                                }
//                                            });
//                                        }else {Toast.makeText(Searchactivity.this,"Please select Entry Date",Toast.LENGTH_LONG).show();}
//                                    }
//                                }
//                            }
//
//                        }
//
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//                    }
//                }
//            }
//        });

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progibar.setVisibility(View.VISIBLE);
//                Transactioninv.clear();
//                Tids.clear();
//                int flag=0;
//                for (i=0;i<7;i++){
//                    if (flag!=0){
//                        if (checkBoxes.get(i).isChecked()&&Priority[i]!=null){
//
//                                if (i==1){
//                                    for (int j=0;j<Tids.size();j++) {
//                                    if (!Transactioninv.get(j).recieveredit.equals(Priority[1])) {
//                                        Transactioninv.remove(j);
//                                        Tids.remove(j);
//                                    }
//                                }
//                                        Log.d("1wala",""+Transactioninv.size());
//                                }
//                                Log.d("1walake bahar",""+Transactioninv.size());
//                                Log.d("valuie",Transactioninv.toString());
//
//                                if (i==2){
//                                    for (int j=0;j<Tids.size();j++) {
//                                        if (!Transactioninv.get(j).entrydatieedit.equals(Priority[2])) {
//                                            Transactioninv.remove(j);
//                                            Tids.remove(j);
//                                        }
//                                    }
//                                }
//                                if (i==3){
//                                    for (int j=0;j<Tids.size();j++) {
//                                        if (!Transactioninv.get(j).maturitydateedit.equals(Priority[3])) {
//                                            Transactioninv.remove(j);
//                                            Tids.remove(j);
//                                        }
//                                    }
//                                }
//                                if (i==4){
//                                    for (int j=0;j<Tids.size();j++) {
//                                        if (!Transactioninv.get(j).recieveredit.equals(Priority[4])) {
//                                            Transactioninv.remove(j);
//                                            Tids.remove(j);
//                                        }
//                                    }
//                                }
//                                if (i==5){
//                                    for (int j=0;j<Tids.size();j++) {
//                                    if (!Transactioninv.get(j).recieveredit.equals(Priority[5])) {
//                                        Transactioninv.remove(j);
//                                        Tids.remove(j);
//                                    }
//                                    }
//                                }
//                                if (i==6){
//                                    for (int j=0;j<Tids.size();j++) {
//                                        if (!Transactioninv.get(j).recieveredit.equals(Priority[6])) {
//                                            Transactioninv.remove(j);
//                                            Tids.remove(j);
//                                        }
//                                    }
//                                }
//
//                            Log.d("valuie1",Transactioninv.toString());
//                        }
//                        Log.d("valuie2",Transactioninv.toString());
//                    }else{
//                        if (checkBoxes.get(i).isChecked()&&Priority[i]!=null){
//                            Query query=FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild(databasevariables[i]).equalTo(Priority[i]);
//                            query.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                                        Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
//                                        Tids.add(dataSnapshot1.getKey());
//                                    }
//                                    Log.d("valuie3",Transactioninv.toString());
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });
//                            flag=1;
//                        }
//                    }
//                    Log.d("valuie4",Transactioninv.toString());
//                }
//
//                Log.d("valuie5",Transactioninv.toString());
//
//                reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//            }
//        });

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("enteringonclick","a"+Amountcheckboxselection+"b");
//                if (Amountcheckbox.isChecked()&&!Amountcheckboxselection.equals("")){
//                    Log.d("enteringifstatement","a"+Amountcheckboxselection+"b");
//                   Query query=FirebaseDatabase.getInstance().getReference("Transaction Enteries").orderByChild("amountedit").equalTo(Amountcheckboxselection);
//                   query.addValueEventListener(new ValueEventListener() {
//                       @Override
//                       public void onDataChange(DataSnapshot dataSnapshot) {
//                           Log.d("eeeafteren",dataSnapshot.toString());
//                           for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                               Transactioninv.add(dataSnapshot1.getValue(TransactionentryModel.class));
//                               Tids.add(dataSnapshot1.getKey());
//                           }
//                           reciv.setLayoutManager(new LinearLayoutManager(Searchactivity.this));
//                           reciv.setAdapter(new Searchadapter(Transactioninv,Searchactivity.this,Tids));
//
//                       }
//
//                       @Override
//                       public void onCancelled(DatabaseError databaseError) {
//
//                       }
//                   });
//                }
//            }
//        });


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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        currentDateString= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        searchi.setText(currentDateString);
    }
    public void newdafor(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                Searchactivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }




    @Override
    public void respond(String s, String y, String z, String a) {
        if (a.equals("investorsearch")){
            investorcheckboxselectiongroupid=y;
            investorcheckboxselectionuserid=s;
            selectfielddata.setText(z);
            Priority[0]=z;
        }
        if (a.equals("recieversearch")){
            recievercheckboxselectiongroupid=y;
            recievercheckboxselectionuserid=s;
            selectfielddata.setText(z);
            Priority[1]=z;
        }
        if (a.equals("R.O.I")){
            ROIcheckboxselection=s;
            selectfielddata.setText(s);
            Priority[5]=s;
        }
        if (a.equals("Amount")){
            Amountcheckboxselection=s;
            selectfielddata.setText(s);
            Priority[4]=s;
        }
        if (a.equals("Mode")){
            Modecheckboxselection=s;
            selectfielddata.setText(s);
            Priority[6]=s;
        }



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
        Intent intent=new Intent(Searchactivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return true;
    }
}
