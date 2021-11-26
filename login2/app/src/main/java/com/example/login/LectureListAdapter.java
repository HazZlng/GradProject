package com.example.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class LectureListAdapter extends BaseAdapter {
    private Context context;
    private JSONArray lectureList;

    public LectureListAdapter(Context context, JSONArray lectureList) {
        this.context = context;
        this.lectureList = lectureList;
    }

    @Override
    public int getCount() {
        return lectureList.length();
    }

    @Override
    public Object getItem(int position) {
        Object result = null;
        try {
            result = lectureList.get(position);
        } catch (Exception e) {        }
        return result;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.lecture, null);

        CheckBox subjctNm = (CheckBox) v.findViewById(R.id.subjectNM);
        //TextView subjctNm = (TextView) v.findViewById(R.id.subjectNM);
        TextView lctreCredit = (TextView) v.findViewById(R.id.lctreCredit);
        TextView lctreTime = (TextView) v.findViewById(R.id.lctreTime);
        TextView lctreRoom = (TextView) v.findViewById(R.id.lctreRoom);

        try {
            JSONObject obj = (JSONObject) lectureList.get(position);
            subjctNm.setText(obj.optString("subjctNm"));
            lctreCredit.setText(obj.optString("credit")+"학점");
            lctreTime.setText(obj.optString("lctreTime"));
            lctreRoom.setText(obj.optString("lctreRoom"));
            String subjctCode = obj.optString("subjctCode");
            subjctNm.setTag(position);

            if (obj.optInt("checkCnt")==0) {
                subjctNm.setChecked(false);
            } else {
                subjctNm.setChecked(true);
            }
            obj.put("check", subjctNm.isChecked());

            subjctNm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        if(subjctNm.isChecked() == true) {
                            JSONObject obj = (JSONObject) lectureList.get((Integer)subjctNm.getTag());
                            obj.put("check", subjctNm.isChecked());
                            Log.d("checked", subjctNm.isChecked()+""+subjctNm.getTag());
                        }
                        else if(subjctNm.isChecked() == false)
                        {
                            obj.remove("check");
                            Log.d("checked", subjctNm.isChecked()+"" + subjctNm.getTag());;
                        }
                    } catch (Exception e) {

                    }
                }
            });
            v.setTag(obj.optString("subjctNm"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    public void checkboxCallback(Handler handler, String subjctCode)
    {
        Message messsage = handler.obtainMessage();

        Bundle bundle = new Bundle();
        bundle.putString("data", subjctCode);

        messsage.setData(bundle);
        handler.sendMessage(messsage);
    }

}
