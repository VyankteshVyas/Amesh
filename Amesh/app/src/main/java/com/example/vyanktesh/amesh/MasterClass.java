package com.example.vyanktesh.amesh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.authentication.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Random;

public class MasterClass extends Fragment implements Filename {


    DatabaseReference profiledatabaseReference, investordatabaseReference, recieverdatabaseReference, Uspdo;
    TextView Editgorup;
    ImageButton logout;
    TextView lastilogin,automaticPassword;
    TextView submit;
    DatabaseReference databaseReference;
    //    RadioGroup radioGroup;
//    LinearLayout selectgrouop;
    //    RadioButton selectgroupradiobutton,newgrouop;

    RelativeLayout newuserrelativelayout;
    DatabaseReference groups;
    boolean booli=false;
    String selectedItem = "";
    String selectedgroupmobilenumber = "";
    TextView groupnamei;
    EditText partyName, address, mobilenumber, password, PanNo, Bankname, Bnakbranch, accountNumber, Accountname, IFSCcode, Entrydateedit,Remarksedit;
    Spinner typespinner;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            booli=true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.masterclass, container, false);
        view.getRootView().setClickable(true);
        view.getRootView().setFocusable(true);
        partyName=view.findViewById(R.id.Partynameedit);
        Entrydateedit = view.findViewById(R.id.Entrydateedit);
        Remarksedit=view.findViewById(R.id.Remarksedit);
        address = view.findViewById(R.id.Addressedit);
        mobilenumber = view.findViewById(R.id.Mobileedit);
        PanNo = view.findViewById(R.id.Pannoedit);
        Bankname = view.findViewById(R.id.Banknameedit);
        Bnakbranch = view.findViewById(R.id.BankBranchedit);
        accountNumber = view.findViewById(R.id.AccountNoedit);
        Accountname = view.findViewById(R.id.Accountnameedit);
        lastilogin=view.findViewById(R.id.lastlogin);
        IFSCcode = view.findViewById(R.id.IFSCcodeedit);
        typespinner = view.findViewById(R.id.Typespinner);
        submit = view.findViewById(R.id.Submit);
        Editgorup=view.findViewById(R.id.addoredit);
        automaticPassword=view.findViewById(R.id.automaticPassword);
        logout=view.findViewById(R.id.logout);

        Editgorup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group group=new group();
                Bundle bundle=new Bundle();
                bundle.putString("transactiontogroup","editgroup");
                group.setArguments(bundle);
                group.show(getFragmentManager(),"ohk");
