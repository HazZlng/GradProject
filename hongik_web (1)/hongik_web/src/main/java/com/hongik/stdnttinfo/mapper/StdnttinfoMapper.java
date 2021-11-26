package com.hongik.stdnttinfo.mapper;

import com.hongik.common.CustomMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StdnttinfoMapper {

    public List<CustomMap> selectStdnttInfo(String stdnNb);
    public int selectStdnCreditInfo(String stdnNb);
    public String selectDate();
    public void insertStdnttInfo(Map<String, Object> param);
    public void deleteStdnttInfo(Map<String, Object> param);
    public String getTime();
}
