package com.example.login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Statistics statistics = new Statistics();

    ListView statisticsCourseListView;

    JSONArray addedlectureList;
    StatisticsCourseListAdapter adapter;
    String stdnNB;
    List<TableRow> rowList = new ArrayList<>();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onActivityCreated(Bundle b) {
        Log.d("statisticsFragment", "Activity is created");
        super.onActivityCreated(b);

        Bundle bundle = getArguments();
        stdnNB = bundle.getString("stdnNB");

        rowList.add(getView().findViewById(R.id.time0));
        rowList.add(getView().findViewById(R.id.time1));
        rowList.add(getView().findViewById(R.id.time2));
        rowList.add(getView().findViewById(R.id.time3));
        rowList.add(getView().findViewById(R.id.time4));
        rowList.add(getView().findViewById(R.id.time5));
        rowList.add(getView().findViewById(R.id.time6));
        rowList.add(getView().findViewById(R.id.time7));
        rowList.add(getView().findViewById(R.id.time8));
        rowList.add(getView().findViewById(R.id.time9));

        LinearLayout creditLayout = (LinearLayout)getView().findViewById(R.id.creditLayout);
        creditLayout.setVisibility(View.INVISIBLE);

        statistics.getAddedLectureList(new ScheduleLectureHandler(), stdnNB);
        statistics.getTotalCredit(new TotalCreditHandler(), stdnNB);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    class TotalCreditHandler extends  Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            TextView totalCredit = (TextView) getView().findViewById(R.id.totalCredit);
            String result = msg.getData().getString("data");
            totalCredit.setText(result+"학점");

            LinearLayout creditLayout = (LinearLayout)getView().findViewById(R.id.creditLayout);
            creditLayout.setVisibility(View.VISIBLE);
        }
    }

    class ScheduleLectureHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                TableLayout table = (TableLayout)  getView().findViewById(R.id.schedultTable);
                String result = msg.getData().getString("data");
                addedlectureList = new JSONArray(result);

                Log.d("result.addedlectureList", addedlectureList.toString());
                int day = 0;
                for(int i=0; i<addedlectureList.length(); i++)
                {
                    JSONObject obj = addedlectureList.getJSONObject(i);
                    String lctreTime = obj.optString("lctreTime");
                    String subjctNm = obj.optString("subjctNm");
                    String[] lctreTimeArr = lctreTime.split(",");
                    for (String time : lctreTimeArr) {
                        for (int j=0; j<time.trim().length(); j++) {
                            String data = time.trim().substring(j,j+1);
                            Log.d("time", time.trim().substring(j,j+1));
                            if (j==0)
                                day = getDay(data);
                            else {
                                TableRow row = rowList.get(Integer.parseInt(data));
                                TextView view = (TextView)row.getChildAt(day);
                                view.setText(subjctNm);
                                view.setBackgroundResource(R.drawable.cell_shape2);
                                view.setEllipsize(TextUtils.TruncateAt.END);
                                view.setMaxLines(2);
                                view.setTextSize(Dimension.SP, 10);

//                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
//                                view.setLayoutParams(lp);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int getDay(String day) {
            int result = -1;

            switch (day) {
                case "월" : result = 1; break;
                case "화" : result = 2; break;
                case "수" : result = 3; break;
                case "목" : result = 4; break;
                case "금" : result = 5; break;
            }

            return result;
        }

    }
}