package ext.st.pmgt.indicator.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PIArrayList;
import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.PIReference;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIRuntimeException;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.dao.PIPlanActivityDao;
import com.pisx.tundra.pmgt.plan.dao.PIPlanDao;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.project.dao.PIProjectDao;
import com.pisx.tundra.pmgt.project.model.PIPmgtBaselineType;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import ext.st.pmgt.indicator.dao.ProjectIndicatorDao;
import ext.st.pmgt.indicator.dao.ProjectInstanceINIndicatorDao;
import ext.st.pmgt.indicator.dao.ProjectInstanceOTIndicatorDao;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private PIProjectDao projectDao;

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

    /*
     *请求参数：计划id
     *以上参数查询各类型全量数据的接口也需要，返回数据：项目基础数据（标准工时，标准工期—>客制化属性），
     * （项目启动时间和当前日期差）、项目周期、预实比（计划所属项目 预算费用和实际费用）（手动填写）
     *
     */
    @Override
    public Object api2(String planid) throws PIException {
        String planOid = PIPlan.class.getName() + ":" + planid;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        PIPlan plan = (PIPlan) referenceFactory.getReference(planOid).getObject();
        PIProject project = plan.getProject();
        return JSONObject.toJSONString(project, SerializerFeature.DisableCircularReferenceDetect);
    }

    /*
     *请求参数：计划id，开始时间和结束时间
     *员工，本周完成任务积累风险，周数，计划工时，实际工时，员工基准(目前暂缺）, （任务全部信息）,(返回数据同第一个api)
     *
     */
    @Override
    public Object api3(String userId,Timestamp actualStartDate,Timestamp actualEndDate) throws PIException {
        String userOid = PIUser.class.getName() + ":" + userId;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        PIUser user = (PIUser) referenceFactory.getReference(userId).getObject();
        EntityManager em = PersistenceHelper.service.getEntityManager();
        List<PIResourceAssignment> result = new ArrayList();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = cb.createQuery();
            Root root = criteriaQuery.from(PIResourceAssignment.class);
            Path key1 = root.get("actualStartDate");
            Path key2 = root.get("actualEndDate");
            Path key3 = root.get("rsrcReference").get("id");

            Subquery<PIResource> subquery = criteriaQuery.subquery(PIResource.class);
            Root root1 = subquery.from(PIResource.class);
            Path key11 = root1.get("objectIdentifier").get("id");
            Path key22 = root1.get("userReference").get("key");
            subquery.select(key11).where(cb.equal(key22, user.getObjectIdentifier()));

            criteriaQuery.select(root).where(cb.and(cb.greaterThanOrEqualTo(key1, actualStartDate), cb.lessThanOrEqualTo(key2, actualEndDate), cb.in(key3).value(subquery)));
            TypedQuery query = em.createQuery(criteriaQuery);
            result = query.getResultList();
        } catch (Exception e) {
            throw new PIException(e);
        } finally {
            em.close();
        }
        result = result.stream().filter(item -> item.getRsrc().getUser().equals(user)).collect(Collectors.toList());

        List<Map<String, Object>> result1 = new ArrayList<>();
        for (PIResourceAssignment piResourceAssignment : result) {
            HashMap<String, Object> map = new HashMap<>();
            PIPlanActivity plannable = (PIPlanActivity) piResourceAssignment.getPlannable();
            List<STProjectInstanceOTIndicator> otIndicators = (List) projectOTIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference((PIPlanActivity) plannable));
            List<STProjectInstanceINIndicator> inIndicators = (List) projectINIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference((PIPlanActivity) plannable));
            map.put("任务", plannable);
            map.put("OT", otIndicators);
            map.put("IN", inIndicators);
            map.put("员工", user);
            result1.add(map);
        }
        return JSONObject.toJSONString(result1, SerializerFeature.DisableCircularReferenceDetect);


    }
}