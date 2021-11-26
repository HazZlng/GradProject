package com.hongik.login;

import com.hongik.login.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    @Autowired
    LoginMapper mapper;

    public boolean existStdnInfo(String stdn_nb)
    {
        Map<String, Object> result =  mapper.selectStdnInfo(stdn_nb);

        if (result==null || result.isEmpty())
            return false;
        else
            return true;
    }

    public Map<String, Object> selectStdnInfo(String stdn_nb)
    {
        Map<String, Object> result =  mapper.selectStdnInfo(stdn_nb);

        return result;
    }

    public void insertStdnInfo(Map<String, Object> param)
    {
        mapper.insertStdnInfo(param);
    }

}
