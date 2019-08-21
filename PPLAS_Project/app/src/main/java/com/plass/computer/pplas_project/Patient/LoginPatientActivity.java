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

import com.plass.computer.pplas_project.R;
import com.plass.computer.pplas_project.common.PatientData;

public class LoginPatientActivity extends AppCompatActivity {

    private Context context;
    private PatientDataHandleService pService;
    private boolean pBound = false;

    private Button testButton;

    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        context = this;

        setTitle(getIntent().getStringExtra("title"));

        testButton = findViewById(R.id.testButton); //응급상황 발생 test버튼
        testButton.setOnClickListener(testListener);

        userID = getIntent().getStringExtra("userID");
    }


    View.OnClickListener testListener = new View.OnClickListener(){     //응급상황 발생시 스마트 밴드로 부터 응급상황발생을 감지하는 버튼을 재현
        @Override
        public void onClick(View v){
            Intent emsIntent = new Intent(context, EmergencyActivity.class);    //구조대 대기 화면 띄움

            PatientData pData = pService.getPatientData();      //밴드로부터 받은 데이터
            String sendMsg = pData.getMqttMessage();

            emsIntent.putExtra("mqttMessage",sendMsg);
            emsIntent.putExtra("userID",userID);

            startActivity(emsIntent);
            finish();
        }
    };

    //////////////////////////////////////////////서비스///////////////////////////////////////////
    ServiceConnection mConn = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            pService = ((PatientDataHandleService.LocalBinder)service).getService();
            pBound = true;
            Log.e("service_connect","success");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            pBound = false;
        }
    };

    protected void onStart(){
        super.onStart();
        //서비스 실행
        Intent serviceIntent = new Intent(context, PatientDataHandleService.class);
        bindService(serviceIntent, mConn,context.BIND_AUTO_CREATE);
    }
    protected void onStop(){
        super.onStop();
        if(pBound){
            unbindService(mConn);
            pBound = false;
        }
    }


}
