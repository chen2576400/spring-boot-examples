package ext.st.pmgt.indicator.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pisx.tundra.foundation.fc.collections.PIArrayList;
import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.PIReference;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.dao.PIPlanActivityDao;
import com.pisx.tundra.pmgt.plan.dao.PIPlanDao;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.dao.ProjectIndicatorDao;
import ext.st.pmgt.indicator.dao.ProjectInstanceINIndicatorDao;
import ext.st.pmgt.indicator.dao.ProjectInstanceOTIndicatorDao;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * @ClassName STServiceImpl
 * @Description:
 * @Author hma
 * @Date 2020/9/29
 * @Version V1.0
 **/
@Service
public class STIndicatorServiceImpl implements STIndicatorService {
    @Autowired
    private ProjectIndicatorDao projectIndicatorDao;

    @Autowired
    private ProjectInstanceINIndicatorDao projectINIndicatorDao;

    @Autowired
    private ProjectInstanceOTIndicatorDao projectOTIndicatorDao;

    @Autowired
    private PIPlanDao piPlanDao;

    @Autowired
    private PIPlanActivityDao piPlanActivityDaolanDao;

    @Override
    public PICollection findProjectINIndicatorByProject(PIProject project) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectINIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(project)));
        return result;
    }

    @Override
    public PICollection findProjectINIndicatorByPlan(PIPlan plan) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectINIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(plan)));
        return result;
    }

    @Override
    public PICollection findProjectINIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectINIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(planActivity)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByProject(PIProject project) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(project)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByPlan(PIPlan plan) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(plan)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(planActivity)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByPlanDeliverable(PIPlanDeliverable planDeliverable) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByPlanDeliverableReference(ObjectReference.newObjectReference(planDeliverable)));
        return result;
    }


    /*
     *参数：planid(数字)
     * 返回数据：权重，汇报偏差，标准偏差，（汇报困难度），标准困难度，广度，关键度，输出评定，平均发布次数，
     * */
    @Override
    public Object api1(String planid) throws PIException {
        String planOid = PIPlan.class.getName() + ":" + planid;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        ObjectReference planReference = (ObjectReference) referenceFactory.getReference(planOid);
        List<Map<String, Object>> result = new ArrayList<>();
        Collection planactivities = piPlanActivityDaolanDao.findByRootReference(planReference);
        for (Object planactivity : planactivities) {
            HashMap<String, Object> map = new HashMap<>();
            List<STProjectInstanceOTIndicator> otIndicators = (List) projectOTIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference((PIPlanActivity) planactivity));
            List<STProjectInstanceINIndicator> inIndicators = (List) projectINIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference((PIPlanActivity) planactivity));
            map.put("任务", planactivity);
            map.put("OT", otIndicators);
            map.put("IN", inIndicators);
            result.add(map);
        }
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }
}