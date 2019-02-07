package com.example.vyanktesh.amesh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UsermessageAdapter extends RecyclerView.Adapter<UsermessageAdapter.UsermessageViewHolder> {

    List<Groupadminmessangingmodel> messageText=new ArrayList<>();

    public UsermessageAdapter(){}
    public UsermessageAdapter(List<Groupadminmessangingmodel> messageText) {
        this.messageText = messageText;
    }

    @NonNull
    @Override
    public UsermessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.usermessagedialog,viewGroup,false);
        UsermessageViewHolder usermessageViewHolder=new UsermessageViewHolder(view);
        return usermessageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsermessageViewHolder usermessageViewHolder, int i) {
        usermessageViewHolder.messageTextview.setText(messageText.get(i).message);
    }

    @Override
    public int getItemCount() {
        return messageText.size();
    }

    public class UsermessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageTextview;
        public UsermessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextview=itemView.findViewById(R.id.messagetext);
        }
    }
}
