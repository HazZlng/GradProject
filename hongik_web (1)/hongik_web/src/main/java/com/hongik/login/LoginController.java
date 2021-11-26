package com.hongik.login;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class LoginController {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    LoginService service;

    /**
     * 로그인
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/login.act")
    public ModelAndView login(@RequestBody Map<String, Object> paramMap) {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        String stdnNb = paramMap.get("stdnNb")+"";
        String stdnPswd = paramMap.get("stdnPswd")+"";

        logger.debug(paramMap);

        Map<String,Object> result = service.selectStdnInfo(stdnNb);
        logger.debug(result);
        if (result==null || result.isEmpty())
        {
            mv.addObject("errorCode", "-404");
            mv.addObject("errorMsg", "사용자 정보가 없습니다.");
        } else if(result.get("stdnPswd").equals(stdnPswd)==false) {
            mv.addObject("errorCode", "-405");
            mv.addObject("errorMsg", "암호가 일치 하지 않습니다.");
        }

        logger.debug(result);
        return mv;
    }

    /**
     * 회원 가입
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/loginCreate.act")
    public ModelAndView loginCreate(@RequestBody Map<String, Object> paramMap) {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        logger.debug(paramMap);
        try
        {
            String stdnNb = paramMap.get("stdnNb")+"";
            Map<String,Object> result = service.selectStdnInfo(stdnNb);
            if (result!=null && result.isEmpty()==false)
            {
                mv.addObject("errorCode", "-400");
                mv.addObject("errorMsg", "사용자 정보가 존재 합니다.");
            } else {
                service.insertStdnInfo(paramMap);
            }
        } catch (Exception ex) {
            mv.addObject("errorCode", "-401");
            mv.addObject("errorMsg", ex.getMessage());
        }

        return mv;
    }




}
