package com.example.vyanktesh.amesh;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {
    public static String getMonthName(int month) {
        String monthString;
        switch (month) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
    }

    public static String getDayName(int day) {
        String dayString;
        switch (day) {
            case 0:
                dayString = "Monday";
                break;
            case 1:
                dayString = "Tuesday";
                break;
            case 2:
                dayString = "Wednesday";
                break;
            case 3:
                dayString = "Thursday";
                break;
            case 4:
                dayString = "Friday";
                break;
            case 5:
                dayString = "Saturday";
                break;
            case 6:
                dayString = "Sunday";
                break;
            default:
                dayString = "Invalid day";
                break;
        }
        return dayString;
    }

    public static int getDayNo(String day) {
        if (day.equalsIgnoreCase("Monday")) {
            return 0;
        } else if (day.equalsIgnoreCase("Tuesday")) {
            return 1;
        } else if (day.equalsIgnoreCase("Wednesday")) {
            return 2;
        } else if (day.equalsIgnoreCase("Thursday")) {
            return 3;
        } else if (day.equalsIgnoreCase("Friday")) {
            return 4;
        } else if (day.equalsIgnoreCase("Saturday")) {
            return 5;
        } else if (day.equalsIgnoreCase("Sunday")) {
            return 6;
        }
        return 7;
    }

//    public static int getMonthNumber(String monthName) {
//        if (monthName.equalsIgnoreCase("January")) {
//            return 1;
//        } else if (monthName.equalsIgnoreCase("February")) {
//            return 2;
//        } else if (monthName.equalsIgnoreCase("March")) {
//            return 3;
//        } else if (monthName.equalsIgnoreCase("April")) {
//            return 4;
//        } else if (monthName.equalsIgnoreCase("May")) {
//            return 5;
//        } else if (monthName.equalsIgnoreCase("June")) {
//            return 6;
//        } else if (monthName.equalsIgnoreCase("July")) {
//            return 7;
//        }else if (monthName.equalsIgnoreCase("August")) {
//            return 8;
//        } else if (monthName.equalsIgnoreCase("September")) {
//            return 9;
//        } else if (monthName.equalsIgnoreCase("October")) {
//            return 10;
//        } else if (monthName.equalsIgnoreCase("November")) {
//            return 11;
//        } else if (monthName.equalsIgnoreCase("December")) {
//            return 12;
//        }
//        else{
//            return 0;
//        }
//    }

    String formattedDate="";
    List<Groupadminmessangingmodel> groupadminmessangingmodels=new ArrayList<>();
    List<String> Tids=new ArrayList<>();
    Toolbar toolo;
    String q="";
    EditText messagecontent,mobilenumber;
    Button sendmessage,dailymessages;
    List<DataSnapshot> maturitydatesdatasnapshot=new ArrayList<>();
    List<String> mobilenumberslist=new ArrayList<>();
    List<String> transactionlist=new ArrayList<>();
    FloatingActionButton faab,messagefaab;
    RecyclerView recyclerView;
    ProgressBar proig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        messagecontent=findViewById(R.id.messgecontent);
//        sendmessage=findViewById(R.id.sendmessage);
//        mobilenumber=findViewById(R.id.mobilenumber);
//        dailymessages=findViewById(R.id.dailymessages);
//        faab=findViewById(R.id.faab);
        messagefaab=findViewById(R.id.messagefaab);
        toolo=findViewById(R.id.toolb);
        recyclerView=findViewById(R.id.recyi);

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

//        faab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Readmessagedialog readmessagedialog=new Readmessagedialog();
//                Bundle bundle=new Bundle();
//                bundle.putString("move","adminmessagecheck");
//                readmessagedialog.setArguments(bundle);
//                readmessagedialog.show(getSupportFragmentManager(),"dif");
//
//            }
//        });

        readdata(FirebaseDatabase.getInstance().getReference("Messages").child("Admin Messages").child("read0"), new OnGetDataListner() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    groupadminmessangingmodels.add(dataSnapshot1.getValue(Groupadminmessangingmodel.class));
                    Tids.add(dataSnapshot1.getKey());
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(Message.this));
                recyclerView.setAdapter(new Messageadapter(groupadminmessangingmodels,Tids));

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
                bundle.putString("move","fromadminpanel");
                messagedialog.setArguments(bundle);
                messagedialog.show(getSupportFragmentManager(),"uh");
            }
        });
