package com.plass.computer.pplas_project.Ems;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.plass.computer.pplas_project.common.PatientData;
import com.plass.computer.pplas_project.R;
import com.plass.computer.pplas_project.common.CustomTask;
import com.plass.computer.pplas_project.common.MqttTask;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginEmsActivity extends AppCompatActivity {
    public static Context context;
    private String emsID;

    private MqttTask mqttTask;
    private MqttAndroidClient mqttAndroidClient;

    private ListView patientList;
    private ArrayList<PatientData> patientArray;
    private PatientAdapter patientAdapter;
    private Button sendMessageTest;

    private String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ems);
        setTitle(getIntent().getStringExtra("title"));
        context = this;
        emsID = getIntent().getStringExtra("userID");
        patientID = "";

        patientList = findViewById(R.id.patientList);
        patientArray = new ArrayList<PatientData>();
        patientAdapter = new PatientAdapter(context, patientArray);
        patientList.setAdapter(patientAdapter);



        sendMessageTest = findViewById(R.id.sendMessageTest);

        mqttTask = new MqttTask(context,"",emsID,"ems");    //MqttAndroidClient객체생성, mqttConnect, mqttSetCallback
        mqttAndroidClient = mqttTask.getMqttClient();

        patientList.setAdapter(patientAdapter);
        Log.e("adapter_add_sucess","success");



        sendMessageTest.setOnClickListener(new View.OnClickListener(){              //환자의 안드로이드폰으로 message send.
            @Override
            public void onClick(View v){
                try {
                    String result = new CustomTask().execute(emsID,"","","","","","searchEmsName").get();

                    if(result.contains("emsName=")){
                        String emsName = (result.split("="))[1];
                        mqttAndroidClient.publish("user/patient/"+"patient1", emsName.getBytes(),0,false);          //여기서 patient1은 아이디인데 mqtt메시지로 받을 예정
                        Log.e("mqttMessage","ems publish");
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //----------------------------------------------------------MqttTask에서 호출할 메소드 들 -----------------------------------------//
    public void addArrayList(PatientData patientData){  //메세지로 받은 환자정보를 Array에 추가하는 메소드
        patientArray.add(patientData);
    }

    public void updateListView(){                       //리스트 뷰를 최신화 시키는 메소드
        patientAdapter = new PatientAdapter(context, patientArray);
        patientList.setAdapter(patientAdapter);
    }

    //------------------------------------------------------------------------------------------------------//

    public void publishToPatient(String patientID, String emsName){     //실제로 DetailViewActivity에서 퍼블리시 할 예정이기 때문에 만들어놓은 메소드.
        try {
            mqttAndroidClient.publish("user/patient/"+patientID, "ems move!!".getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------어댑터 클래스----------------------------------------------
    class PatientAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<PatientData> list;

        public PatientAdapter(Context context, ArrayList<PatientData> list ){
            this.context = context;
            this.list = list;
        }
        @Override
        public int getCount() {
            return patientArray.size();
        }

        @Override
        public Object getItem(int position) {
            return patientArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long)position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listitem_patient, null);     //listitem_patient.xml을 inflate한다.

            TextView patientName = convertView.findViewById(R.id.patientName);
            TextView arriveTime = convertView.findViewById(R.id.arriveTime);

            PatientData patientData = patientArray.get(position);                     //해당 인덱스의 PatientData객체를 가져온다.

            patientName.setText(patientData.getName());
            arriveTime.setText("도착시간");

            Button detailButton = (Button)convertView.findViewById(R.id.detailButton);
            detailButton.setOnClickListener(new DetailButtonListener(patientData));
            return convertView;
        }

        class DetailButtonListener implements View.OnClickListener{
            private PatientData patientData;

            public DetailButtonListener(PatientData patientData) {
                this.patientData = patientData;
            }
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, DetailViewActivity.class);
                intent.putExtra("patientData",patientData.getMqttMessage());
                startActivity(intent);
            }
        }
    }
}
