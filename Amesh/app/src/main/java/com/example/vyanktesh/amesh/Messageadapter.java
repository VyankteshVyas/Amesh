package com.example.vyanktesh.amesh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Messageadapter extends RecyclerView.Adapter<Messageadapter.MessageViewHolder> {

    List<Groupadminmessangingmodel> groupadminmessangingmodels;
    List<String> Tids;

    public Messageadapter(){}
    public Messageadapter(List<Groupadminmessangingmodel> groupadminmessangingmodels, List<String> tids) {
        this.groupadminmessangingmodels = groupadminmessangingmodels;
        Tids = tids;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.readmessageadmindialog,viewGroup,false);
        MessageViewHolder messageViewHolder=new MessageViewHolder(view);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        messageViewHolder.name.setText(groupadminmessangingmodels.get(i).groupname);
        messageViewHolder.number.setText(groupadminmessangingmodels.get(i).read);
        messageViewHolder.message.setMovementMethod(new ScrollingMovementMethod());
        messageViewHolder.message.setText(groupadminmessangingmodels.get(i).message);
    }

    @Override
    public int getItemCount() {
        return Tids.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView name,number,message;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.groupnameadmin);
            number=itemView.findViewById(R.id.gorupmobi);
            message=itemView.findViewById(R.id.messagecontent);
        }
    }
}
