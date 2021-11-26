package com.example.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.CommonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Statistics {

    String _selectTTinfoURL                 = "http://192.168.123.197:8080/selectAttendantInfo.act";
    String _insertAttendantInfoURL          = "http://192.168.123.197:8080/insertAttendantInfo.act";
    String _selectStdnCreditInfoURL         = "http://192.168.123.197:8080/selectStdnCreditInfo.act";
    String _selectDateURL                   = "http://192.168.123.197:8080/selectDate.act";

    /**
     * 저장한 강의코드 조회
     * @param handler
     */
    public void getAddedLectureList(Handler handler, String stdnNb) {
        new Thread()
        {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    ArrayList<String> result = new ArrayList<String>();

                    jsonObject.put("stdnNb", stdnNb);
                    JSONObject obj = CommonUtil.getHttpData(_selectTTinfoURL, jsonObject);

                    result.clear();
                    for(int i = 0; i < obj.optJSONArray("result").length(); i++)
                    {
                        JSONObject value = (JSONObject)obj.optJSONArray("result").get(i);
                        result.add(value.toString());
                    }
                    Log.d("result.value", result.toString());

                    Message messsage = handler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putString("data", result.toString());

                    messsage.setData(bundle);
                    handler.sendMessage(messsage);

                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void attendent(StatisticFragment.ButtonPressedHandler handler, JSONObject jsonObject)
    {
        new Thread() {
            public void run() {
                try{
                    Bundle bundle = new Bundle();
                    Message message = handler.obtainMessage();

                    CommonUtil.getHttpData(_insertAttendantInfoURL, jsonObject);

                    bundle.putString("data", "출석되었습니다.");
                    message.setData(bundle);
                    handler.sendMessage(message);
                    Log.d("data.put", bundle.getString("data"));

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void getTotalCredit(Handler handler, String stdnNb)
    {
        new Thread()
        {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("stdnNb", stdnNb);
                    JSONObject obj = CommonUtil.getHttpData(_selectStdnCreditInfoURL, jsonObject);

                    Message messsage = handler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putString("data", obj.optString("result"));

                    messsage.setData(bundle);
                    handler.sendMessage(messsage);

                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getDate(Handler handler)
    {
        new Thread()
        {
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();

                    JSONObject obj = CommonUtil.getHttpData(_selectDateURL, jsonObject);

                    Message messsage = handler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putString("data", obj.optString("result"));

                    messsage.setData(bundle);
                    handler.sendMessage(messsage);

                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