//        sendmessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(messagecontent.getText().toString())){
//
//                }
//            }
//        });
//        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

//        dailymessages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Date c = Calendar.getInstance().getTime();
//                SimpleDateFormat df = new SimpleDateFormat(", MMMMMMMMM dd, yyyy");
//                String r = df.format(c);
//                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//                Date e = new Date();
//                String t = sdf.format(e);   //Tuesday, November 5, 1998
//                r=t+r;
//                String s = r;  //string s = aaj ki date dal dena
//                String p="";
//                String[] str = s.split(", ");
//                String[] str1 = str[1].split(" ");
//                int d = Integer.parseInt(str1[1]);
//                int m = Integer.parseInt(str1[0]);
//                int y = Integer.parseInt(str[2]);
//                int dno = getDayNo(str[0]);
//                int[] arr = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//                if (y % 4 == 0) {
//                    if (y % 400 == 0) {
//                        arr[1]++;
//                    } else if (y % 100 == 0) {
//                    } else {
//                        arr[1]++;
//                    }
//                }
//                int k = 2;
//                while (k > 0) {
//                    dno = (dno + 1) % 7;
//                    k--;
//                    if (d < arr[m - 1]) {
//                        d = d + 1;
//                    } else {
//                        if (m != 12) {
//                            d = 1;
//                            m++;
//                        } else {
//                            d = 1;
//                            m = 1;
//                            y++;
//                        }
//                    }
//                    q = String.valueOf(d) +  "-" + String.valueOf(m) + "-" + String.valueOf(y);  //2 din bad ki date keval inhi logo ko sms karein
//                }
//
//                Log.d("dfiu",q);
//                readdata(FirebaseDatabase.getInstance().getReference("Maturity Dates"), new OnGetDataListner() {
//                    @Override
//                    public void onSuccess(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.hasChild(q)){
//                            for (DataSnapshot dataSnapshot1:dataSnapshot.child(q).getChildren()){
//                                transactionlist.add(dataSnapshot1.getValue(String.class));
//                            }
//                            for (int i=0;i<transactionlist.size();i++){
//                                readdata(FirebaseDatabase.getInstance().getReference("Transaction Enteries").child(transactionlist.get(i)), new OnGetDataListner() {
//                                    @Override
//                                    public void onSuccess(DataSnapshot dataSnapshot) {
//                                        if (dataSnapshot.child("status").getValue(String.class).equalsIgnoreCase("active")){
//                                            try {
//                                                // Construct data
//                                                String apiKey = "apikey=" + "3ReMOsLqtYs-oqynMhmFXMOlUxDjIOA3ubs0MBsh42";
//                                                String message = "&message=" + "Hello,\n This is to bring to your concern that your transaction with transaction id"+dataSnapshot.getKey()+"and of amount"+dataSnapshot.child("amountedit").getValue(String.class)+"is due with party name"+dataSnapshot.child("investoredit").getValue(String.class)+" and its maturity date is"+q;
//                                                String sender = "&sender=" + "TXTLCL";
//                                                String numbers = "&numbers=" + dataSnapshot.child("recieveruid").getValue(String.class);
//
//                                                // Send data
//                                                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
//                                                String data = apiKey + numbers + message + sender;
//                                                conn.setDoOutput(true);
//                                                conn.setRequestMethod("POST");
//                                                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//                                                conn.getOutputStream().write(data.getBytes("UTF-8"));
//                                                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                                                final StringBuffer stringBuffer = new StringBuffer();
//                                                String line;
//                                                while ((line = rd.readLine()) != null) {
//                                                    stringBuffer.append(line);
//                                                    Toast.makeText(Message.this,"The message is null"+line,Toast.LENGTH_LONG).show();
//                                                }
//                                                rd.close();
//
////                    return stringBuffer.toString();
//                                            } catch (Exception e) {
////                    System.out.println("Error SMS "+e);
//                                                Toast.makeText(Message.this,"the message is"+e,Toast.LENGTH_LONG).show();
////                    return "Error "+e;
//                                            }
//                                            mobilenumberslist.add(dataSnapshot.child("recieveruid").getValue(String.class));
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
//                        }else {
//                            Toast.makeText(Message.this,"There are no maturity dates",Toast.LENGTH_LONG).show();
//                        }
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
        FirebaseDatabase.getInstance().getReference("Uspd").child("7999325318").child("lastlogin").setValue(formattedDate);

        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(Message.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return true;
    }

    }

