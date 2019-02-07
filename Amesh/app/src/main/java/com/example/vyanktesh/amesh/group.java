package com.example.vyanktesh.amesh;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class group extends DialogFragment{


Filename filename;





    public group newInstance(String message){
        group group=new group();
        return group;
    }

    ListView listView;
//    Button createNewGroup;
    List<String> grouplist=new ArrayList<>();
    List<String> groupno=new ArrayList<>();
    List<String> partypelist=new ArrayList<>();
    List<String> groupmembers=new ArrayList<>();
    List<String> groupmembersid=new ArrayList<>();
    List<String> partysearchname=new ArrayList<>();
    List<String> partysearchgroupid=new ArrayList<>();
    List<String> partysearchid=new ArrayList<>();
    DatabaseReference groups;
    FloatingActionButton faab;
    String groupmobileno="pjijih";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.group,container,false);
//        filename= (Filename) context;
        listView=view.findViewById(R.id.listi);
        faab=view.findViewById(R.id.faab);
        groups= FirebaseDatabase.getInstance().getReference("groups");
        String string=getArguments().getString("transactiontogroup");
        Log.d("ihiefi",string);
        if (string.equals("editgroup")){
            readdata(FirebaseDatabase.getInstance().getReference("groups") ,new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    grouplist.clear();
                    groupno.clear();
                    partypelist.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                        grouplist.add(dataSnapshot1.child("groupname").getValue(String.class));
                        groupno.add(dataSnapshot1.getKey());
                    }
                    Log.d("pasize",""+partypelist.size());
                    Log.d("stringsize in groupJK:",""+getActivity().toString());
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,grouplist);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            Query query=FirebaseDatabase.getInstance().getReference("Profiles").orderByChild("groupid").equalTo(groupno.get(position));
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                        groupmembersid.add(dataSnapshot1.getKey());
                                        groupmembers.add(dataSnapshot1.child("partyName").getValue(String.class));

                                        Log.d("datashnapi",dataSnapshot.toString());
                                    }
                                    if (getActivity()!=null){
                                        ArrayAdapter<String> arrauAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,groupmembers);
                                        listView.setAdapter(arrauAdapter);
                                    }

                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int positio, long id) {
//                                            filename.respond(groupno.get(position),groupmembersid.get(positio),grouplist.get(position),"editexistingmemberofgroup");

                                            MasterClass masterClass=new MasterClass();
                                            Bundle argi=new Bundle();
                                            argi.putString("move","editexistingmemberofgroup");
                                            argi.putString("groupid",groupno.get(position));
                                            argi.putString("userid",groupmembersid.get(positio));
                                            masterClass.setArguments(argi);
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            transaction.add(R.id.newuserrelativelayout, masterClass, "newgroupfragment");
                                            transaction.addToBackStack("editexistingmemberofgroup");
                                            transaction.commit();
                                            group.this.dismiss();

                                        }
                                    });
                                    CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) faab.getLayoutParams();
                                    p.setBehavior(null); //should disable default animations
                                    p.setAnchorId(View.NO_ID); //should let you set visibility
                                    faab.setLayoutParams(p);
                                    faab.setVisibility(View.VISIBLE);
                                    faab.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            MasterClass masterClass=new MasterClass();
                                            Bundle bundle=new Bundle();
                                            bundle.putString("move","addnewmembertogroup");
                                            bundle.putString("groupno",groupno.get(position));
                                            bundle.putString("groupname",grouplist.get(position));
                                            masterClass.setArguments(bundle);
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            transaction.add(R.id.newuserrelativelayout, masterClass, "newgroupfragment");
                                            transaction.addToBackStack("addnewmembertogroup");
                                            transaction.commit();
//                                            filename.respond(groupno.get(position),"",grouplist.get(position),"addnewmembertogroup");

                                            group.this.dismiss();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });



