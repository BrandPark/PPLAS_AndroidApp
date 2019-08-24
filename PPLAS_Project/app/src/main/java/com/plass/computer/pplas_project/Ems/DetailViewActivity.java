package com.plass.computer.pplas_project.Ems;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.plass.computer.pplas_project.common.PatientData;
import com.plass.computer.pplas_project.R;

public class DetailViewActivity extends AppCompatActivity {
    private Context context;
    private PatientData patientData;
    private int arrayPosition;

    private TextView patientName;
    private TextView pulse;
    private TextView temperature;
    private Button acceptButton;
    private Button denyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        setTitle("응급환자 정보");
        context = this;

        patientName = (TextView)findViewById(R.id.patientName);
        pulse = (TextView)findViewById(R.id.pulse);
        temperature = (TextView)findViewById(R.id.temperature);
        acceptButton = (Button)findViewById(R.id.acceptButton);
        denyButton = (Button)findViewById(R.id.denyButton);

        patientData = new PatientData(getIntent().getStringExtra("patientData"));
        arrayPosition = getIntent().getIntExtra("arrayPosition",-1);

        patientName.setText(patientData.getName());
        pulse.setText(patientData.getPulse());
        temperature.setText(patientData.getTemperature());

        acceptButton.setOnClickListener(onClickListener);
        denyButton.setOnClickListener(onClickListener);


    }
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent = new Intent();
            switch(v.getId()){
                case R.id.acceptButton:
                    setResult(RESULT_OK,intent);
                    finish();
                    break;
                case R.id.denyButton:
                    intent.putExtra("arrayPosition",arrayPosition);
                    setResult(RESULT_CANCELED,intent);
                    finish();
                    break;
            }
        }
    };
}
