package com.example.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class FindPWActivity extends AppCompatActivity {
    EditText studentCode, studentEmail;

    String _signinurl = "http://192.168.123.118:8080/loginCreate.act";
    HttpURLConnection httpCon = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);
    }
    public void findPWCancel(View v) {
        finish();
    }
    public void findPWSubmit(View v){
        bind();
        JSONObject jsonObject = new JSONObject();
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(_signinurl);

                    httpCon = (HttpURLConnection) url.openConnection();
                    httpCon.setRequestMethod("GET");

                    httpCon.setDoOutput(true);
                    httpCon.setDoInput(true);

                    httpCon.setRequestProperty("Accept", "application/json");
                    httpCon.setRequestProperty("Content-type", "application/json");

                    OutputStream os = httpCon.getOutputStream();
                    os.write(jsonObject.toString().getBytes("euc-kr"));
                    os.flush();
                    os.close();

                    BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                    StringBuffer buffer = new StringBuffer();
                    String buf;
                    while ((buf = br.readLine()) != null) {
                        Log.d("recieved", buf);
                        buffer.append(buf);
                    }

                    br.close();

                    JSONObject obj = new JSONObject(buffer.toString());

                } catch (ProtocolException protocolException) {
                    protocolException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        }.start();

    }
    public void bind() {
        studentCode = (EditText)findViewById(R.id.editStudentCode_F);
        studentEmail = (EditText)findViewById(R.id.editEmail_F);
    }
}
