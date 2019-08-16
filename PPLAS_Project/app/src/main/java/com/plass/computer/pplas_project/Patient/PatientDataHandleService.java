package com.plass.computer.pplas_project.Patient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.plass.computer.pplas_project.Login.LoginActivity;
import com.plass.computer.pplas_project.common.Message;

import java.util.Timer;
import java.util.TimerTask;

public class PatientDataHandleService extends Service {

    private PatientData pData;
    private TimerTask timerTask;
    private Timer timer;
    static int counter = 0;

    public PatientDataHandleService(){
        pData = new PatientData("환자1","150","39.8","37.56653","126.9779691900000","38");

        timerTask = new TimerTask(){
            @Override
            public void run(){
                //밴드로부터 데이터를 가져오는 함수
                getDataToBand();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,0,3000);   //실행되고 0초후에, 3초마다 실행
    }

    @Override
    public IBinder onBind(Intent intent) {  //3 onBind()메서드 재정의; 바인더 객체 생성 & 리턴
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        PatientDataHandleService getService(){  //바인더 클래스정의; 현재의 서비스 객체 리턴
            return PatientDataHandleService.this;
        }
    }

    public PatientData getPatientData(){
        return pData;
    }
    public String getDataToBand(){
        String bandData = "";
        return bandData;
    }

}
