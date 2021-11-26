package com.example.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.login.R.id.attendanceButton;
import static com.example.login.R.id.container;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Statistics statistics = new Statistics();

    View view;
    Button attendButton;
    String stdnNB;

    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ListView statisticsCourseListView;

    JSONArray addedlectureList;
    StatisticsCourseListAdapter adapter;

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

        LinearLayout dateLayout = (LinearLayout) getView().findViewById(R.id.dateLayout);
        dateLayout.setVisibility(View.INVISIBLE);

        statistics.getAddedLectureList(new StatisticsLectureHandler(), stdnNB);
        statistics.getDate(new DateHandler());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("statisticsFragment", "statistics Fragment called");

        view = (ViewGroup) inflater.inflate(R.layout.fragment_statistic, container, false);
        attendButton = (Button) view.findViewById(attendanceButton);

        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    class StatisticsLectureHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            statisticsCourseListView = (ListView) getView().findViewById(R.id.statisticsCourseListView);

            try {
                //lectureList = new ArrayList<>();
                String result = msg.getData().getString("data");
                addedlectureList = new JSONArray(result);

                Log.d("result.addedlectureList", result.toString());

                adapter = new StatisticsCourseListAdapter(getActivity(), addedlectureList, new ButtonPressedHandler());
                statisticsCourseListView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ButtonPressedHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            String data = msg.getData().getString("data");
            Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();

            statistics.getAddedLectureList(new StatisticsLectureHandler(), stdnNB);
        }
    }

    public class DateHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            TextView dateText = (TextView) getView().findViewById(R.id.dateText);
            String data = msg.getData().getString("data");

            dateText.setText(data);

            LinearLayout dateLayout = (LinearLayout) getView().findViewById(R.id.dateLayout);
            dateLayout.setVisibility(View.VISIBLE);
        }
    }

}