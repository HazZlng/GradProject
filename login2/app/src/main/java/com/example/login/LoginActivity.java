package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CommonUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {
    TextView idtext, pwtext;
    String _loginurl = "http://192.168.123.197:8080/login.act";
    Activity activity = null;

    public String stdntNB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;
    }

    public void on1(View v) {
        idtext = (TextView)findViewById(R.id.editID);
        pwtext = (TextView)findViewById(R.id.editPW);
        JSONObject jsonObject = new JSONObject();
        Intent main = new Intent(this, MainActivity.class);

        //String temp = "{\"stdnNb\""+":"+"\""+idtext.getText().toString()+"\""+ "," + "\"stdnPswd\""+":" + "\"" + pwtext.getText().toString() + "\"" + "}";
        try {
            jsonObject.put("stdnNb", idtext.getText().toString());
            jsonObject.put("stdnPswd", pwtext.getText().toString());


            //Log.d("loginClick", obj.optString("errorMsg"));

            new Thread(){
                public void run() {
                    try {
                        JSONObject obj = CommonUtil.getHttpData(_loginurl, jsonObject);
                        Log.d("loginClick", jsonObject.toString());

                        if(Integer.parseInt(obj.optString("errorCode", "0"))!=0)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, obj.optString("errorMsg"), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, jsonObject.optString("stdnNb") + "님이 로그인 하셨습니다.", Toast.LENGTH_SHORT).show();
                                    //stdntNB = jsonObject.optString("stdnNb");
                                    main.putExtra("stdnNb", jsonObject.optString("stdnNb"));
                                    startActivity(main);
                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }



    public void signIn(View v){
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }

    public void findPW(View v) {
        Intent i = new Intent(this, FindPWActivity.class);
        startActivity(i);
    }

}

