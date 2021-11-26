package com.example;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class CommonUtil {

    /*
    public static JSONObject getHttpData(String httpUrl) throws Exception
    {
        return getHttpData(httpUrl, null);
    }
    */

    public static JSONObject getHttpData(String httpUrl, JSONObject param) throws Exception
    {
        URL url = new URL(httpUrl);

        HttpURLConnection httpCon = null;
        httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("POST");

        httpCon.setDoOutput(true);
        httpCon.setDoInput(true);

        httpCon.setRequestProperty("Accept", "application/json");
        httpCon.setRequestProperty("Content-type", "application/json");

        if (param != null) {
            OutputStream os = httpCon.getOutputStream();
            os.write(param.toString().getBytes("utf-8"));
            os.flush();
            os.close();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String buf;
        while ((buf = br.readLine()) != null) {
            Log.d("recieved", buf);
            buffer.append(buf);
        }

        br.close();

        return new JSONObject(buffer.toString());
    }

    public static JSONObject getHttpData(String httpUrl, JSONArray param) throws Exception
    {
        URL url = new URL(httpUrl);

        HttpURLConnection httpCon = null;
        httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("POST");

        httpCon.setDoOutput(true);
        httpCon.setDoInput(true);

        httpCon.setRequestProperty("Accept", "application/json");
        httpCon.setRequestProperty("Content-type", "application/json");

        if (param != null) {
            OutputStream os = httpCon.getOutputStream();
            os.write(param.toString().getBytes("utf-8"));
            os.flush();
            os.close();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String buf;
        while ((buf = br.readLine()) != null) {
            Log.d("recieved", buf);
            buffer.append(buf);
        }

        br.close();

        return new JSONObject(buffer.toString());
    }
}
