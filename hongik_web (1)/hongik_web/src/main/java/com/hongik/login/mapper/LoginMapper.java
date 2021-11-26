package com.hongik.login.mapper;

import com.hongik.common.CustomMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginMapper {

    public CustomMap selectStdnInfo(String stdn_nb);
    public void insertStdnInfo(Map<String, Object> param);
    public String getTime();

}
