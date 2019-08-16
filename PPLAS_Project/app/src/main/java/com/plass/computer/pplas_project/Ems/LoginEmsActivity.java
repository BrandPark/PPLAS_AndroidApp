package com.plass.computer.pplas_project.Ems;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.plass.computer.pplas_project.Patient.PatientData;
import com.plass.computer.pplas_project.R;
import com.plass.computer.pplas_project.common.MqttTask;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class LoginEmsActivity extends AppCompatActivity {
    private Context context;
    private String emsID;
    private String emsName;

    private MqttTask mqttTask;
    private MqttAndroidClient mqttAndroidClient;

    private ListView patientList;
    private Button sendMessageTest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ems);
        setTitle(getIntent().getStringExtra("title"));
        context = this;
        emsID = getIntent().getStringExtra("userID");
        emsName = "119구조대";                                     //디비로 부터 가져올 예정.

        patientList = findViewById(R.id.patientList);
        sendMessageTest = findViewById(R.id.sendMessageTest);

        mqttTask = new MqttTask(context,"",emsID,"ems");    //MqttAndroidClient객체생성, mqttConnect, mqttSetCallback
        mqttAndroidClient = mqttTask.getMqttClient();

        sendMessageTest.setOnClickListener(new View.OnClickListener(){              //환자의 안드로이드폰으로 message send.
            @Override
            public void onClick(View v){
                try {
                    mqttAndroidClient.publish("user/patient"+"patient1",emsName.getBytes(),0,false);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void publishToPatient(String patientID, String emsName){     //실제로 DetailViewActivity에서 퍼블리시 할 예정이기 때문에 만들어놓은 메소드.
        try {
            mqttAndroidClient.publish("user/patient/"+patientID, "ems move!!".getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
