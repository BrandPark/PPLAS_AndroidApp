package com.plass.computer.pplas_project.Login;


import android.content.Context;
import android.content.Intent;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.plass.computer.pplas_project.Ems.LoginEmsActivity;
import com.plass.computer.pplas_project.Manager.ManagerSettingActivity;
import com.plass.computer.pplas_project.Patient.LoginPatientActivity;
import com.plass.computer.pplas_project.R;

import com.plass.computer.pplas_project.common.CustomTask;
import com.plass.computer.pplas_project.common.Message;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    private Context context;
    private RadioGroup userType;    //유저타입, 라디오그룹
    private EditText userID;
    private EditText userPW;
    private CheckBox saveIDCheckBox;
    private Button loginButton;
    private SharedPreferences pref;
    private boolean saveCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        userType = findViewById(R.id.userType);
        userID = findViewById(R.id.userID);
        userPW = findViewById(R.id.userPW);
        saveIDCheckBox = findViewById(R.id.saveIDCheckBox);
        loginButton = findViewById(R.id.loginButton);

        pref = getSharedPreferences("login_info",0);        //로그인 정보를 저장할 프리퍼런스 객체
        saveCheck = false;

        if(savedInstanceState==null){
            String savedID = pref.getString("userID","");
            int selectedType = pref.getInt("userType",R.id.patientType);
            userID.setText(savedID);
            userType.check(selectedType);

        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLoginInfo();
                saveCheck = true;

                String id = userID.getText().toString();
                String pw = userPW.getText().toString();
                int typeId = userType.getCheckedRadioButtonId();

                if(checkUser(id, pw,typeId)){
                    if(typeId == R.id.patientType){
                        Intent intent = new Intent(context, LoginPatientActivity.class);
                        intent.putExtra("title", id+"_login");
                        intent.putExtra("userID",id);
                        startActivity(intent);
                        finish();
                    }
                    else if(typeId == R.id.emsType){
                        Intent intent = new Intent(context, LoginEmsActivity.class);
                        intent.putExtra("title", id+"_login");
                        intent.putExtra("userID",id);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Message.information(context, "알림", "관리자 로긴 성공");
                        Intent intent = new Intent(context, ManagerSettingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    public boolean checkUser(String id, String pw, int typeId){         //MySql에서 로그인정보 확인
        try {
            String result = "";

            switch(typeId){
                case R.id.adminType:    //어드민으로 로그인
                    result = new CustomTask().execute(id,pw,"","","","admin","login").get(); break;
                case R.id.emsType:    //의료진으로 로그인
                    result = new CustomTask().execute(id,pw,"","","","ems","login").get(); break;
                case R.id.patientType:    //환자로 로그인
                    result = new CustomTask().execute(id,pw,"","","","patient","login").get(); break;
            }

            if(result.equals("loginOK")) {
                return true;
            }
            else if(result.equals("wrongPW")) {
                Message.information(context, "알림", "비밀번호 틀림!");
                userPW.setText("");
                return false;
            }
            else if(result.equals("wrongID")) {
                Message.information(context, "알림", "해당 계정이 존재하지 않음!");
                userID.setText("");
                userPW.setText("");
                return false;
            }
        } catch (InterruptedException e) { e.printStackTrace(); } catch (ExecutionException e) { e.printStackTrace(); }

        return false;
    }

    public void saveLoginInfo(){                //로그인정보를 저장하는 함수
        SharedPreferences.Editor editor = pref.edit();

        if(saveIDCheckBox.isChecked()){     //아이디 저장 체크박스가 체크되어있는 경우
            String id = userID.getText().toString();
            int selectedType = userType.getCheckedRadioButtonId();
            editor.putString("userID",id);
            editor.putInt("userType",selectedType);
        } else {                //체크가 안되어있는경우
            editor.putString("userID","");
            editor.putInt("userType",R.id.patientType);
        }

        editor.commit();
    }
}
