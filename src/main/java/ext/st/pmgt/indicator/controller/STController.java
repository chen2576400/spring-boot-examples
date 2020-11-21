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
        //todo testUrl:http://localhost:8080/st/getDataByPlan?planId=planId
        return STIndicatorHelper.service.getDataByPlanId(planId);
    }

    //根据项目id查询项目相关数据
    @RequestMapping(value = "/getDataByProject", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByProject(@RequestParam(value = "projectId", required = false) String projectId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByProject?projectId=projectId
        return STIndicatorHelper.service.getDataByProjectId(projectId);
    }

//
//    //根据项目id查询下面所有数据
//    @RequestMapping(value = "/getDataByProject2", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Object getDataByProject2(@RequestParam(value = "projectId", required = false) String projectId) throws Exception {
//        //todo testUrl:http://localhost:8080/st/getDataByProject?projectId=projectId
//        return STIndicatorHelper.service.getAllByProjectId(projectId);
//    }

    @RequestMapping(value = "/getDataByUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByUser(@RequestParam(value = "userId", required = false) String userId,
                                @RequestParam(value = "startTime", required = false) String startTime,
                                @RequestParam(value = "endTime", required = false) String endTime
                                ) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByUser?userId=917&time1=???&time2=???
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp date1 = new Timestamp(simpleDateFormat.parse(startTime).getTime());
        Timestamp date2 = new Timestamp(simpleDateFormat.parse(endTime).getTime());
        return STIndicatorHelper.service.getDataByUserIdAndTime(userId,date1,date2);
    }

    @RequestMapping(value = "/getDataByActivityId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getPert(@RequestParam(value = "activityId", required = false) String activityId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByActivityId?activityId=5897

        return STIndicatorHelper.service.getPERTData(activityId);
    }

    @RequestMapping(value = "/getDataByProjectIdAndUserId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDataByProjectIdAndUserId(@RequestParam(value = "projectId", required = false) String projectId,@RequestParam(value = "userId", required = false) String userId) throws Exception {
        //todo testUrl:http://localhost:8080/st/getDataByActivityId?activityId=5897

        return STIndicatorHelper.service.getDataByProjectIdAndUserId(projectId,userId);
    }
}