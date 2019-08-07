package com.plass.computer.pplas_project.Patient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.plass.computer.pplas_project.R;

public class LoginActivity2 extends AppCompatActivity {

    private Intent intent;
    private Context context;
    private PatientDataHandleService pService;
    private boolean pBound = false;
    private Button testButton;
    private TextView stateMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        context = this;
        intent = getIntent();

        stateMessageView = findViewById(R.id.stateMessageView);
        testButton = findViewById(R.id.testButton); //응급상황 발생 test버튼
        testButton.setOnClickListener(testListener);
    }

    ServiceConnection mConn = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            pService = ((PatientDataHandleService.LocalBinder)service).getService();
            pBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            pBound = false;
        }
    };

    protected void onStart(){
        super.onStart();

        //응급상황 아닐 때
        String userID = intent.getStringExtra("userID");
        stateMessageView.setText(userID+"환자 로그인 중!!");

        Intent intent = new Intent(context, PatientDataHandleService.class);
        bindService(intent, mConn,context.BIND_AUTO_CREATE);
    }
    protected void onStop(){
        super.onStop();
        if(pBound){
            unbindService(mConn);
            pBound = false;
        }
    }
    View.OnClickListener testListener = new View.OnClickListener(){     //응급상황 발생시 스마트 밴드로 부터 환자의 정보를 받는 버튼을 재현한 리스너
        @Override
        public void onClick(View v){
            PatientData pData = new PatientData();      //응급환자의 정보
            pService.recievePatientData(pData); // 응급환자의 정보를 서비스로 보냄
            stateMessageView.setText("의료진 대기중"); //환자의 안드로이드폰을 으료진 대기중으로 바꿈.
        }
    };



}
