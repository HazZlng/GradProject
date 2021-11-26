package com.example.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.CommonUtil;

import org.json.JSONException;
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
import java.util.Iterator;

public class SignInActivity extends AppCompatActivity {

    EditText studentCode, passWord, passWordCF, name, major, emailID;
    //String s_studentCode, s_passWord, s_passWordCF, s_name, s_major, s_emailID;

    String _signinurl = "http://192.168.123.118:8080/loginCreate.act";
    HttpURLConnection httpCon = null;
    Activity activity = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }
    public void signInCancel(View v) {
        finish();
    }

    public void signInSubmit(View v) throws JSONException {
            bind();
            JSONObject jsonObject = new JSONObject();
            if (studentCode.length() == 0) {
                Toast.makeText(getApplicationContext(), "학번을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if (passWord.length() == 0) {
                Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if (passWord.getText().toString().equals(passWordCF.getText().toString()) == false) {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
            else if (name.length() == 0) {
                Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if (major.length() == 0) {
                Toast.makeText(getApplicationContext(), "전공을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else if (emailID.length() == 0) {
                Toast.makeText(getApplicationContext(), "이메일 주소를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
            else {
                jsonObject.put("stdnNb", studentCode.getText().toString());
                jsonObject.put("stdnPswd", passWord.getText().toString());
                jsonObject.put("stdnNm", name.getText().toString());
                jsonObject.put("stdnMajor", major.getText().toString());
                jsonObject.put("stdnEmail", emailID.getText().toString());

                new Thread() {
                    public void run() {
                        try {

                            JSONObject obj = CommonUtil.getHttpData(_signinurl, jsonObject);
                            Log.d("loginClick", jsonObject.toString());

                            /*
                            URL url = new URL(_signinurl);

                            httpCon = (HttpURLConnection) url.openConnection();
                            httpCon.setRequestMethod("POST");

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
*/
                        } catch (ProtocolException protocolException) {
                            protocolException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                Toast.makeText(getApplicationContext(), "다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                finish();
        }
    }


    public void bind(){
        studentCode = (EditText) findViewById(R.id.editID);
        passWord = (EditText) findViewById(R.id.editPW);
        passWordCF = (EditText) findViewById((R.id.editCFPassword));
        name = (EditText) findViewById(R.id.editName);
        major = (EditText) findViewById(R.id.editMajor);
        emailID = (EditText) findViewById(R.id.editEmail_ID);

    }

}
