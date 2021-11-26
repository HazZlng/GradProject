package com.hongik.attendance;

import com.hongik.attendance.mapper.AttendanceMapper;
import com.hongik.common.CustomMap;
import com.hongik.stdnttinfo.mapper.StdnttinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {
    @Autowired
    AttendanceMapper mapper;

    public void insertAttendantInfo( Map<String, Object> param )
    {
        mapper.insertAttendantInfo(param);
    }

    public List<CustomMap> selectAttendantInfo(Map<String, Object> param )
    {
        List<CustomMap> result = mapper.selectAttendantInfo(param);
        return result;
    }


}
