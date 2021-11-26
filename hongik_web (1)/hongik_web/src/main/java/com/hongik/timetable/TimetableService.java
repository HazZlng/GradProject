package com.hongik.timetable;

import com.hongik.common.CustomMap;
import com.hongik.login.mapper.LoginMapper;
import com.hongik.timetable.mapper.TimetableMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TimetableService {

    @Autowired
    TimetableMapper mapper;

    /**
     * 학기 목록 조회
     * @return
     */
    public List<CustomMap> getLctreEstblSemstrList()
    {
        return mapper.selectLctreEstblSemstr();
    }

    /**
     * 개설학과 목록 조회
     * @param estbl_semstr
     * @return
     */
    public List<CustomMap> getLctreEstblMajorList(String estbl_semstr)
    {
        return mapper.selectLctreEstblMajor(estbl_semstr);
    }

    /**
     * 강의 목록 조회
     * @param estbl_semstr
     * @param estbl_major
     * @return
     */
    public List<CustomMap> getLctreList(Map<String, Object> paramMap)
    {
        return mapper.selectLctreList(paramMap);
    }


    /**
     * 과목코드로 강의목록 조회
     * @param subjctCode
     * @return
     */
    /*
    public List<CustomMap> getLctreListBySubjctCode(String subjctCode)
    {
        return mapper.selectLctreListBySubjctCode(subjctCode);
    }*/


}
