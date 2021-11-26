package com.example.login;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.CommonUtil;

import java.util.ArrayList;
import java.util.List;


public class Course {
    String _lctreEstblSemstrListURL = "http://192.168.123.197:8080/lctreEstblSemstrList.act";
    String _lctreEstblMajorListURL  = "http://192.168.123.197:8080/lctreEstblMajorList.act";
    String _lctreListURL            = "http://192.168.123.197:8080/getlctreList.act";
    String _insertTTinfoURL            = "http://192.168.123.197:8080/insertTTinfo.act";

    Activity activity = null;



    /**
     * 학기 리스트 조회
     * @return
     */
    public void getSemesterList(Handler handler)
    {
        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    ArrayList<String> result = new ArrayList<String>();
                    JSONObject obj = CommonUtil.getHttpData(_lctreEstblSemstrListURL, jsonObject);

                    result.clear();
                    for(int i = 0; i < obj.optJSONArray("result").length(); i++)
                    {
                        JSONObject value = (JSONObject)obj.optJSONArray("result").get(i);
                        result.add(value.optString("estblSemstr"));
                    }
                    Log.d("result.semester", result.toString());

                    Message messsage = handler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("data", result);

                    messsage.setData(bundle);
                    handler.sendMessage(messsage);

                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 개설학과 리스트 조회
     * @param handler
     * @return
     */
    public void getEstblMajorList(Handler handler, String semester)
    {
        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("estblSemstr", semester);

                    Log.d("put.semstr", jsonObject.toString());

                    JSONObject obj = CommonUtil.getHttpData(_lctreEstblMajorListURL, jsonObject);
                    ArrayList<String> result = new ArrayList<String>();

                    result.clear();
                    for(int i = 0; i < obj.optJSONArray("result").length(); i++)
                    {
                        JSONObject value = (JSONObject)obj.optJSONArray("result").get(i);
                        result.add(value.optString("estblMajor"));
                    }
                    Log.d("result.major", result.toString());

                    Message messsage = handler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("data", result);

                    messsage.setData(bundle);
                    handler.sendMessage(messsage);

                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void getLetureList(Handler handler, String semester, String major, String stdnNb)
    {
        new Thread() {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("estblSemstr", semester);
                    jsonObject.put("estblMajor", major);
                    jsonObject.put("stdnNb", stdnNb);

                    Log.d("put.json", jsonObject.toString());

                    JSONObject obj = CommonUtil.getHttpData(_lctreListURL, jsonObject);

                    Log.d("get.json", obj.toString());
                    //Log.d("result.major", result.toString());

                    ArrayList<JSONObject> result = new ArrayList<JSONObject>();

                    result.clear();

                    Message messsage = handler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putString("data", obj.optJSONArray("result").toString());

                    Log.d("result.obj.optJSONArray", obj.optJSONArray("result").toString());


                    messsage.setData(bundle);
                    handler.sendMessage(messsage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getSelectedLectureList(Handler handler, JSONArray array) throws Exception {
        //Log.d("selectedJSONArray", array.toString());
        JSONObject jsonObject = new JSONObject();

        new Thread() {
            public void run() {
                try {

                        Log.d("selectedJSONArray", array.toString());

                        String result = null;
                        Bundle bundle = new Bundle();

                        Message message = handler.obtainMessage();

                        if(array.length() == 0)
                        {
                            bundle.putString("data", "선택된 데이터가 없습니다.");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                        else
                        {
                            for(int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = new JSONObject();
                                obj = (JSONObject)array.get(i);
                                CommonUtil.getHttpData(_insertTTinfoURL, obj);
                            }
                            //CommonUtil.getHttpData(_insertTTinfoURL, array);

                            bundle.putString("data", array.length()+"건이 저장 되었습니다.");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}