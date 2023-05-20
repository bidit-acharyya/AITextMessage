package com.example.textmessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView t, textIn, textOut;
    static String number;

    BroadcastReceiverClass b = new BroadcastReceiverClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = findViewById(R.id.phonenum);
        textIn = findViewById(R.id.textIn);
        textOut = findViewById(R.id.textOut);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            /*ActivityResultLauncher<String[]> locationPermissionRequest =
                    registerForActivityResult(new ActivityResultContracts
                                    .RequestMultiplePermissions(), result -> {
                                Boolean sendSMSGranted = result.getOrDefault(
                                        Manifest.permission.SEND_SMS, false);
                                Boolean receiveSMSGranted = result.getOrDefault(
                                        Manifest.permission.RECEIVE_SMS,false);
                                Boolean readSMSGranted = result.getOrDefault(
                                    Manifest.permission.READ_SMS,false);
                                if (fineLocationGranted != null && fineLocationGranted) {
                                    // Precise location access granted.
                                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                    // Only approximate location access granted.
                                } else {
                                    // No location access granted.
                                }
                            }
                    );

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
            locationPermissionRequest.launch(new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            });
            return;*/
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 1);
        }

        /*phonebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttclicked = true;
            }
        });*/

        /*send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttclicked) {
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sm.sendTextMessage(phonenum.getText().toString().substring(phonenum.getText().toString().length()-4), null, text.getText().toString(), null, null);
                           // Log.d("BENIS", phonenum.getText().toString().substring(phonenum.getText().toString().length()-4));
                        }
                    }, 100);
                }
            }
        });*/

        /*Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                sm.sendTextMessage(number.substring(number.length()-4), null, "penis man", null, null);
                Log.d("BENIS", "BEEENISSSSSSS");
            }
        }, 3000);*/
        //b.onReceive();
    }

    /*BroadcastReceiver b = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            Object[] pdus = (Object[]) (b.get("pdus"));
            //String

            SmsMessage[] msgs = new SmsMessage[pdus.length];

            for(int i = 0; i < pdus.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i], b.getString("format"));
                Log.d("HELLOH", msgs[i].getMessageBody());
                Log.d("HELLOPEOL", msgs[i].getOriginatingAddress());
            }
            t.setText(msgs[0].getMessageBody());
        }
    };*/

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        //filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(b, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(b);
    }
}