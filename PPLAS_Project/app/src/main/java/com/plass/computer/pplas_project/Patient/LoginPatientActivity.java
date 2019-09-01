package com.plass.computer.pplas_project.Patient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.plass.computer.pplas_project.R;
import com.plass.computer.pplas_project.common.CustomTask;
import com.plass.computer.pplas_project.common.MqttTask;


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.ExecutionException;

public class LoginPatientActivity extends AppCompatActivity {

    public static Context context;
    private BandDataHandleService pService;
    private BandData bandData;
    private boolean pBound = false;

    private TextView nameView;
    private TextView pulseView;
    private TextView temperatureView;

    private String patientID;
    private String patientName;

    private MqttTask mqttTask;
    private MqttAndroidClient mqttAndroidClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        context = this;

        setTitle(getIntent().getStringExtra("title"));

        nameView = findViewById(R.id.patientName);
        pulseView = findViewById(R.id.pulse);
        temperatureView = findViewById(R.id.temperature);

        patientID = getIntent().getStringExtra("userID");       //환자 아이디

        mqttTask = new MqttTask(context,patientID,"patient");
        mqttAndroidClient = mqttTask.getMqttClient();

        try {
            String result = new CustomTask().execute(patientID,"","","","","","searchName").get();

            if(result.contains("findName=")){
                patientName = result.split("=")[1];             //환자 이름
            }
        } catch (InterruptedException e) {e.printStackTrace();} catch (ExecutionException e) {e.printStackTrace();}
    }


    //////////////////////////////////////////////서비스///////////////////////////////////////////
    ServiceConnection mConn = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("test","onServiceConnected_start");
            pService = ((BandDataHandleService.LocalBinder)service).getService();        //서비스에서 밴드로부터 데이터를 받아 객체화 시킨다.

            bandData = pService.getBandData();                  //서비스에서 환자 데이터를 뽑는다.

            nameView.setText(patientName);
            pulseView.setText(bandData.getPulse());
            temperatureView.setText(bandData.getTemperature());

            pBound = true;
            Log.e("test","onServiceConnected_end");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            pBound = false;
        }
    };

    protected void onStart(){
        super.onStart();
        //서비스 실행
        Log.e("test","LoginPatientActivity_onStart()");
        Intent serviceIntent = new Intent(context, BandDataHandleService.class);
        bindService(serviceIntent, mConn,context.BIND_AUTO_CREATE);
        Log.e("test","LoginPatientActivity_bindService()");
    }
    protected void onStop(){
        super.onStop();
        if(pBound){
            unbindService(mConn);
            pBound = false;
        }
    }
    public void publishToServer(String message){                //서비스에서 호출할 메소드
        try {
            mqttAndroidClient.publish("server/patient/"+patientID,message.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
