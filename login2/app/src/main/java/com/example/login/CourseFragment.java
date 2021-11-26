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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CommonUtil;
import com.example.login.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Course course = new Course();
    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayAdapter semesterListAdapter;
    private Spinner semesterListSpiner;
    private ArrayAdapter majorListAdapter;
    private Spinner majorListSpiner;

    int semstrIndex, majorIndex;
    Button searchButton;
    Button saveButton;

    ListView lectureListView;
    JSONArray lectureList;
    LectureListAdapter adapter;

    String stdnNB;

    @Override
    public void onActivityCreated(Bundle b)
    {
        super.onActivityCreated(b);


        Bundle bundle = getArguments();
        stdnNB = bundle.getString("stdnNB");


        semesterListSpiner = (Spinner) getView().findViewById(R.id.semesterListSpiner);
        majorListSpiner = (Spinner) getView().findViewById(R.id.majorListSpiner);

        searchButton = (Button) getView().findViewById(R.id.search_button);
        saveButton = (Button) getView().findViewById(R.id.save_button);

        semesterListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        semesterListSpiner.setAdapter(semesterListAdapter);

        majorListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        majorListSpiner.setAdapter(majorListAdapter);


        /**
         * 학기 스피너 기능
         */
        semesterListSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position", position+"");
                String semester = (String)semesterListAdapter.getItem(position);
                course.getEstblMajorList(new EstblMajorListHandler(), semester);

                semstrIndex = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /**
         * 개설학과 스피너 기능
         */
        majorListSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String major = (String)majorListAdapter.getItem(position);
                majorIndex = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * 검색버튼 기능
         */
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semester = (String)semesterListAdapter.getItem(semstrIndex);
                String major = (String)majorListAdapter.getItem(majorIndex);

                course.getLetureList(new LectureListHandler(), semester, major, stdnNB);
            }
        });


        /**
         * 저장버튼 기능
         */
       saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject obj = null;
                    JSONArray result = new JSONArray();

                    if (lectureList != null) {
                        for (int i = 0; i < lectureList.length(); i++)
                        {
                            obj = (JSONObject) lectureList.get(i);
                            Log.d("optBoolean", obj.optBoolean("check", false) + "");
//                            if (obj.optBoolean("check") == false)
//                                continue;

                            obj.putOpt("stdnNb", stdnNB);
                            Log.d("obj.putOn", obj.toString());
                            result.put(obj);
                        }
                        course.getSelectedLectureList(new SelectedLectureListHandler(), result);
                    }

                    /*
                    if (result.length()==0)
                        Toast.makeText(getActivity(), "선택된 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), result.length()+"건이 저장 되었습니다.", Toast.LENGTH_SHORT).show();
                    */
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        course.getSemesterList(new SemesterListHandler());

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    class SemesterListHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            ArrayList<String> result = msg.getData().getStringArrayList("data");

            semesterListAdapter.clear();
            for(int i = 0; i < result.size(); i++)
            {
                semesterListAdapter.add(result.get(i));
            }
            Log.d("result.semester", semesterListAdapter.toString());

            semesterListSpiner.setSelection(0);
            String semester = semesterListSpiner.getSelectedItem().toString();
        }
    }

    class EstblMajorListHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            ArrayList<String> result = msg.getData().getStringArrayList("data");

            majorListAdapter.clear();
            for(int i = 0; i < result.size(); i++)
            {
                majorListAdapter.add(result.get(i));
            }
            Log.d("result.semester", majorListAdapter.toString());

            majorListSpiner.setSelection(0);
        }
    }

    class LectureListHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            lectureListView = (ListView) getView().findViewById(R.id.courseListView);

            try {
               //lectureList = new ArrayList<>();
                String result = msg.getData().getString("data");
                lectureList = new JSONArray(result);

                Log.d("result.subjctCode", result.toString());

                adapter = new LectureListAdapter(getActivity(), lectureList);
                lectureListView.setAdapter(adapter);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class SelectedLectureListHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            String data = msg.getData().getString("data");
            Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
        }

    }

}