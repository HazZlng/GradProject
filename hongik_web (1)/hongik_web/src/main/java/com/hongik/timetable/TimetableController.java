package com.hongik.timetable;


import com.hongik.common.CustomMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TimetableController {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    TimetableService service;

    @RequestMapping(value = "/lctreEstblSemstrList.act")
    public ModelAndView lctreEstblSemstrList() {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        List<CustomMap> result = service.getLctreEstblSemstrList();
        mv.addObject("result", result);

        logger.debug(result);
        return mv;
    }

    @RequestMapping(value = "/lctreEstblMajorList.act")
    public ModelAndView lctreEstblMajorList(@RequestBody Map<String, Object> paramMap) {
        ModelAndView mv = new ModelAndView("jsonView");

        String estblSemstr = paramMap.get("estblSemstr")+"";

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        List<CustomMap> result = service.getLctreEstblMajorList(estblSemstr);
        mv.addObject("result", result);

        logger.debug(result);
        return mv;
    }


    @RequestMapping(value = "/getlctreList.act")
    public ModelAndView getlctreList(@RequestBody Map<String, Object> paramMap){
        ModelAndView mv = new ModelAndView("jsonView");

//        Map<String,String> paramMap = new HashMap<>();
//        String estblSemstr = paramMap.get("estblSemstr")+"";
//        String estblMajor = paramMap.get("estblMajor")+"";
//        String stdnNb = paramMap.get("stdnNb")+"";

        logger.debug(paramMap);

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        List<CustomMap> result = service.getLctreList(paramMap);
        mv.addObject("result", result);

        logger.debug(result);

        return mv;
    }

    /*
    @RequestMapping(value = "/getlctreListBySubjctCode.act")
    public ModelAndView getlctreListBySubjctCode(@RequestBody Map<String, Object> paramMap){
        ModelAndView mv = new ModelAndView("jsonView");

        String subjctCode = paramMap.get("subjctCode")+"";

        logger.debug(subjctCode);

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        List<CustomMap> result = service.getLctreListBySubjctCode(subjctCode);
        mv.addObject("result", result);

        logger.debug(result);

        return mv;
    }
    */
}
