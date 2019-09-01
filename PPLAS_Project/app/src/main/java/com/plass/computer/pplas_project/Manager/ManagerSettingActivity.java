package com.plass.computer.pplas_project.Manager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.plass.computer.pplas_project.R;

public class ManagerSettingActivity extends AppCompatActivity {
    private Context context;
    private TextView stateMessageView;
    private Intent intent;
    private Button addStaffButton;
    private Button addPatientButton;
    private Button patientListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_setting);
        setTitle("관리자 설정");
        context = this;
        intent = getIntent();

        stateMessageView = findViewById(R.id.stateMessageView);
        addPatientButton = findViewById(R.id.addPatientButton);
        patientListButton = findViewById(R.id.patientListButton);

        addPatientButton.setOnClickListener(onClickListener);
    }
    protected void onStart(){
        super.onStart();
        stateMessageView.setText("관리자 로그인중!!");
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent = null;
            switch(v.getId()){
                case R.id.addPatientButton:
                    intent = new Intent(context, AddPatientActivity.class);
                    startActivity(intent); break;
            }

        }
    };

}
