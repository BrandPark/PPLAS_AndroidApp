package com.plass.computer.pplas_project.Login;


import android.content.Context;
import android.content.Intent;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.plass.computer.pplas_project.Manager.ManagerSettingActivity;
import com.plass.computer.pplas_project.Patient.LoginActivity2;
import com.plass.computer.pplas_project.R;

import com.plass.computer.pplas_project.common.CustomTask;
import com.plass.computer.pplas_project.common.Message;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    private Context context;
    private RadioGroup userType;    //유저타입, 라디오그룹
    private EditText userID;
    private EditText userPW;
    private CheckBox saveIDChcekBox;
    private Button loginButton;
    private Button changePWButton;

    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        message = new Message();

        userType = findViewById(R.id.userType);
        userID = findViewById(R.id.userID);
        userPW = findViewById(R.id.userPW);
        saveIDChcekBox = findViewById(R.id.saveIDCheckBox);
        loginButton = findViewById(R.id.loginButton);
        changePWButton = findViewById(R.id.changePWButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userID.getText().toString();
                String pw = userPW.getText().toString();
                int typeId = userType.getCheckedRadioButtonId();

                if(checkUser(id, pw,typeId)){
                    if(typeId == R.id.patientType){
                        Intent intent = new Intent(context, LoginActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(typeId == R.id.staffType){
                        Intent intent = new Intent(context, LoginActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        message.information(context, "알림", "관리자 로긴 성공");
                        Intent intent = new Intent(context, ManagerSettingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
    public boolean checkUser(String id, String pw, int typeId){
        try {
            String result = "";

            switch(typeId){
                case R.id.adminType:    //어드민으로 로그인
                    result = new CustomTask().execute(id,pw,"","","","admin","login").get(); break;
                case R.id.staffType:    //의료진으로 로그인
                    result = new CustomTask().execute(id,pw,"","","","staff","login").get(); break;
                case R.id.patientType:    //환자로 로그인
                    result = new CustomTask().execute(id,pw,"","","","patient","login").get(); break;
            }

            if(result.equals("loginOK")) {
                return true;
            }
            else if(result.equals("wrongPW")) {
                message.information(context, "알림", "비밀번호 틀림!");
                userPW.setText("");
                return false;
            }
            else if(result.equals("wrongID")) {
                message.information(context, "알림", "해당 계정이 존재하지 않음!");
                userID.setText("");
                userPW.setText("");
                return false;
            }
        } catch (InterruptedException e) { e.printStackTrace(); } catch (ExecutionException e) { e.printStackTrace(); }

        return false;
    }
}
