package com.example.hyeonseob.fcmtestapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getToken();
    }

    public void send_btn_click(View view) {
        String to = ((EditText)findViewById(R.id.input_to)).getText().toString();
        String msg = ((EditText)findViewById(R.id.input_msg)).getText().toString();
        String from = ((EditText)findViewById(R.id.input_from)).getText().toString();

        new HttpAsyncTask().execute("http://192.168.0.30:3000/send_message", from, to, msg);
    }

    private static class HttpAsyncTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            String strUrl = params[0];
            try{
                // 요청
                RequestBody body = new FormBody.Builder()
                        .add("from", params[1])
                        .add("to", params[2])
                        .add("msg", params[3])
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.0.30:3000/send_message")
                        .post(body)
                        .build();
                client.newCall(request).execute();
            }catch (IOException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                Log.d("HttpAsyncTask", s);
            }
        }
    }
}
