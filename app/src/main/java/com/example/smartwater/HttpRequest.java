package com.example.smartwater;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    public static String sendCmd(String bore_name, String start_code, String flag)
    {
        String response1 = "some error has occured";
        //check whether the msg empty or not
        if(bore_name.length()>0) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://www.atharvantechsys.com/smart_city/update_start.php");
            httppost.addHeader("Cookie","PHPSESSID=459t7l5ipnsf2eretk3cmerta0");

            try {
                System.out.println("++"+bore_name+"++"+start_code+"++"+flag);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("bore_name", bore_name));
                nameValuePairs.add(new BasicNameValuePair("start_code", start_code));
                nameValuePairs.add(new BasicNameValuePair("flag", flag));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                System.out.println("++++++++"+ response.getStatusLine()+"+++++++++++++++++++++++++++++++++++");
                System.out.println("++++++++"+ response.getEntity()+"+++++++++++++++++++++++++++++++++++");
                //System.out.println("++++++++"+ EntityUtils.toString(response.getEntity()) +"+++++++++++++++++++++++++++++++++++");
                //msgTextField.setText(""); //reset the message text field

                return EntityUtils.toString(response.getEntity());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //display message if text field is empty
            System.out.println("bore_name empty.");
        }

        return response1;
    }


    public static String login(){

        String response1 = "some error has occured";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://www.atharvantechsys.com/smart_city/login.php");
        httppost.addHeader("Cookie","PHPSESSID=459t7l5ipnsf2eretk3cmerta0");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("uname", "admin"));
            nameValuePairs.add(new BasicNameValuePair("pwd", "12345"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("++++++++"+ response.getStatusLine()+"+++++++++++++++++++++++++++++++++++");
            System.out.println("++++++++"+ response.getEntity()+"+++++++++++++++++++++++++++++++++++");
            //System.out.println("++++++++"+ EntityUtils.toString(response.getEntity()) +"+++++++++++++++++++++++++++++++++++");
            //msgTextField.setText(""); //reset the message text field
            //Toast.makeText(getBaseContext(),"Sent",Toast.LENGTH_SHORT).show();
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),"Error during login", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),"Error during login", Toast.LENGTH_SHORT).show();
        }
        return response1;
    }

}
