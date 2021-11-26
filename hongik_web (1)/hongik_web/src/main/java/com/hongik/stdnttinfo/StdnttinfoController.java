package com.hongik.stdnttinfo;

import com.hongik.common.CustomMap;
import com.hongik.login.LoginService;
import com.hongik.timetable.TimetableService;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StdnttinfoController {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    StdnttinfoService service;

    /**
     * 강의 담기
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/insertTTinfo.act")
    public ModelAndView insertTTinfo(@RequestBody Map<String, Object> paramMap)
    {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        logger.debug(paramMap);
        HashMap<String, Object> data = new HashMap<>();

        //JSONObject obj = (JSOarray.get(i);
        String stdnNb = paramMap.get("stdnNb") + "";
        String subjctCode = paramMap.get("subjctCode") + "";

        data.put("stdnNb", stdnNb);
        data.put("subjctCode", subjctCode);

        logger.debug(data);
        service.deleteStdnttInfo(data);

        if ((paramMap.get("check")+"").equals("true"))
          service.insertStdnttInfo(data);

        return mv;
    }

    /**
     * 담은 강의 조회하기
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectTTinfo.act")
    public ModelAndView selectTTinfo(@RequestBody Map<String, Object> paramMap)
    {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");
        logger.debug(paramMap);

        String stdnNb = paramMap.get("stdnNb") + "";
        logger.debug(stdnNb);

        List<CustomMap> result = service.selectStdnttInfo(stdnNb);
        logger.debug(result);

        mv.addObject("result", result);

        return mv;
    }

    @RequestMapping(value="/selectStdnCreditInfo.act")
    public ModelAndView selectStdnCreditInfo(@RequestBody Map<String, Object> paramMap)
    {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");
        logger.debug(paramMap);

        String stdnNb = paramMap.get("stdnNb") + "";
        logger.debug(stdnNb);

        int result = service.selectStdnCreditInfo(stdnNb);
        logger.debug(result);

        mv.addObject("result", result);

        return mv;
    }

    @RequestMapping(value="/selectDate.act")
    public ModelAndView selectDate()
    {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        String result = service.selectDate();
        logger.debug(result);

        mv.addObject("result", result);

        return mv;
    }
}
