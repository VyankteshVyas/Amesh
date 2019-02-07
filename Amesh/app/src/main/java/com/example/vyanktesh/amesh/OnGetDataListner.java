package com.example.vyanktesh.amesh;

import com.google.firebase.database.DataSnapshot;

public interface OnGetDataListner {
    //make new interface for call back
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