//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        FragmentManager manager=getFragmentManager();
//                        Bundle args=new Bundle();
//                        args.putString("groupname",grouplist.get(position));
//                        args.putString("adminno",groupno.get(position));
//                        args.putString("partytype",partypelist.get(position));
//
//
//
//                    }
//                });
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }
            });
        }
        if (string.equals("investorsearch")){
            readdata(FirebaseDatabase.getInstance().getReference("Investors"), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        partysearchid.add(dataSnapshot1.getKey());
                        partysearchgroupid.add(dataSnapshot1.child("groupid").getValue().toString());
                        partysearchname.add(dataSnapshot1.child("userName").getValue().toString());
                    }
                    if (getActivity()!=null){
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,partysearchname);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                filename.respond(partysearchid.get(position),partysearchgroupid.get(position),partysearchname.get(position),"investorsearch");
                                group.this.dismiss();
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
        }
        if (string.equals("recieversearch")){
            readdata(FirebaseDatabase.getInstance().getReference("Recievers"), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        partysearchid.add(dataSnapshot1.getKey());
                        partysearchgroupid.add(dataSnapshot1.child("groupid").getValue().toString());
                        partysearchname.add(dataSnapshot1.child("userName").getValue().toString());
                    }
                    if (getActivity()!=null){
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,partysearchname);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                filename.respond(partysearchid.get(position),partysearchgroupid.get(position),partysearchname.get(position),"recieversearch");
                                group.this.dismiss();
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
        }
        if (string.equals("investortransaction")){
            readdata(FirebaseDatabase.getInstance().getReference("Investors"), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        partysearchid.add(dataSnapshot1.getKey());
                        partysearchgroupid.add(dataSnapshot1.child("groupid").getValue().toString());
                        partysearchname.add(dataSnapshot1.child("userName").getValue().toString());
                    }
                    if (getActivity()!=null){
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,partysearchname);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                filename.respond(partysearchid.get(position),partysearchgroupid.get(position),partysearchname.get(position),"investortransaction");
                                group.this.dismiss();
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
        }
        if (string.equals("recievertransaction")){
            readdata(FirebaseDatabase.getInstance().getReference("Recievers"), new OnGetDataListner() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        partysearchid.add(dataSnapshot1.getKey());
                        partysearchgroupid.add(dataSnapshot1.child("groupid").getValue().toString());
                        partysearchname.add(dataSnapshot1.child("userName").getValue().toString());
                    }
                    if (getActivity()!=null){
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,partysearchname);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                filename.respond(partysearchid.get(position),partysearchgroupid.get(position),partysearchname.get(position),"recievertransaction");
                                group.this.dismiss();
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
        }
        if (string.equals("userpanelrecievercheckbox")){
            final ArrayList<String> usids=getArguments().getStringArrayList("usids");
            ArrayList<String> usnames=getArguments().getStringArrayList("usnames");
            final ArrayList<String> userstatus=getArguments().getStringArrayList("userstatus");
            Log.d("dfhd","dufd"+userstatus.size());
            Log.d("deowfhd","jdeedufd"+usids.size());

            if (getActivity()!=null){
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,usnames);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        filename.respond(usids.get(position),userstatus.get(position),"","recievertransaction");
                        group.this.dismiss();
                    }
                });
            }

        }
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//       createNewGroup=getActivity().findViewById(R.id.createNewGroup);

        filename= (Filename) getActivity();


//       createNewGroup.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               NewGroup newGroup=new NewGroup();
//               FragmentManager manager=getFragmentManager();
//               FragmentTransaction transaction=manager.beginTransaction();
//               transaction.add(R.id.rellayout,newGroup,"newgroupfragment");
//               transaction.addToBackStack("group to newgroup");
//               transaction.commit();
//
//           }
//       });


    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();




//                recyclerView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d("groupkeandaronclick", "onClick: ");
//                        MasterClass masterClass=new MasterClass();
//                        FragmentManager manager=getFragmentManager();
//                        FragmentTransaction transaction=manager.beginTransaction();
//                        transaction.replace(R.id.rellayout,masterClass);
//                        transaction.commit();
//                    }
//                });





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
    public void passgroupname(String groupname) {
    }


}
