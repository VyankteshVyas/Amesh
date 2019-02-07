package com.example.vyanktesh.amesh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
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

public class TransactionEntryNew extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,Filename {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "MainActivity";
    DatabaseReference Allgroups,TransactionEntries,EntryDates,MaturityDates,Amounts,Investors,Recievers,Rateofintrest;
    String currentDateString;
    String formattedDate="";
    Toolbar toolo;
    String bankname=new String();
    String bankbranch=new String();
    ToggleButton toggleButton;
    List<String> investori=new ArrayList<>();
    List<String> recieveri=new ArrayList<>();
    List<String> investorgroupid=new ArrayList<>();
    List<String> investoruserid=new ArrayList<>();
    List<String> recievergroupid=new ArrayList<>();
    List<String> recieveruserid=new ArrayList<>();
    String idOfClickedItem;
    AutoCompleteTextView investoredit,recieveredit;
    TextView entrydatieedit,maturitydateedit,chequedateedit;
    TextView hiddenreciever,hiddeninvestor,hiddenentrydate;
    EditText amountedit,ROI,Intrestedit,TDS,chequenoedit,Bank,Branch,Brokerage,TDSBrokerage,Remarks;
    TextInputLayout investortextinputlayout,recievertextinputlayout,amountdittextinputlayout,roidittextinputlayout,intrestedittextinputlayout,TDSedittextinputlayout;
    Spinner spinnermode;
    ProgressBar progressBar;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_transaction_entry_new);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        investoredit=findViewById(R.id.investoredit);
        recieveredit=findViewById(R.id.recieveredit);
        entrydatieedit=findViewById(R.id.Entrydateedit);
        maturitydateedit=findViewById(R.id.Maturitydateedit);
        amountedit=findViewById(R.id.amountdit);
        ROI=findViewById(R.id.ROIedit);
        TDS=findViewById(R.id.TDSedit);
        spinnermode=findViewById(R.id.spinnermode);
        chequenoedit=findViewById(R.id.Chequenoedit);
        Intrestedit=findViewById(R.id.intrestedit);
        chequedateedit=findViewById(R.id.Chequedateedit);
        Bank=findViewById(R.id.Bankedit);
        Branch=findViewById(R.id.Branchedit);
        Brokerage=findViewById(R.id.Brokerageedit);
        TDSBrokerage=findViewById(R.id.TDSBrokerageedit);
        Remarks=findViewById(R.id.Remarksedit);
        submit=findViewById(R.id.submittransactiontofirebase);
        progressBar =findViewById(R.id.progressBar2);
        progressBar.setIndeterminate(true);
        toggleButton=findViewById(R.id.togglehidden);
        investortextinputlayout=findViewById(R.id.investortextinputlayout);
        recievertextinputlayout=findViewById(R.id.recievertextinputlayout);
        amountdittextinputlayout=findViewById(R.id.amountdittextinputlayout);
        roidittextinputlayout=findViewById(R.id.roidittextinputlayout);
        intrestedittextinputlayout=findViewById(R.id.intrestedittextinputlayout);
        TDSedittextinputlayout=findViewById(R.id.TDSedittextinputlayout);
        Investors=FirebaseDatabase.getInstance().getReference("Investors");
        Recievers=FirebaseDatabase.getInstance().getReference("Recievers");
        Allgroups=FirebaseDatabase.getInstance().getReference("Allgroups");
        TransactionEntries=FirebaseDatabase.getInstance().getReference("Transaction Enteries");
        EntryDates=FirebaseDatabase.getInstance().getReference("Entry Dates");
        MaturityDates=FirebaseDatabase.getInstance().getReference("Maturity Dates");
        Amounts=FirebaseDatabase.getInstance().getReference("Amount Enteries");
        Rateofintrest=FirebaseDatabase.getInstance().getReference("Rate Of Intrests");
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
//        hiddeninvestor=findViewById(R.id.hiddeninvestor);
//        hiddenreciever=findViewById(R.id.hiddenreciever);
        entrydatieedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                        String date = day + "-" + month + "-" + year;
                        entrydatieedit.setText(date);
                    }
                };
                newdafor();
            }
        });
        maturitydateedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                        String date = day + "-" + month + "-" + year;
                        maturitydateedit.setText(date);
                    }
                };
                newdafor();
            }
        });
        chequedateedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                        String date = day + "-" + month + "-" + year;
                        chequedateedit.setText(date);
                    }
                };
                newdafor();
            }
        });
        String verify=getIntent().getStringExtra("verify");
        if (verify.equals("1")){
            progressBar.setVisibility(View.VISIBLE);
            readdata(Investors, new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Log.d("iiu","oid");
                    investori.clear();
                    investorgroupid.clear();
                    investoruserid.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        InvestorMode invo=dataSnapshot1.getValue(InvestorMode.class);
                        investori.add(invo.userName);
                        investoruserid.add(dataSnapshot1.getKey());
                        investorgroupid.add(invo.Groupid);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onStart() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure() {

                }
            });
            readdata(Recievers, new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    recieveri.clear();
                    recievergroupid.clear();
                    recieveruserid.clear();
                    for (DataSnapshot recovo: dataSnapshot.getChildren()){
                        RecieverModel recieverModel=recovo.getValue(RecieverModel.class);
                        recieveri.add(recieverModel.userName);
                        recieveruserid.add(recovo.getKey());
                        recievergroupid.add(recieverModel.Groupid);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onStart() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure() {

                }
            });
            ArrayAdapter investoradapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,investori);
            investoredit.setAdapter(investoradapter);

            ArrayAdapter recieveradapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,recieveri);
            recieveredit.setAdapter(recieveradapter);
            final String transactionidhidden=getIntent().getStringExtra("transactionidhidden");


            final String maturityid=getIntent().getStringExtra("maturitydateid");
            final String hiddeninvestoruid=getIntent().getStringExtra("investoruid");
            final String hiddeninvestorgroupid=getIntent().getStringExtra("hiddeninvestorgroupid");
            final String hiddenrecievergroupid=getIntent().getStringExtra("hiddenrecievergroupid");
            final String hiddenrecieveruid=getIntent().getStringExtra("recieveruid");
            final String entryid=getIntent().getStringExtra("entryid");
            final String hiddenmaturitydate=getIntent().getStringExtra("maturitydatehidden");
            final String hiddenentrydate=getIntent().getStringExtra("entrydatehidden");
            final String amountid=getIntent().getStringExtra("amountid");
            final String roiid=getIntent().getStringExtra("roiid");
            final String hiddenamount=getIntent().getStringExtra("amounthidden");
            final String hiddenroi=getIntent().getStringExtra("roihidden");
            progressBar.setVisibility(View.INVISIBLE);
            entrydatieedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;
                            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                            String date = day + "-" + month + "-" + year;
                            entrydatieedit.setText(date);
                        }
                    };
                    newdafor();
                }
            });
            maturitydateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;
                            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                            String date = day + "-" + month + "-" + year;
                            maturitydateedit.setText(date);
                        }
                    };
                    newdafor();
                }
            });
            chequedateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;
                            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                            String date = day + "-" + month + "-" + year;
                            chequedateedit.setText(date);
                        }
                    };
                    newdafor();
                }
            });

            spinnermode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position==1)
                    {
                        chequedateedit.setVisibility(View.INVISIBLE);
                        chequenoedit.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit.setEnabled(false);
                    String pushkey=FirebaseDatabase.getInstance().getReference("PreMaturitydates").child(transactionidhidden).push().getKey();
                    moveFirebaseRecord(new Firebase("https://amesh-e678e.firebaseio.com/Transaction Enteries/"+transactionidhidden),new Firebase("https://amesh-e678e.firebaseio.com/PreMaturitydates/"+transactionidhidden+"/"+pushkey));
//                    MaturityDates.child(hiddenmaturitydate).child(maturityid).setValue(null);
//                    EntryDates.child(hiddenentrydate).child(entryid).setValue(null);
//                    Amounts.child(hiddenamount).child(amountid).setValue(null);
//                    Rateofintrest.child(hiddenroi).child(roiid).setValue(null);
                    Log.d("1stline",""+hiddenmaturitydate+"{{{"+maturityid);
                    String ama = MaturityDates.child(maturitydateedit.getText().toString()).push().getKey();
                    String eama=EntryDates.child(entrydatieedit.getText().toString()).push().getKey();
                    String newamountid=Amounts.child(amountedit.getText().toString()).push().getKey();
                    String newroiid=Rateofintrest.child(ROI.getText().toString()).push().getKey();
                    Log.d("ama ki value",""+ama);
//                    MaturityDates.child(maturitydateedit.getText().toString()).child(ama).setValue(transactionidhidden);
//                    EntryDates.child(entrydatieedit.getText().toString()).child(eama).setValue(transactionidhidden);
//                    Rateofintrest.child(roi.getText().toString()).child(newroiid).setValue(transactionidhidden);
//                    Amounts.child(amountedit.getText().toString()).child(newamountid).setValue(transactionidhidden);
                    if(toggleButton.isChecked()){
                    final TransactionentryModel transactionentryModel1=new TransactionentryModel(hiddeninvestor.getText().toString(),hiddenreciever.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"active",hiddeninvestoruid,hiddenrecieveruid,"1",hiddeninvestorgroupid,hiddenrecievergroupid);
                        FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(transactionidhidden).setValue(transactionentryModel1);
                    }else {
                        final TransactionentryModel transactionentryModel1=new TransactionentryModel(hiddeninvestor.getText().toString(),hiddenreciever.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"active",hiddeninvestoruid,hiddenrecieveruid,"0",hiddeninvestorgroupid,hiddenrecievergroupid);
                        FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(transactionidhidden).setValue(transactionentryModel1);
                    }


                }
            });


        }
        readdata(Investors, new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.d("iiu","oid");
                investori.clear();
                investorgroupid.clear();
                investoruserid.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    InvestorMode invo=dataSnapshot1.getValue(InvestorMode.class);
                    investori.add(invo.userName);
                    investoruserid.add(dataSnapshot1.getKey());
                    investorgroupid.add(invo.Groupid);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure() {

            }
        });
        readdata(Recievers, new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                recieveri.clear();
                recievergroupid.clear();
                recieveruserid.clear();
                for (DataSnapshot recovo: dataSnapshot.getChildren()){
                    RecieverModel recieverModel=recovo.getValue(RecieverModel.class);
                    recieveri.add(recieverModel.userName);
                    recieveruserid.add(recovo.getKey());
                    recievergroupid.add(recieverModel.Groupid);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure() {

            }
        });
        ArrayAdapter investoradapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,investori);
        investoredit.setAdapter(investoradapter);

        ArrayAdapter recieveradapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,recieveri);
        recieveredit.setAdapter(recieveradapter);
        if (verify.equals("0")){
            investoredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    group group=new group();
                    Bundle bundle=new Bundle();
                    bundle.putString("transactiontogroup","investortransaction");
                    group.setArguments(bundle);
                    group.show(getSupportFragmentManager(),"ohk");
                }
            });
            recieveredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    group group=new group();
                    Bundle bundle=new Bundle();
                    bundle.putString("transactiontogroup","recievertransaction");
                    group.setArguments(bundle);
                    group.show(getSupportFragmentManager(),"ohk");
                }
            });


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (!validateinvestor()){
//                        return;
//                    }
                    if (!TextUtils.isEmpty(recieveredit.getText()) && !TextUtils.isEmpty(investoredit.getText()) && !TextUtils.isEmpty(amountedit.getText()) && !TextUtils.isEmpty(maturitydateedit.getText()) && !TextUtils.isEmpty(entrydatieedit.getText())) {
//                        submit.setEnabled(false);
//
                        Log.d("recie", "onClick: " + "''" + recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())) + ";;" + recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())));
                        Log.d("hello", "hai");
                        String transactionid=TransactionEntries.push().getKey();
                        String maturityid=MaturityDates.push().getKey();
                        String entryid=EntryDates.push().getKey();
                        String amountid=Amounts.push().getKey();
                        String  roiid=Rateofintrest.push().getKey();
                        if (toggleButton.isChecked()){
                            TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"1",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                            TransactionEntries.child(transactionid).setValue(transactionentryModel);



                        }else {
                            TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"0",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                            TransactionEntries.child(transactionid).setValue(transactionentryModel);

                        }
