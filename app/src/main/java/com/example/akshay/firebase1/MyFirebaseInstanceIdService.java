package com.example.akshay.firebase1;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstanceIdSer";
    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {


        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onTokenRefresh: "+recentToken);
    }
}
