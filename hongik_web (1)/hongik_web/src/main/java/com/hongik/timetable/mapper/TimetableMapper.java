package com.hongik.timetable.mapper;

import com.hongik.common.CustomMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TimetableMapper {

    public List<CustomMap> selectLctreEstblSemstr();
    public List<CustomMap> selectLctreEstblMajor(String estbl_semstr);
    public List<CustomMap> selectLctreList(Map<String, Object> paramMap);
    //public List<CustomMap> selectLctreListBySubjctCode(@Param("subjctCode")String subjctCode);
}
