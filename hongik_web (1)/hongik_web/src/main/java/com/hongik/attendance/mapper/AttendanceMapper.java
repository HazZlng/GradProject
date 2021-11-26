package com.hongik.attendance.mapper;

import com.hongik.common.CustomMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AttendanceMapper {
    public void insertAttendantInfo( Map<String, Object> param );
    public List<CustomMap> selectAttendantInfo(Map<String, Object> param );
}
