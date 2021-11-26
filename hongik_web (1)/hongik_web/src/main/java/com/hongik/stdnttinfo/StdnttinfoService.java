package com.hongik.stdnttinfo;

import com.hongik.common.CustomMap;
import com.hongik.stdnttinfo.mapper.StdnttinfoMapper;
import com.hongik.timetable.mapper.TimetableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StdnttinfoService {
    @Autowired
    StdnttinfoMapper mapper;


    public void deleteStdnttInfo( Map<String, Object> param ) { mapper.deleteStdnttInfo(param); }
    public int selectStdnCreditInfo(String stdnNb) { return mapper.selectStdnCreditInfo(stdnNb); }
    public String selectDate() { return mapper.selectDate(); }
    public void insertStdnttInfo( Map<String, Object> param )
    {
        mapper.insertStdnttInfo(param);
    }

    public List<CustomMap> selectStdnttInfo(String stdnNb)
    {
        List<CustomMap> result =  mapper.selectStdnttInfo(stdnNb);

        return result;
    }

}