//                        MaturityDates.child(maturitydateedit.getText().toString()).child(maturityid).setValue(transactionid);
//                        EntryDates.child(entrydatieedit.getText().toString()).child(entryid).setValue(transactionid);
//                        Amounts.child(amountedit.getText().toString()).child(amountid).setValue(transactionid);
//                        Rateofintrest.child(roi.getText().toString()).child(roiid).setValue(transactionid);
//                        FirebaseDatabase.getInstance().getReference("PreMaturitydates").child(transactionid).push().setValue(transactionentryModel);
//                        Allgroups.child(investorgroupid.get(investori.indexOf(investoredit.getText().toString()))).child(investoruserid.get(investori.indexOf(investoredit.getText().toString()))).child(transactionid).setValue("active");
//                        Allgroups.child(recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString()))).child(recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString()))).child(transactionid).setValue("active");
//                Allgroups.child(recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString()))).child()
//                Allgroups.getDatabase().getReference(investorgroupid.get(investori.indexOf(investoredit.getText().toString()))).child(investoruserid.get(investori.indexOf(investoredit.getText().toString()))).child(transactionid).setValue("Active");
//                Allgroups.getDatabase().getReference(recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString()))).child(recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString()))).child(transactionid).setValue("Active");
//                Log.d("djfkdj",""+investorgroupid.get(investori.indexOf(investoredit.getText().toString())));

                    } else {
                        Toast.makeText(TransactionEntryNew.this, "Please make sure if maturity date,entry date, amount, reciver and investor are filled", Toast.LENGTH_LONG).show();
                    }
                    Intent intent=new Intent(TransactionEntryNew.this,AdminPanel.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            });





        }
        if (verify.equals("2")){
            submit.setText("Edit");
            final String Tids=getIntent().getStringExtra("Tids");
            readdata(TransactionEntries.child(Tids), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    TransactionentryModel transactionentryModel=dataSnapshot.getValue(TransactionentryModel.class);
                    investoredit.setText(transactionentryModel.investoredit);
                    recieveredit.setText(transactionentryModel.recieveredit);
                    entrydatieedit.setText(transactionentryModel.entrydatieedit);
                    maturitydateedit.setText(transactionentryModel.maturitydateedit);
                    amountedit.setText(transactionentryModel.amountedit);
                    ROI.setText(transactionentryModel.roi);
                    Intrestedit.setText(transactionentryModel.intrestedit);
                    TDS.setText(transactionentryModel.tds);
                    if (transactionentryModel.spinnermode.equals("Investor")){
                        spinnermode.setSelection(0);
                    }else spinnermode.setSelection(1);
                    chequenoedit.setText(transactionentryModel.chequenoedit);
                    chequedateedit.setText(transactionentryModel.chequedateedit);
                    Bank.setText(transactionentryModel.bank);
                    Branch.setText(transactionentryModel.branch);
                    Brokerage.setText(transactionentryModel.brokerage);
                    TDSBrokerage.setText(transactionentryModel.tdsbrokerage);
                    Remarks.setText(transactionentryModel.remarks);
                    if (transactionentryModel.hidden.equals("1")){
                        toggleButton.setChecked(true);
                    }else toggleButton.setChecked(false);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (toggleButton.isChecked()){
                                TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"1",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                                TransactionEntries.child(Tids).setValue(transactionentryModel);



                            }else {
                                TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"0",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                                TransactionEntries.child(Tids).setValue(transactionentryModel);

                            }
                            Intent intent=new Intent(TransactionEntryNew.this,AdminPanel.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (toggleButton.isChecked()){
                        TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"1",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                        TransactionEntries.child(Tids).setValue(transactionentryModel);



                    }else {
                        TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"0",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                        TransactionEntries.child(Tids).setValue(transactionentryModel);

                    }
                    Intent intent=new Intent(TransactionEntryNew.this,AdminPanel.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });

        }
        if (verify.equals("3")){
            submit.setText("Renew");
            final String Tids=getIntent().getStringExtra("Tids");
            readdata(TransactionEntries.child(Tids), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    TransactionentryModel transactionentryModel=dataSnapshot.getValue(TransactionentryModel.class);
                    investoredit.setText(transactionentryModel.investoredit);
                    recieveredit.setText(transactionentryModel.recieveredit);
                    entrydatieedit.setText(transactionentryModel.entrydatieedit);
                    maturitydateedit.setText(transactionentryModel.maturitydateedit);
                    amountedit.setText(transactionentryModel.amountedit);
                    ROI.setText(transactionentryModel.roi);
                    Intrestedit.setText(transactionentryModel.intrestedit);
                    TDS.setText(transactionentryModel.tds);
                    if (transactionentryModel.spinnermode.equals("Investor")){
                        spinnermode.setSelection(0);
                    }else spinnermode.setSelection(1);
                    chequenoedit.setText(transactionentryModel.chequenoedit);
                    chequedateedit.setText(transactionentryModel.chequedateedit);
                    Bank.setText(transactionentryModel.bank);
                    Branch.setText(transactionentryModel.branch);
                    Brokerage.setText(transactionentryModel.brokerage);
                    TDSBrokerage.setText(transactionentryModel.tdsbrokerage);
                    Remarks.setText(transactionentryModel.remarks);
                    Log.d("iuiu1","ijf"+transactionentryModel.status.equals("1"));
                    String thi=transactionentryModel.hidden;
                    Log.d("thiio",thi);
                    if (thi.equals("1")){
                        Log.d("iuiu1","ijf"+transactionentryModel.hidden.equals("1"));
                        toggleButton.setChecked(true);


                    }
                    if (thi.equals("0")){
                        Log.d("iuiu2","ijf"+transactionentryModel.hidden);

                        toggleButton.setChecked(false);
                    }

                }


                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pushkey=FirebaseDatabase.getInstance().getReference("PreMaturitydates").child(Tids).push().getKey();
                    moveFirebaseRecord(new Firebase("https://amesh-e678e.firebaseio.com/Transaction Enteries/"+Tids),new Firebase("https://amesh-e678e.firebaseio.com/PreMaturitydates/"+Tids+"/"+pushkey));
                    if (toggleButton.isChecked()){
                        TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"1",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                        TransactionEntries.child(Tids).setValue(transactionentryModel);



                    }else {
                        TransactionentryModel transactionentryModel=new TransactionentryModel(investoredit.getText().toString(),recieveredit.getText().toString(),entrydatieedit.getText().toString(),maturitydateedit.getText().toString(),chequedateedit.getText().toString(),amountedit.getText().toString(),ROI.getText().toString(),Intrestedit.getText().toString(),TDS.getText().toString(),chequenoedit.getText().toString(),Bank.getText().toString(),Branch.getText().toString(),Brokerage.getText().toString(),TDSBrokerage.getText().toString(),Remarks.getText().toString(),spinnermode.getSelectedItem().toString(),"Active",investoruserid.get(investori.indexOf(investoredit.getText().toString())),recieveruserid.get(recieveri.indexOf(recieveredit.getText().toString())),"0",investorgroupid.get(investori.indexOf(investoredit.getText().toString())),recievergroupid.get(recieveri.indexOf(recieveredit.getText().toString())));
                        TransactionEntries.child(Tids).setValue(transactionentryModel);

                    }
                    Intent intent=new Intent(TransactionEntryNew.this,AdminPanel.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }



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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        currentDateString= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        switch(idOfClickedItem)
        {
            case "2131230743":
                Log.d("curri",currentDateString);
                entrydatieedit.setText(currentDateString);
                break;
            case "2131230749":
                maturitydateedit.setText(currentDateString);
                break;
            case "2131230739":
                chequedateedit.setText(currentDateString);
                break;
            default:
                System.out.println("no match");
        }

    }
    public void newdafor() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                TransactionEntryNew.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public static void moveFirebaseRecord(Firebase fromPath, final Firebase toPath)
    {
        fromPath.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new Firebase.CompletionListener()
                {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase)
                    {
                        if (firebaseError != null)
                        {
                            System.out.println("Copy failed");
                        }
                        else
                        {
                            System.out.println("Success");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Copy failed");
            }
        });
    }
