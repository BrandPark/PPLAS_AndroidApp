package com.plass.computer.pplas_project.Patient;

/**
 * Created by alsrh on 2019-08-01.
 */

public class PatientData {

    private String patientName;
    private String patientID;
    private String patientHeartbeat;
    private String patientTemperature;
    private String latitude;
    private String longitude;
    private String altitude;
    private String patientLocation;

    public PatientData(String name, String heartbeat, String temperature, String latitude,String longitude, String altutude){
        this.patientName = "홍길동";
        this.patientID = "patient1";
        this.patientHeartbeat = "150";
        this.patientTemperature = "39.0";
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.patientLocation = latitude + ":" + longitude + ":" + altutude;
    }
    public String getMqttMessage(){
        String msg = patientName+"%"+patientID+"%"+patientHeartbeat+"%"+patientTemperature+"%"+patientLocation;    // 이름 / 아이디 / 심박수 / 체온 / 위치
        return msg;
    }
    public String getHeartbeat(){
        return patientHeartbeat;
    }
    public String getTemperature(){
        return patientTemperature;
    }
    public String getName(){
        return patientName;
    }
    public String getID(){
        return patientID;
    }
}