//                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                        transaction.add(R.id.newuserrelativelayout, group, "newgroupfragment");
//                        transaction.addToBackStack("group to newgroup");
//                        transaction.commit();
            }
        });


        final String move=getArguments().getString("move");
        if (move.equals("admintonewgroup")){
            Log.d("move","admintonewgroup");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mobilenumber.getText()) && !TextUtils.isEmpty(partyName.getText())) {
                        masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),partyName.getText().toString(),Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Not group Admin",mobilenumber.getText().toString(),Remarksedit.getText().toString());
                        profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (getActivity()!=null){
                                    Toast.makeText(getActivity(),"Profile of new gorup created",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (getActivity()!=null){
                                    Toast.makeText(getActivity(),"Unable to Create new group",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        if (typespinner.getSelectedItem().toString().equals("Investor")){
                            InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
                            investordatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode);

                        }
                        if (typespinner.getSelectedItem().toString().equals("Reciever")){
                            RecieverModel recieverModel=new RecieverModel(mobilenumber.getText().toString(),partyName.getText().toString());
                            recieverdatabaseReference.child(mobilenumber.getText().toString()).setValue(recieverModel);

                        }


                        Random rand = new Random();
                        int otp;
                        String otpCheck = "";
                        otp = 100000 + rand.nextInt(900000);
                        otpCheck = Integer.toString(otp);
                        Uspd uspd=new Uspd(otpCheck,"First Login",mobilenumber.getText().toString());
                        final String finalOtpCheck = otpCheck;
                        Uspdo.child(mobilenumber.getText().toString()).setValue(uspd).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (getActivity()!=null){
                                    Toast.makeText(getActivity(),"Password generated successfully",Toast.LENGTH_SHORT).show();
                                    Dialogboxforsearchenteries dialogboxforsearchenteries=new Dialogboxforsearchenteries();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("dialogtitle","Password");
                                    bundle.putString("editablehint", finalOtpCheck);
                                    dialogboxforsearchenteries.setArguments(bundle);
                                    dialogboxforsearchenteries.show(getFragmentManager(),"idjf");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                if (getActivity()!=null){
                                    Toast.makeText(getActivity(),"Some error occured",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        FirebaseDatabase.getInstance().getReference("groups").child(mobilenumber.getText().toString()).setValue(new NewgroupModel(partyName.getText().toString(),mobilenumber.getText().toString(),typespinner.getSelectedItem().toString()));
                    }

                }
            });

        }
        if (move.equals("editexistingmemberofgroup")){
            Editgorup.setText("Inactive user");


            Log.d("move","editexistingmemberofgroup");
            final String userid=getArguments().getString("userid");
            Log.d("userid",userid);
            final String[] finalType = new String[1];
            final String[] mobi = new String[1];
            final String groupid=getArguments().getString("groupid");
            Log.d("groupid",groupid);
            automaticPassword.setVisibility(View.VISIBLE);


                readdata(FirebaseDatabase.getInstance().getReference("Uspd").child(userid), new OnGetDataListner() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        automaticPassword.setText(dataSnapshot.child("password").getValue().toString());
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            readdata(FirebaseDatabase.getInstance().getReference("Profiles").child(userid), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    masterClassModel masterClassModel= dataSnapshot.getValue(masterClassModel.class);
//                    Log.d("Profilestatus",masterClassModel.partyName);
//                    Toast.makeText(getActivity(),""+masterClassModel.partyName,Toast.LENGTH_LONG).show();
                    partyName.setText(masterClassModel.partyName);
                    address.setText(masterClassModel.address);
                    mobilenumber.setText(masterClassModel.mobilenumber);
                   mobi[0] =masterClassModel.mobilenumber;
                    PanNo.setText(masterClassModel.PanNo);
                    Bankname.setText(masterClassModel.Bankname);
                    Bnakbranch.setText(masterClassModel.Bnakbranch);
                    accountNumber.setText(masterClassModel.accountNumber);
                    IFSCcode.setText(masterClassModel.IFSCcode);
                    Accountname.setText(masterClassModel.Accountname);
                    Remarksedit.setText(masterClassModel.Remarks);
                    String type="";
                    if (masterClassModel.typespinner.equals("Investor")){
                        typespinner.setSelection(0);
                        type="Investor";
                    }else {
                        typespinner.setSelection(1);
                        type="Reciever";
                    }

                    Entrydateedit.setText(masterClassModel.Entrydateedit);
                    submit.setText("Update");
                    finalType[0] = type;

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
                    submit.setEnabled(false);
                    Log.d("valis1","fdfe");
                    Log.d("typespinnerslecteditem",""+typespinner.getSelectedItem());
                    if (finalType[0].equals("Investor")){

                        Log.d("valis2","fdfe");

                        investordatabaseReference.child(mobi[0]).setValue(null);
                        profiledatabaseReference.child(mobi[0]).setValue(null);
                        if (typespinner.getSelectedItemPosition()==0){
                            Log.d("valis3","dfd");
                            InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
                            if (userid.equals(groupid)){
                                Log.d("ieif","jdifd"+mobi[0]);
                                FirebaseDatabase.getInstance().getReference("groups").child(mobi[0]).setValue(null);
                                FirebaseDatabase.getInstance().getReference("groups").child(mobilenumber.getText().toString()).setValue(new NewgroupModel(partyName.getText().toString(),mobilenumber.getText().toString(),typespinner.getSelectedItem().toString()));
                                final String[] previeouspassword = new String[1];
                                final String[] previouslogin = new String[1];
                                readdata(Uspdo.child(mobi[0]), new OnGetDataListner() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        previeouspassword[0] =dataSnapshot.child("password").getValue(String.class);
                                        previouslogin[0] =dataSnapshot.child("lastlogin").getValue(String.class);
                                        Uspdo.child(mobi[0]).setValue(null);
                                        Uspd uspd1=new Uspd(previeouspassword[0],previouslogin[0],mobilenumber.getText().toString());
                                        Uspdo.child(mobilenumber.getText().toString()).setValue(uspd1);
                                    }

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });



                            }
                            investordatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),groupid,Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Not group Admin",mobilenumber.getText().toString(),Remarksedit.getText().toString());
                            profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
                            if (userid.equals(groupid)){
                                FirebaseDatabase.getInstance().getReference("groups").child(mobi[0]).setValue(null);
                                FirebaseDatabase.getInstance().getReference("groups").child(mobilenumber.getText().toString()).setValue(new NewgroupModel(partyName.getText().toString(),mobilenumber.getText().toString(),typespinner.getSelectedItem().toString()));
                                final String[] previeouspassword = new String[1];
                                final String[] previouslogin = new String[1];
                                readdata(Uspdo.child(mobi[0]), new OnGetDataListner() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        previeouspassword[0] =dataSnapshot.child("password").getValue(String.class);
                                        previouslogin[0] =dataSnapshot.child("lastlogin").getValue(String.class);
                                        Uspdo.child(mobi[0]).setValue(null);
                                        Uspd uspd1=new Uspd(previeouspassword[0],previouslogin[0],mobilenumber.getText().toString());
                                        Uspdo.child(mobilenumber.getText().toString()).setValue(uspd1);
                                    }

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });



                            }
                            recieverdatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),groupid,Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Not group Admin",mobilenumber.getText().toString(),Remarksedit.getText().toString());
                            profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }else {
                        Log.d("tyepspinnercase","elos wala chala");
                        recieverdatabaseReference.child(mobi[0]).setValue(null);
                        profiledatabaseReference.child(mobi[0]).setValue(null);
                        if (typespinner.getSelectedItemPosition()==0){

                            InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
                            if (userid.equals(groupid)){
                                FirebaseDatabase.getInstance().getReference("groups").child(mobi[0]).setValue(null);
                                FirebaseDatabase.getInstance().getReference("groups").child(mobilenumber.getText().toString()).setValue(new NewgroupModel(partyName.getText().toString(),mobilenumber.getText().toString(),typespinner.getSelectedItem().toString()));
                                final String[] previeouspassword = new String[1];
                                final String[] previouslogin = new String[1];
                                readdata(Uspdo.child(mobi[0]), new OnGetDataListner() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        previeouspassword[0] =dataSnapshot.child("password").getValue(String.class);
                                        previouslogin[0] =dataSnapshot.child("lastlogin").getValue(String.class);
                                        Uspdo.child(mobi[0]).setValue(null);
                                        Uspd uspd1=new Uspd(previeouspassword[0],previouslogin[0],mobilenumber.getText().toString());
                                        Uspdo.child(mobilenumber.getText().toString()).setValue(uspd1);
                                    }

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });



                            }
                            investordatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),groupid,Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Not group Admin",mobilenumber.getText().toString(),Remarksedit.getText().toString());
                            profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
                            if (userid.equals(groupid)){
                                FirebaseDatabase.getInstance().getReference("groups").child(mobi[0]).setValue(null);
                                FirebaseDatabase.getInstance().getReference("groups").child(mobilenumber.getText().toString()).setValue(new NewgroupModel(partyName.getText().toString(),mobilenumber.getText().toString(),typespinner.getSelectedItem().toString()));
                                final String[] previeouspassword = new String[1];
                                final String[] previouslogin = new String[1];
                                readdata(Uspdo.child(mobi[0]), new OnGetDataListner() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        previeouspassword[0] =dataSnapshot.child("password").getValue(String.class);
                                        previouslogin[0] =dataSnapshot.child("lastlogin").getValue(String.class);
                                        Uspdo.child(mobi[0]).setValue(null);
                                        Uspd uspd1=new Uspd(previeouspassword[0],previouslogin[0],mobilenumber.getText().toString());
                                        Uspdo.child(mobilenumber.getText().toString()).setValue(uspd1);
                                    }

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });



                            }
                            recieverdatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),groupid,Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Not group Admin",mobilenumber.getText().toString(),Remarksedit.getText().toString());
                            profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Unable to update Profile",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                    FragmentManager manager = getFragmentManager();

                    FragmentTransaction transaction = manager.beginTransaction();
                    manager.popBackStack("editexistingmemberofgroup", manager.POP_BACK_STACK_INCLUSIVE);
                    transaction.commit();
                }
            });

        }
        if (move.equals("addnewmembertogroup")){
            Log.d("move","addnewmembertogroup");
            final String groupnos=getArguments().getString("groupno");
            String groupname=getArguments().getString("groupname");
            Log.d("groupnos",groupnos);
            submit.setText("Add");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit.setEnabled(false);
                    if (!TextUtils.isEmpty(mobilenumber.getText()) && !TextUtils.isEmpty(partyName.getText())) {
                        masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),groupnos,Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Not group Admin",groupnos,Remarksedit.getText().toString());
                        profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (getActivity()!=null){
                                    Toast.makeText(getActivity(),"Added member to group",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (getActivity()!=null){
                                    Toast.makeText(getActivity(),"Unable to add member to group",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        if (typespinner.getSelectedItem().toString().equals("Investor")){
                            InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
                            investordatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Member added to group successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Error occured while adding member to group",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        if (typespinner.getSelectedItem().toString().equals("Reciever")){
                            RecieverModel recieverModel=new RecieverModel(mobilenumber.getText().toString(),partyName.getText().toString());
                            recieverdatabaseReference.child(mobilenumber.getText().toString()).setValue(recieverModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Member added to group successfully",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (getActivity()!=null){
                                        Toast.makeText(getActivity(),"Error occured while adding member to group",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                    FragmentManager manager = getFragmentManager();

                                FragmentTransaction transaction = manager.beginTransaction();
                                manager.popBackStack("admintomasterclass", manager.POP_BACK_STACK_INCLUSIVE);
                                transaction.commit();
                }
            });



        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        groups = FirebaseDatabase.getInstance().getReference("groups");

//        radioGroup=getActivity().findViewById(R.id.radiogrouop);
//        selectgrouop = getActivity().findViewById(R.id.selectgrouop);
        newuserrelativelayout = getActivity().findViewById(R.id.newuserrelativelayout);

//        selectgroupradiobutton=getActivity().findViewById(R.id.selectgroupradiobutton);
        databaseReference = FirebaseDatabase.getInstance().getReference("groups");

        final String[] requiredradio = new String[1];


        profiledatabaseReference = FirebaseDatabase.getInstance().getReference("Profiles");
        investordatabaseReference = FirebaseDatabase.getInstance().getReference("Investors");
        recieverdatabaseReference = FirebaseDatabase.getInstance().getReference("Recievers");
        Uspdo = FirebaseDatabase.getInstance().getReference("Uspd");
        Log.d("onActivityCreated", "ahagain");



        final FragmentManager manager = getFragmentManager();


        final List<String> grouplist = new ArrayList<>();
        final List<String> groupno = new ArrayList<>();
        final List<String> partypelist = new ArrayList<>();

        String fromgrouptomasterclasss = "1234";



//        Log.d("ohk", "onActivityCreated: before read data");
//            readdata(groups, new OnGetDataListner() {
//                @Override
//                public void onSuccess(DataSnapshot dataSnapshot) {
//                    Log.d("ohk", "onActivityCreated: after read data");
//                    grouplist.clear();
//                    groupno.clear();
//                    partypelist.clear();
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        Log.d("hello", dataSnapshot1.getValue().toString());
//                        NewgroupModel nm1 = dataSnapshot1.getValue(NewgroupModel.class);
//
//                        grouplist.add(nm1.groupname);
//                        groupno.add(nm1.groupadminmobileNumber);
//                        Log.d("parttype", nm1.PartyType + "   " + partypelist.size());
//                        partypelist.add(nm1.PartyType);
//
//
//                    }
//                    Log.d("pasize", "" + partypelist.size());
//                    Log.d("stringsize in groupJK:", "" + grouplist.size());
//                    if(getActivity()!=null){
//                        ArrayAdapter<String> arroAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, grouplist);
//
//                    }
//                    Log.d("grouplit",""+grouplist.size());
//
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
//        if ((checkboxedit.isChecked())){
//            newuserrelativelayout.setVisibility(View.INVISIBLE);
//        }










//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                String radioid =Integer.toString(radioGroup.getCheckedRadioButtonId());
//                Log.d("entered","radiogroup");
//                if (radioid.equals(Integer.toString(getActivity().findViewById(R.id.selectgroupradiobutton).getId()))){
//                    //For selecting a group
//                    Log.d("fdfe88",radioid);
//                    selectgrouop.setVisibility(View.VISIBLE);
//                    submit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(mobilenumber.getText())&&!TextUtils.isEmpty(partyName.getText())){
//                                masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),groupno.get(grouplist.indexOf(sponi.getSelectedItem().toString())),Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Not group Admin");
//                                profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel);
//
//                                if (typespinner.getSelectedItem().toString().equals("Investor")){
//                                    InvestorMode investorMode=new InvestorMode(groupno.get(grouplist.indexOf(sponi.getSelectedItem().toString())),partyName.getText().toString());
//                                    investordatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode);
//
//                                }
//                                if (typespinner.getSelectedItem().toString().equals("Reciever")){
//                                    RecieverModel recieverModel=new RecieverModel(groupno.get(grouplist.indexOf(sponi.getSelectedItem().toString())),partyName.getText().toString());
//                                    recieverdatabaseReference.child(mobilenumber.getText().toString()).setValue(recieverModel);
//
//                                }
//
//                                FragmentManager manager = getFragmentManager();
//                                FragmentTransaction transaction = manager.beginTransaction();
//                                manager.popBackStack("admintogroup", manager.POP_BACK_STACK_INCLUSIVE);
//                                transaction.commit();
//                            }else {
//                                Toast.makeText(getActivity(),"Please Enter Mobile Number and party name",Toast.LENGTH_SHORT).show();}
//                        }
//                    });
//
//                }
////                if (radioid.equals(Integer.toString(getActivity().findViewById(R.id.singlemembergrou).getId()))){
////                    //For single member group
////                    Log.d("fdfe88",radioid);
////                    selectgrouop.setVisibility(View.INVISIBLE);
////                    submit.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            if (!TextUtils.isEmpty(mobilenumber.getText())&&!TextUtils.isEmpty(partyName.getText())){
////                                masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),mobilenumber.getText().toString(),Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Group Admin");
////                                profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel);
////
////                                if (typespinner.getSelectedItem().toString().equals("Investor")){
////                                    InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
////                                    investordatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode);
////
////                                }
////                                if (typespinner.getSelectedItem().toString().equals("Reciever")){
////                                    RecieverModel recieverModel=new RecieverModel(mobilenumber.getText().toString(),partyName.getText().toString());
////                                    recieverdatabaseReference.child(mobilenumber.getText().toString()).setValue(recieverModel);
////
////                                }
////
////                                FragmentManager manager = getFragmentManager();
////                                FragmentTransaction transaction = manager.beginTransaction();
////                                manager.popBackStack("admintogroup", manager.POP_BACK_STACK_INCLUSIVE);
////                                transaction.commit();
////                            }else {
////                                Toast.makeText(getActivity(),"Please Enter Mobile Number and party name",Toast.LENGTH_SHORT).show();}
////                        }
////                    });
////                }
//                if (radioid.equals(Integer.toString(getActivity().findViewById(R.id.newgru).getId()))){
//                    Log.d("fdfe88",radioid);
//                    selectgrouop.setVisibility(View.INVISIBLE);
//
//
//
//                    submit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!TextUtils.isEmpty(mobilenumber.getText())&&!TextUtils.isEmpty(partyName.getText())){
//                                masterClassModel masterClassModel=new masterClassModel(partyName.getText().toString(),address.getText().toString(), mobilenumber.getText().toString(), PanNo.getText().toString(), Bankname.getText().toString(), Bnakbranch.getText().toString(), accountNumber.getText().toString(), Accountname.getText().toString(), IFSCcode.getText().toString(),mobilenumber.getText().toString(),Entrydateedit.getText().toString(),typespinner.getSelectedItem().toString(),"Group Admin");
//                                profiledatabaseReference.child(mobilenumber.getText().toString()).setValue(masterClassModel);
//                                NewgroupModel newgroupModel = new NewgroupModel(partyName.getText().toString(), mobilenumber.getText().toString(),typespinner.getSelectedItem().toString());
//                                databaseReference.child(mobilenumber.getText().toString()).setValue(newgroupModel);
//
//                                if (typespinner.getSelectedItem().toString().equals("Investor")){
//                                    InvestorMode investorMode=new InvestorMode(mobilenumber.getText().toString(),partyName.getText().toString());
//                                    investordatabaseReference.child(mobilenumber.getText().toString()).setValue(investorMode);
//
//                                }
//                                if (typespinner.getSelectedItem().toString().equals("Reciever")){
//                                    RecieverModel recieverModel=new RecieverModel(mobilenumber.getText().toString(),partyName.getText().toString());
//                                    recieverdatabaseReference.child(mobilenumber.getText().toString()).setValue(recieverModel);
//
//                                }
//
//                                FragmentManager manager = getFragmentManager();
//                                FragmentTransaction transaction = manager.beginTransaction();
//                                manager.popBackStack("admintogroup", manager.POP_BACK_STACK_INCLUSIVE);
//                                transaction.commit();
//                            }else {
//                                Toast.makeText(getActivity(),"Please Enter Mobile Number and party name",Toast.LENGTH_SHORT).show();}
//                        }
//                    });
//
//                }
//
//                requiredradio[0] =Integer.toString(selectgroupradiobutton.getId());
//                Log.d("ohmod",""+ radioid +"  "+ requiredradio[0]);
//
//            }
//        });}





//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("group name0 aaya kya",""+groupname);

//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d("Starti","chala"+groupname);
//
//    }

//    public void passgroupname(String groupname) {
////        this.groupname=groupname;
////        groupnamei.setText("oh god");
//            Log.d("value aai kya",groupname);
//    }

        String formattedDate="";
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
                lastilogin.setText(("Last Login :"+ lastlogin[0]));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
        final String finalFormattedDate = formattedDate;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Uspd").child("7999325318").child("lastlogin").setValue(finalFormattedDate);
                FirebaseAuth.getInstance().signOut();
//                FragmentManager manager = getFragmentManager();
//
//                FragmentTransaction transaction = manager.beginTransaction();
//                manager.popBackStack("admintomasterclass", manager.POP_BACK_STACK_INCLUSIVE);
//                transaction.commit();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
    public void readdata ( final DatabaseReference reference, final OnGetDataListner listner){
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
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
        submit.setText("Add");

    }

    @Override
    public void respond(String s, String y, String z, String a) {

    }
}

