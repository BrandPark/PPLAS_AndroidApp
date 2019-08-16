package com.plass.computer.pplas_project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class DoctorListActivity extends AppCompatActivity {
    ListView doctorList;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        context = this;
        doctorList = findViewById(R.id.doctorList);
    }
}
