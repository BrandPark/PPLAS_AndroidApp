package com.plass.computer.pplas_project.Patient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.plass.computer.pplas_project.common.Message;

public class PatientDataHandleService extends Service {
    private PatientData pData;

    public PatientDataHandleService() {
        pData = new PatientData();
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
    public void recievePatientData(PatientData pData){
        this.pData = pData;
        Log.i("data","데이터 전송 성공, 서비스 실행 확인");
    }
    //받은 응급환자의 정보를 서버로 보냄
    public void sendToServer(PatientData pData){
    }

}
