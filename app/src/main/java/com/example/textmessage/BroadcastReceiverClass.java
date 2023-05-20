package com.example.textmessage;

import static com.example.textmessage.MainActivity.number;
import static com.example.textmessage.MainActivity.t;
import static com.example.textmessage.MainActivity.textIn;
import static com.example.textmessage.MainActivity.textOut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class BroadcastReceiverClass extends BroadcastReceiver {

    String info, returnMsg;
    URL url;
    URLConnection urlC;
    InputStream is;
    BufferedReader br;
    JSONObject jo;
    Handler h = new Handler();
    SmsManager sm = SmsManager.getDefault();
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b = intent.getExtras();
        Object[] pdus = (Object[]) (b.get("pdus"));
        //String
        //SmsManager sm = SmsManager.getDefault();

        SmsMessage[] msgs = new SmsMessage[pdus.length];

        for(int i = 0; i < pdus.length; i++){
            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i], b.getString("format"));
        }
        t.setText("From: " + msgs[0].getOriginatingAddress());
        textIn.setText(msgs[0].getMessageBody());
        number = msgs[0].getOriginatingAddress();

        LongRunningTask l = new  LongRunningTask();
        l.execute(textIn.toString());

    }

    public class LongRunningTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                info = "";
                url = new URL("http://api.brainshop.ai/get?bid=174969&key=mRhY6JgpPWGSrArN&uid=20&msg="+textIn.getText().toString());
                Log.d("Lakers in 6", url.toString());
                urlC = url.openConnection();
                is = urlC.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                for (String i = br.readLine(); i != null; i = br.readLine())
                    info += i;
                br.close();
                Log.d("TRAVVVVVVY", info);
                return info;
            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            try{
                Log.d("PRANEEEEEETH", info);
                jo = new JSONObject(info);
                returnMsg = jo.getString("cnt");
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textOut.setText(returnMsg);
                        sm.sendTextMessage(number, null, returnMsg, null, null);
                    }
                }, 3000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

