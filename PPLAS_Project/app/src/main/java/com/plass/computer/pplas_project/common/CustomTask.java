package com.plass.computer.pplas_project.common;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alsrh on 2019-08-03.
 */

public class CustomTask extends AsyncTask<String, Void, String> {

    private String sendMsg, recieveMsg;

    @Override
    protected String doInBackground(String... strings) {
        String jspUrl = "http://192.168.78.1:8080/MGJSP_Book/PPLAS/add.jsp";
        HttpURLConnection conn=null;
        try {
            String tmp;
            URL url = new URL(jspUrl);

            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            // type: 로그인, 회원가입 구별; authority: staff, admin, patient구별
            sendMsg = "id="+strings[0]+"&pw="+strings[1]+"&name="+strings[2]+"&resident_id="+strings[3]+"&phone="+strings[4]+
                    "&authority="+strings[5]+"&type="+strings[6];
            osw.write(sendMsg);
            osw.flush();

            if(conn.getResponseCode() == conn.HTTP_OK) {     //통신 성공
                InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                StringBuffer buffer = new StringBuffer();

                while((tmp=reader.readLine())!=null){
                    buffer.append(tmp);
                }
                recieveMsg = buffer.toString();
            }else{                                             //통신 에러
                Log.i("통신결과", conn.getResponseCode()+"에러");
            }

        } catch (MalformedURLException e) { e.printStackTrace(); } catch(IOException e){ e.printStackTrace(); }
        finally {
            if(conn!=null) conn.disconnect();
            Log.e("conn_disconnect","disconnect");
        }

        return recieveMsg;
    }
}
