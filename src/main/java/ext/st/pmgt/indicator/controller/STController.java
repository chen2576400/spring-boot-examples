package ext.st.pmgt.indicator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.netfactory.util.web.controller.BaseController;
import ext.st.pmgt.indicator.STIndicatorHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @ClassName STController
 * @Description:
 * @Author hma
 * @Date 2020/10/20
 * @Version V1.0
 **/
@Controller
@RequestMapping("/st")
public class STController extends BaseController {


    @RequestMapping(value = "/getDataByPlan", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByPlan(@RequestParam(value = "planId", required = false) String planId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByPlan?planId=5706
        return STIndicatorHelper.service.api1(planId);
    }

    @RequestMapping(value = "/getDataByPlan2", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByPlan2(@RequestParam(value = "planId", required = false) String planId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByPlan2?planId=5706
        return STIndicatorHelper.service.api2(planId);
    }

    @RequestMapping(value = "/getDataByUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByUser(@RequestParam(value = "userId", required = false) String userId,
                                @RequestParam(value = "time1", required = false) String time1,
                                @RequestParam(value = "time2", required = false) String time2
                                ) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByUser?userId=PiDim
        return STIndicatorHelper.service.api3(userId,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()));
    }

    @RequestMapping(value = "/getDataByActivityId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getPert(@RequestParam(value = "activityId", required = false) String activityId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByActivityId?activityId=5897

        return STIndicatorHelper.service.api4(activityId);
    }

}