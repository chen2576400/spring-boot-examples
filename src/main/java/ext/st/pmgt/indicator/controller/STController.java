package ext.st.pmgt.indicator.controller;

import com.pisx.tundra.netfactory.util.web.controller.BaseController;
import ext.st.pmgt.indicator.STIndicatorHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
        return STIndicatorHelper.service.getDataByPlanId(planId);
    }

    @RequestMapping(value = "/getDataByProject", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByProject(@RequestParam(value = "projectId", required = false) String projectId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByProject?projectId=5480
        return STIndicatorHelper.service.getDataByProjectId(projectId);
    }

    @RequestMapping(value = "/getDataByUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByUser(@RequestParam(value = "userId", required = false) String userId,
                                @RequestParam(value = "time1", required = false) String time1,
                                @RequestParam(value = "time2", required = false) String time2
                                ) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByUser?userId=917&time1=???&time2=???
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp date1 = new Timestamp(simpleDateFormat.parse(time1).getTime());
        Timestamp date2 = new Timestamp(simpleDateFormat.parse(time2).getTime());
        return STIndicatorHelper.service.getDataByUserIdAndTime(userId,date1,date2);
    }

    @RequestMapping(value = "/getDataByActivityId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getPert(@RequestParam(value = "activityId", required = false) String activityId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByActivityId?activityId=5897

        return STIndicatorHelper.service.getDataByActId(activityId);
    }

}