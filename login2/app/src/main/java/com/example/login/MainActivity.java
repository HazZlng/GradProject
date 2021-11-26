package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adpater;
    private List<Notice> noticeList;
    private long lastTimeBackPressed;

    LinearLayout notice;
    RelativeLayout fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        String stdnNB = i.getStringExtra("stdnNb");

        // 공지사항 리스트
        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));
        noticeList.add(new Notice("공지사항테스트", "관리자", "2021-01-01"));

        adpater = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adpater);

        final Button courseButton = (Button) findViewById(R.id.courseButton);
        final Button statisticButton = (Button) findViewById(R.id.statisticButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        notice = (LinearLayout) findViewById(R.id.notice);
        fragment = (RelativeLayout) findViewById(R.id.fragment);

        Bundle bundle = new Bundle();
        bundle.putString("stdnNB", stdnNB);

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){


                //공지 레이아웃 가림
                notice.setVisibility(View.GONE);
                fragment.setVisibility(View.VISIBLE);

                //프래그먼트 전환시 색 바뀜
                courseButton.setBackgroundColor((getResources().getColor(R.color.white)));
                statisticButton.setBackgroundColor((getResources().getColor(R.color.design_default_color_primary)));
                scheduleButton.setBackgroundColor((getResources().getColor(R.color.design_default_color_primary)));

                //선택한 프래그먼트 백스택에 저장 후 전환
                FragmentManager fragmantManager = getSupportFragmentManager();

                Log.d("backstack", "[S]entryCount="+fragmantManager.getBackStackEntryCount());

                CourseFragment courseFragment = new CourseFragment();
                courseFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, courseFragment).commit();

                /*
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
*/
                Log.d("backstack", "[E]entryCount="+fragmantManager.getBackStackEntryCount());
            }
        });

        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                notice.setVisibility(View.GONE);
                fragment.setVisibility(View.VISIBLE);

                courseButton.setBackgroundColor((getResources().getColor(R.color.design_default_color_primary)));
                statisticButton.setBackgroundColor((getResources().getColor(R.color.white)));
                scheduleButton.setBackgroundColor((getResources().getColor(R.color.design_default_color_primary)));

                FragmentManager fragmantManager = getSupportFragmentManager();

                Log.d("backstack", "[S]entryCount="+fragmantManager.getBackStackEntryCount());

                StatisticFragment statisticFragment = new StatisticFragment();
                statisticFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, statisticFragment).commit();

                /*
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
*/
                Log.d("backstack", "[E]entryCount="+fragmantManager.getBackStackEntryCount());
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                notice.setVisibility(View.GONE);
                fragment.setVisibility(View.VISIBLE);

                courseButton.setBackgroundColor((getResources().getColor(R.color.design_default_color_primary)));
                statisticButton.setBackgroundColor((getResources().getColor(R.color.design_default_color_primary)));
                scheduleButton.setBackgroundColor((getResources().getColor(R.color.white)));


                FragmentManager fragmantManager = getSupportFragmentManager();

                Log.d("backstack", "[S]entryCount="+fragmantManager.getBackStackEntryCount());

                ScheduleFragment scheduleFragment = new ScheduleFragment();
                scheduleFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, scheduleFragment).commit();

                /*
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
*/
                Log.d("backstack", "[E]entryCount="+fragmantManager.getBackStackEntryCount());
            }
        });
    }

    //뒤로가기 기능 구현
    @Override
    public void onBackPressed() {
        notice.setVisibility(View.VISIBLE);
        fragment.setVisibility(View.GONE);
        /*
        FragmentManager fragmantManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Log.d("backstack_pop", "[S]entryCount="+fragmantManager.getBackStackEntryCount());

        //백스택에 프래그먼트가 있을 시 뒤로가기
        if(fragmantManager.getBackStackEntryCount() > 0) {
            fragmantManager.popBackStack();
            fragmentTransaction.commit();
        }

        else if (fragmantManager.getBackStackEntryCount() == 0)
        {
            //fragmentTransaction.commit();
            //this.findViewById(R.id.notice).setVisibility(View.VISIBLE);
        }

        Log.d("backstack_pop", "[E]]entryCount="+fragmantManager.getBackStackEntryCount());
         */
        /*
        //메인 레이아웃이 VISIBLE일때 뒤로가기 두번 입력시 종료
        if (this.findViewById(R.id.notice).getVisibility() == View.VISIBLE && System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 로그아웃합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
        */

    }

    public void logout(View v){ finish();}

}
