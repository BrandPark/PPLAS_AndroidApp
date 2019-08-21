package com.plass.computer.pplas_project.Ems;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.plass.computer.pplas_project.common.PatientData;
import com.plass.computer.pplas_project.R;

public class DetailViewActivity extends AppCompatActivity {
    private Context context;
    private PatientData patientData;

    private TextView patientName;
    private TextView heartbeat;
    private TextView temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        setTitle("응급환자 정보");
        context = this;

        patientName = (TextView)findViewById(R.id.patientName);
        heartbeat = (TextView)findViewById(R.id.heartbeat);
        temperature = (TextView)findViewById(R.id.temperature);

        patientData = new PatientData(getIntent().getStringExtra("patientData"));

        patientName.setText(patientData.getName());
        heartbeat.setText(patientData.getHeartbeat());
        temperature.setText(patientData.getTemperature());
    }
}
