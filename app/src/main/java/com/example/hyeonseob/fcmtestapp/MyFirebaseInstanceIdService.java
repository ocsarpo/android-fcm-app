package com.example.hyeonseob.fcmtestapp;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // 설치 시 자동 생성
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: "+ refreshedToken);

        // 생성한 토큰을 서버에 보내 저장
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.0.30:3000/regi_token")
                .post(body)
                .build();
        try{
            client.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
