package com.plass.computer.pplas_project.Patient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.plass.computer.pplas_project.R;

public class ResponseMessageActivity extends AppCompatActivity {

    private TextView emsStateText;
    private String emsName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_message);
        setTitle("구조대 호출 성공");

        emsName = getIntent().getStringExtra("emsName");

        emsStateText = findViewById(R.id.emsStateText);
        emsStateText.setText(emsName + "오는 중");
    }
}
