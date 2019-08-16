package com.plass.computer.pplas_project.Patient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.plass.computer.pplas_project.R;
import com.plass.computer.pplas_project.common.MqttTask;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.w3c.dom.Text;

public class EmergencyActivity extends AppCompatActivity {

    private Context context;

    private TextView illnessName;
    private TextView callStatement;
    private Button callTestButton;

    private MqttTask mqttTask;
    private MqttAndroidClient mqttAndroidClient;
    private String mqttMessage;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        setTitle("응급상황 발생");
        context = this;

        mqttMessage = getIntent().getStringExtra("mqttMessage"); //환자정보
        userID = getIntent().getStringExtra("userID");

        mqttTask = new MqttTask(context,mqttMessage,userID,"patient");  //MqttAndroidClient객체생성, mqttConnect, mqttSetCallback
        mqttAndroidClient = mqttTask.getMqttClient();

        illnessName = findViewById(R.id.illnessName);
        callStatement = findViewById(R.id.callStatement);
        callTestButton = findViewById(R.id.callTestButton);

        illnessName.setText("");
        callStatement.setText("가까운 의료진 호출 중");


        callTestButton.setOnClickListener(new View.OnClickListener(){   //ems로 보내는 message test 버튼
            @Override
            public void onClick(View v) {
                try {
                    mqttAndroidClient.publish("user/ems",mqttMessage.getBytes(),0,false);
                    Log.e("mqttMessage","patient publish");
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