//    class ourdataloadupdate extends AsyncTask<Void,Integer,Integer>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setMax(100);
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            progressBar.setProgress(values[0]);
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            for (int i=0;i<100;i++){
//                publishProgress(i);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//        }
//    }
    public boolean validateinvestor(){
        if (TextUtils.isEmpty(investoredit.getText().toString())){
            investortextinputlayout.setError("Investor field is empty");
            return false;
        }else {
            investortextinputlayout.setError(null);
            return true;

        }
    }
    public boolean validatereciever(){
        if (TextUtils.isEmpty(recieveredit.getText().toString())){
            recievertextinputlayout.setError("Investor field is empty");
            return false;
        }else {
            recievertextinputlayout.setError(null);
            return true;

        }
    }
    public boolean validateentrydate(){
        if (TextUtils.isEmpty(entrydatieedit.getText().toString())){
            recievertextinputlayout.setError("Investor field is empty");
            return false;
        }else {
            recievertextinputlayout.setError(null);
            return true;

        }
    }
    private void setAdapter(final String searchedString) {
        Query query=FirebaseDatabase.getInstance().getReference("Uspd").orderByKey().startAt(searchedString);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Log.d("ofue",""+dataSnapshot1.getKey());

                }

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
        Intent intent=new Intent(TransactionEntryNew.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return true;
    }

    @Override
    public void respond(String s, String y, String z, String a) {
        if (a.equals("investortransaction")){
            investoredit.setText(z);
            Log.d("uuuuuuuuuu","jid"+a);
            readdata(FirebaseDatabase.getInstance().getReference("Profiles").child(s), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {

                    masterClassModel masterClassModel= dataSnapshot.getValue(masterClassModel.class);                    Log.d("jdjid","di"+masterClassModel.toString());
                    if (masterClassModel.Bankname!=null){
                        bankname=dataSnapshot.child("bankname").getValue().toString();
                        Bank.setText(bankname);
                        Log.d("iiiiiiiiii","kid"+bankname);
                    }
                    if (masterClassModel.Bnakbranch!=null){
                        bankbranch=dataSnapshot.child("bnakbranch").getValue().toString();
                        Branch.setText(bankbranch);
                    }
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }
            });
        }
        if (a.equals("recievertransaction")){
            recieveredit.setText(z);
            Log.d("uuuuuuuuuu","jid"+a);
            readdata(FirebaseDatabase.getInstance().getReference("Profiles").child(s), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("bankname").getValue()!=null){
                        bankname=dataSnapshot.child("bankname").getValue().toString();
                        Bank.setText(bankname);
                        Log.d("iiiiiiiiii","kid"+bankname);
                    }
                    if (dataSnapshot.child("bnakbranch").getValue()!=null){
                        bankbranch=dataSnapshot.child("bnakbranch").getValue().toString();
                        Branch.setText(bankbranch);
                    }
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }
            });
        }
    }
}
