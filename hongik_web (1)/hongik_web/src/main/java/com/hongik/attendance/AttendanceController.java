package com.hongik.attendance;

import com.hongik.common.CustomMap;
import com.hongik.stdnttinfo.StdnttinfoService;
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
public class AttendanceController {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    AttendanceService service;

    @RequestMapping(value="/insertAttendantInfo.act")
    public ModelAndView insertAttendantInfo(@RequestBody Map<String, Object> paramMap)
    {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        logger.debug(paramMap);
        HashMap<String, Object> data = new HashMap<>();

        //JSONObject obj = (JSOarray.get(i);
        String stdnNb = paramMap.get("stdnNb") + "";
        String subjctCode = paramMap.get("subjctCode") + "";
        String atendyn = paramMap.get("atendyn") + "";

        data.put("stdnNb", stdnNb);
        data.put("subjctCode", subjctCode);
        data.put("atendyn", atendyn);

        logger.debug(data);
        //service.deleteStdnttInfo(data);
        service.insertAttendantInfo(data);

        return mv;
    }

    @RequestMapping(value="/selectAttendantInfo.act")
    public ModelAndView selectAttendantInfo(@RequestBody Map<String, Object> paramMap)
    {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addObject("errorCode", "0");
        mv.addObject("errorMsg", "");

        HashMap<String, Object> data = new HashMap<>();

        String stdnNb = paramMap.get("stdnNb") + "";
        data.put("stdnNb", stdnNb);
        logger.debug(data);

        List<CustomMap> result = service.selectAttendantInfo(data);
        logger.debug(result);

        mv.addObject("result", result);

        return mv;
    }
}
