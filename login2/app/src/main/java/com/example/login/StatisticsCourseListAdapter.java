package com.example.login;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;

public class StatisticsCourseListAdapter extends BaseAdapter {
    String _insertAttendantInfoURL                 = "http://192.168.123.197:8080/insertAttendantInfo.act";

    Statistics statistics = new Statistics();
    StatisticFragment sf = new StatisticFragment();

    private Context context;
    private JSONArray statisticslectureList;
    StatisticFragment.ButtonPressedHandler handler;

    public StatisticsCourseListAdapter(Context context, JSONArray statisticslectureList, StatisticFragment.ButtonPressedHandler handler) {
        this.context = context;
        this.statisticslectureList = statisticslectureList;
        this.handler = handler;
    }

    @Override
    public int getCount() { return statisticslectureList.length(); }

    @Override
    public Object getItem(int position) {
        Object result = null;
        try {
            result = statisticslectureList.get(position);
        } catch (Exception e) {        }
        return result; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.statistics, null);

        Button button                   = (Button)   v.findViewById(R.id.attendanceButton);
        TextView courseTitle            = (TextView) v.findViewById(R.id.courseTitle);
        TextView statisticsCredit       = (TextView) v.findViewById(R.id.statisticsCredit);
        TextView statisticsLctreRoom    = (TextView) v.findViewById(R.id.statisticsLctreRoom);
        TextView statisticsLctreTime    = (TextView) v.findViewById(R.id.statisticsLctreTime);

        try {
            JSONObject obj = (JSONObject) statisticslectureList.get(position);
            courseTitle.setText(obj.optString("subjctNm"));
            statisticsCredit.setText(obj.optString("credit")+"학점");
            statisticsLctreRoom.setText(obj.optString("lctreTime"));
            statisticsLctreTime.setText(obj.optString("lctreRoom"));
            String subjectCode = obj.optString("subjctCode");
            button.setTag(position);

            if(obj.optString("atendYn").equals("Y"))
            {
                button.setEnabled(false);
                button.setText("출석완료");
                //button.setVisibility(View.INVISIBLE);
            }
            else
            {
                button.setEnabled(true);
                button.setText("출석하기");
                //button.setVisibility(View.VISIBLE);
            }

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    try{
                        Log.d("button.clicked", obj.toString());
                        Log.d("button.gettag", button.getTag()+"");
                        JSONObject obj1 = (JSONObject) statisticslectureList.get((Integer) button.getTag());
                        obj1.put("atendyn", "Y");

                        Log.d("button.gettag", obj1+"");

                        statistics.attendent(handler, obj1);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            v.setTag(obj.optString("subjctNm"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }

}
