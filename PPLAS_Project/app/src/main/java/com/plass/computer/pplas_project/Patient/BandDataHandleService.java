package com.plass.computer.pplas_project.Patient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class BandDataHandleService extends Service {

    private BandData bandData;
    private TimerTask timerTask;
    private Timer timer;

    public BandDataHandleService(){
        bandData = BandData.getInstance(getDataFromBand()); //BandData객체 생성

        timerTask = new TimerTask(){        //주기적으로 서버로 데이터를 보낸다.
            @Override
            public void run(){
                bandData.updateBandData(getDataFromBand());
                //((LoginPatientActivity)LoginPatientActivity.context).publishToServer("band_data:"+bandData.getBandMessage());       //서버에 환자의ㅏ 데이터를 3초마다 전송
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,1000,3000);   //실행되고 1초후에, 3초마다 실행

    }


    @Override
    public IBinder onBind(Intent intent) {  //3 onBind()메서드 재정의; 바인더 객체 생성 & 리턴
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        BandDataHandleService getService(){  //바인더 클래스정의; 현재의 서비스 객체 리턴
            return BandDataHandleService.this;
        }
    }
    //------------------------------------------------------밴드 메소드 --------------------------------------------------
    public String getDataFromBand(){    //밴드로부터 데이터를 가져오는 메소드

        String bandData = "150%39.8%37.56653:126.9779691900000:38"; //밴드로부터 데이터를 받은 것을 가정. 맥박%체온%위치

        return bandData;
    }
    public BandData getBandData(){
        return bandData;
    }

}
