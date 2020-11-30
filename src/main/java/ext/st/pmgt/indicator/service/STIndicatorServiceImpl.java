package ext.st.pmgt.indicator.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.PIReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.MembershipLink;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.assignment.PIAssignmentHelper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.calendar.model.PICalendar;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverableLink;
import com.pisx.tundra.pmgt.plan.PIPlanHelper;
import com.pisx.tundra.pmgt.plan.dao.PIPlanActivityDao;
import com.pisx.tundra.pmgt.plan.dao.PIPlanDao;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import com.pisx.tundra.pmgt.plan.model.PlannableDuration;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.dao.PIProjectDao;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.SumPIProject;
import com.pisx.tundra.pmgt.resource.PIResourceHelper;
import com.pisx.tundra.pmgt.resource.model.PIResource;
import com.pisx.tundra.pmgt.util.DurationUtils;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.dao.*;
import ext.st.pmgt.indicator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private DeliverableTypeDao deliverableTypeDao;

    @Autowired
    private ProCompetenceDao proCompetenceDao;

    @Autowired
    private PIPlanDao piPlanDao;

    @Autowired
    private PIPlanActivityDao piPlanActivityDaolanDao;


    @Autowired
    private PIProjectDao projectDao;

    @Autowired
    private ExpectedFinishTimeDao expectedFinishTimeDao;

    @Autowired
    private DeviationDao deviationDao;

    @Autowired
    private DifficultyDao difficultyDao;

    @Override
    public Collection findProjectINIndicatorByProject(PIProject project) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectINIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(project)));
        return result;
    }

    @Override
    public Collection findProjectINIndicatorByPlan(PIPlan plan) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectINIndicatorDao.findByPlanReference(ObjectReference.newObjectReference(plan)));
        return result;
    }

    @Override
    public Collection findProjectINIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectINIndicatorDao.findByPlanActivityReferenceAndPlanReference(ObjectReference.newObjectReference(planActivity), planActivity.getRootReference()));
        return result;
    }

    @Override
    public Collection findProjectOTIndicatorByProject(PIProject project) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectOTIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(project)));
        return result;
    }

    @Override
    public Collection findProjectOTIndicatorByPlan(PIPlan plan) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectOTIndicatorDao.findByPlanReference(ObjectReference.newObjectReference(plan)));
        return result;
    }

    @Override
    public Collection findProjectOTIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectOTIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference(planActivity)));
        return result;
    }

    @Override
    public Collection findProjectOTIndicatorByPlanDeliverable(PIPlanDeliverable planDeliverable) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectOTIndicatorDao.findByPlanDeliverableReference(ObjectReference.newObjectReference(planDeliverable)));
        return result;
    }

    @Override
    public Collection findProjectOTIndicatorByPlanActivityAndPlan(PIPlanActivity planActivity, PIPlan plan) throws PIException {
        ArrayList result = new ArrayList();
        result.addAll(projectOTIndicatorDao.findByPlanActivityReferenceAndPlanReference(ObjectReference.newObjectReference(planActivity), ObjectReference.newObjectReference(plan)));
        return result;
    }


    /*
     *参数：planid(数字)
     * 返回数据：权重，汇报偏差，标准偏差，（汇报困难度），标准困难度，广度，关键度，输出评定，平均发布次数，
     * */
    @Override
    public Object getDataByPlanId(String planid) throws PIException {
        String planOid = PIPlan.class.getName() + ":" + planid;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        ObjectReference planReference = (ObjectReference) referenceFactory.getReference(planOid);
        List<Map<String, Object>> result = new ArrayList<>();
        Collection planactivities = piPlanActivityDaolanDao.findByRootReference(planReference);
        for (Object planactivity : planactivities) {
            result.add(getAllDataByAct((PIPlanActivity) planactivity));
        }
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
//        return result;
    }

    private List<STProjectInstanceOTIndicator> getLatestOt(List<STProjectInstanceOTIndicator> ots) {
        List<STProjectInstanceOTIndicator> result = new ArrayList<>();
        Map<String, List<STProjectInstanceOTIndicator>> map = ots.stream().collect(Collectors.groupingBy(STProjectInstanceOTIndicator::getCode));
        for (Map.Entry<String, List<STProjectInstanceOTIndicator>> entry : map.entrySet()) {
            List<STProjectInstanceOTIndicator> value = entry.getValue();
            value.sort((t1, t2) -> t2.getReportTime().compareTo(t1.getReportTime()));
            result.add(value.get(0));
        }
        return result;
    }


    private Map getAllDataByAct(PIPlanActivity activity) throws PIException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DurationUtils du = new DurationUtils();
        PlannableDuration duration = activity.getStandardDuration();
        double standardDuration = 0;
        if (duration != null) {
            standardDuration = du.getDuration(duration);
        }

        HashMap<String, Object> map = new HashMap<>();
        List<STProjectInstanceOTIndicator> otIndicators = (List) projectOTIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference(activity));
        if (otIndicators.size() > 0) {//同样的ot指标只取最新的
            otIndicators = getLatestOt(otIndicators);
        }
        List<STProjectInstanceINIndicator> inIndicators = (List) projectINIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference(activity));
        map.put("任务id", activity.getOid());
        map.put("任务名称", activity.getName());
        map.put("标准工时", activity.getStandardWorkQty() == null ? null : 0);
        map.put("标准工期", standardDuration);

        map.put("计划工时", activity.getTargetWorkQty());
        map.put("计划工期", activity.getTargetDuration() == null ? 0 : du.getDuration(activity.getTargetDuration()));
        map.put("实际开始时间", activity.getActualStartDate() == null ? null : sdf.format(activity.getActualStartDate()));
        map.put("实际完成时间", activity.getActualEndDate() == null ? null : sdf.format(activity.getActualEndDate()));
//            map.put("ot",otIndicators);
//            map.put("in",inIndicators);
        List<Map<String, Object>> otList = new ArrayList<>();
        for (STProjectInstanceOTIndicator otIndicator : otIndicators) {
            HashMap<String, Object> otMap = new HashMap<>();
            otMap.put("id", otIndicator.getOid());
            otMap.put("完成状态", otIndicator.getCompletionStatus() == null ? "0" : otIndicator.getCompletionStatus());
            otMap.put("标准困难度", otIndicator.getStandardDifficultyValue() == null ? null : otIndicator.getStandardDifficultyValue());
            otMap.put("标准偏差值", otIndicator.getStandardDeviationValue() == null ? null : otIndicator.getStandardDeviationValue());
            otMap.put("汇报困难度", otIndicator.getDifficultyReport() == null ? null : otIndicator.getDifficultyReport());
            otMap.put("汇报偏差值", otIndicator.getDeviationReport() == null ? null : otIndicator.getDeviationReport());
//            otMap.put("汇报困难度", "0.1");
//            otMap.put("汇报偏差值", "0.2");
            otMap.put("广度", otIndicator.getBreadth() == null ? null : otIndicator.getBreadth());
            otMap.put("关键度", otIndicator.getCriticality() == null ? null : otIndicator.getCriticality());
            otMap.put("指标编码", otIndicator.getCode() == null ? null : otIndicator.getCode());
            otList.add(otMap);
        }
        map.put("OT", otList);

        List<Map<String, Object>> inList = new ArrayList<>();
        for (STProjectInstanceINIndicator inIndicator : inIndicators) {
            HashMap<String, Object> inMap = new HashMap<>();
            List<STProjectInstanceOTIndicator> ots = (List<STProjectInstanceOTIndicator>) STIndicatorHelper.service.getOTByIN(inIndicator);
            List<String> otIds = ots.stream().map(item -> {
                try {
                    return item.getOid();
                } catch (PIException e) {
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
            inMap.put("权重", inIndicator.getWeights() == null ? null : inIndicator.getWeights());
            inMap.put("对应ot指标id", otIds);
            inMap.put("id", inIndicator.getOid());

            inList.add(inMap);
        }
        map.put("IN", inList);

        return map;


    }

    /*
     *请求参数：计划id
     *以上参数查询各类型全量数据的接口也需要，返回数据：项目基础数据（标准工时，标准工期—>客制化属性），
     * （项目启动时间和当前日期差）、项目周期、预实比（计划所属项目 预算费用和实际费用）（手动填写）
     *
     */
    @Override
    public Object getDataByProjectId(String projectId) throws PIException {
        String projectOid = PIProject.class.getName() + ":" + projectId;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        PIProject project = (PIProject) referenceFactory.getReference(projectOid).getObject();
        PICalendar calendar = project.getCalendar();
        List<PIPlan> plans = PIPlanHelper.service.getPlans(project);
        PIPlan plan = null;
        double projectduration = 0;
        if (plans.size() > 0) {
            DurationUtils du = new DurationUtils(calendar);
            for (PIPlan piPlan : plans) {
                PlannableDuration duration = piPlan.getTargetDuration();
                projectduration = projectduration + du.getDuration(duration);
            }
        }
        SumPIProject sumProject = PIProjectHelper.service.getSumProject(project);
        ArrayList<Object> list = new ArrayList<>();
        HashMap<String, Object> result = new HashMap<>();
        result.put("项目名称", project.getProjectName());
        result.put("项目id", project.getObjectIdentifier().toString());
        result.put("项目启动时间和当前日期差", getTime(new Timestamp(System.currentTimeMillis()), project.getStartDate()));
        DurationUtils durationUtils = new DurationUtils();
//        result.put("项目周期",durationUtils.getDurationForDisplay(plan.getTargetDuration()));
        result.put("项目周期", String.valueOf(projectduration));
//        result.put("预实比", String.valueOf(sumProject.getTargetCost() / sumProject.getActualCost()));
        result.put("预实比", "0.5");
        List<Long> planIds = plans.stream().map(item -> item.getObjectIdentifier().getId()).collect(Collectors.toList());
        result.put("计划id", planIds);
        //计划

        //todo
        List<Map<Object, Object>> users = new ArrayList<>();
        Collection resources = PIResourceHelper.service.getResources(project).persistableCollection();
        for (Object resource : resources) {
            PIResource res = (PIResource) resource;
            PIUser user = res.getUser();
            Map<Object, Object> u = new HashMap<>();
            u.put("userName", user.getName());
            u.put("userId", user.getObjectIdentifier().getId().toString());

            List<PIGroup> groups = (List<PIGroup>) OrgHelper.service.getImmediateParentGroups(user, false);
            ArrayList<Object> list1 = new ArrayList<>();
            if (groups.size() > 0) {
                PIGroup group = groups.get(0);
                Map<Object, Object> g = new HashMap<>();
                g.put("groupName", group.getName());
                g.put("groupId", group.getObjectIdentifier().getId().toString());
                list1.add(g);
            }

            u.put("所属部门", list1);
            users.add(u);
        }
        ArrayList<Object> 资源部门 = new ArrayList<>();
        ArrayList<Object> 员工姓名 = new ArrayList<>();
//        result.put("部门", 资源部门);
        result.put("员工", users);

        list.add(result);
        return JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);

    }

    private Integer getTime(Timestamp t1, Timestamp t2) {
        long t = t1.getTime() - t2.getTime();
        int days = (int) (t / (1000 * 60 * 60 * 24));
        return days;
    }


    public Collection findActByUserAndTime(PIUser user, Timestamp actualStartDate, Timestamp actualEndDate) throws PIException {

        EntityManager em = PersistenceHelper.service.getEntityManager();
        List<PIResourceAssignment> result = new ArrayList();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = cb.createQuery();
            Root root = criteriaQuery.from(PIResourceAssignment.class);
            Path key1 = root.get("actualStartDate");
            Path key2 = root.get("actualEndDate");
            Path key3 = root.get("rsrcReference").get("key").get("id");

            Subquery<PIResource> subquery = criteriaQuery.subquery(PIResource.class);
            Root root1 = subquery.from(PIResource.class);
            Path key11 = root1.get("objectIdentifier").get("id");
            Path key22 = root1.get("userReference").get("key");
            subquery.select(key11).where(cb.equal(key22, user.getObjectIdentifier()));

            criteriaQuery.select(root).where(cb.and(cb.greaterThanOrEqualTo(key1, actualStartDate), cb.lessThanOrEqualTo(key2, actualEndDate), cb.in(key3).value(subquery)));
            TypedQuery query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());
        } catch (Exception e) {
            throw new PIException(e);
        } finally {
            //em.close();
        }
        List acts = result.stream().map(item -> item.getPlannable()).collect(Collectors.toList());
        return acts;
    }

    /*
     *请求参数：计划id，开始时间和结束时间
     *员工，本周完成任务积累风险，周数，计划工时，实际工时，员工基准(目前暂缺）, （任务全部信息）,(返回数据同第一个api)
     *
     */
    @Override
    public Object getDataByUserIdAndTime(String userId, Timestamp actualStartDate, Timestamp actualEndDate) throws PIException {
        String userOid = PIUser.class.getName() + ":" + userId;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        PIUser user = (PIUser) referenceFactory.getReference(userOid).getObject();

        List<PIPlanActivity> acts = (List) findActByUserAndTime(user, actualStartDate, actualEndDate);

        List<Map<String, Object>> result1 = new ArrayList<>();
        Map<String, Object> nameMap = new HashMap<>();

        for (PIPlanActivity act : acts) {
            result1.add(getAllDataByAct(act));
        }
        result1.add(nameMap);
        return JSONObject.toJSONString(result1, SerializerFeature.DisableCircularReferenceDetect);


    }

    @Override
    public Object getPERTData(String activityId) throws PIException {
        String actId = PIPlanActivity.class.getName() + ":" + activityId;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        PIPlanActivity act = (PIPlanActivity) referenceFactory.getReference(actId).getObject();
        ArrayList<Object> list = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<STExpectedFinishTime> expectTime = (List) getExpextTimeByActivity(act);
        List<String> timeList = expectTime.stream().map(item -> sdf.format(item.getExpectedFinishTime())).collect(Collectors.toList());
        for (String s : timeList) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("预估开始时间", act.getTargetStartDate() == null ? null : sdf.format(act.getTargetStartDate()));
            result.put("截至时间", sdf.format(act.getTargetEndDate()));
            result.put("预估完成时间", s);
            result.put("实际开始时间", act.getActualStartDate() == null ? null : sdf.format(act.getActualStartDate()));
            list.add(result);
        }

        return JSONObject.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public Collection getAllDeliverableType() {
        return deliverableTypeDao.findAll();
    }

    @Override
    public Collection getDeliverableTypeByAct(PIPlanActivity act) throws PIException {
        List<STProjectInstanceOTIndicator> otIndacators = (List) projectOTIndicatorDao.findByPlanActivityReference(ObjectReference.newObjectReference(act));
//        List<STDeliverableType> result = otIndacators.stream().map(item -> item.getDeliverableType()).collect(Collectors.toList());
        HashSet<Object> set = new HashSet<>();
        for (STProjectInstanceOTIndicator otIndacator : otIndacators) {
            if (otIndacator.getDeliverableTypeCode() != null) {
                Collection types = deliverableTypeDao.findByCode(otIndacator.getDeliverableTypeCode());
                if (types.size() > 0) {
                    set.addAll(types);
                }
            }
        }
        return set;
    }

    @Override
    public STProCompetence getProContenceByName(String name) {
        return proCompetenceDao.findByNameEquals(name);
    }

    @Override
    public STDeliverableType findDeliverableTypeByCode(String s) {
        return deliverableTypeDao.findByNameEquals(s);
    }

    @Override
    public STProjectInstanceOTIndicator getOTByCode(String s) {
        return projectOTIndicatorDao.findByCode(s);
    }

    @Override
    public Collection getExpextTimeByActivity(PIPlanActivity act) throws PIException {
        return expectedFinishTimeDao.findByPlanActivityReference(ObjectReference.newObjectReference(act));
    }

    @Override
    public Collection getOTByDeliverableType(STDeliverableType deliverableType) throws PIException {
        return projectOTIndicatorDao.findByDeliverableTypeCode(deliverableType.getCode());
    }

    @Override
    public Collection getPlanDeliverablesByOT(STProjectInstanceOTIndicator ot) throws PIException {
        Collection qr = PersistenceHelper.service.navigate(ot, "roleB", PIPlanDeliverableLink.class, true);
        return qr;
    }

    @Override
    public Collection getDeviationByOTCode(String code) {
        return deviationDao.findByCodeEquals(code);
    }

    @Override
    public Collection getDifficultyByOTCode(String code) {
        return difficultyDao.findByCodeEquals(code);
    }

    @Override
    public Collection getOTByIN(STProjectInstanceINIndicator in) throws PIException {

        String code = in.getOtCode();
        PIPlan plan = in.getProjectPlanInstance();
        List result = new ArrayList();
        EntityManager em = PersistenceHelper.service.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = cb.createQuery();
            Root root = criteriaQuery.from(STProjectInstanceOTIndicator.class);
            Path key1 = root.get("code");
            Path key2 = root.get("planReference").get("key");
            criteriaQuery.select(root).where(cb.equal(key1, code), cb.equal(key2, plan.getObjectIdentifier()));//升序
            TypedQuery query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());
        } catch (Exception e) {
            throw new PIException(e);
        } finally {
            //em.close();
        }
        return result;
    }

    @Override
    public STProjectInstanceINIndicator getINByActRef(ObjectReference actRef) {
        List ins = (List) projectINIndicatorDao.findByPlanActivityReference(actRef);
        return (STProjectInstanceINIndicator) ins.get(0);
    }

    @Override
    public STProjectInstanceINIndicator getInByOT(STProjectInstanceOTIndicator ot,PIPlanActivity activity) {
        List result = new ArrayList();
        EntityManager em = PersistenceHelper.service.getEntityManager();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = cb.createQuery();
            Root root = criteriaQuery.from(STProjectInstanceINIndicator.class);
            Path key1 = root.get("otCode");
            Path key2 = root.get("planActivityReference").get("key");
            Path key3 = root.get("planReference").get("key");
            Predicate p1 = cb.equal(key1, ot.getCode());
            Predicate p2 = cb.equal(key2, activity.getObjectIdentifier());
            Predicate p3 = cb.equal(key3, ot.getPlan().getObjectIdentifier());
            criteriaQuery.select(root).where(p1, p2, p3);
            TypedQuery query = em.createQuery(criteriaQuery);
            result.addAll(query.getResultList());
        } finally {
            //em.close();
        }
        if (result.size() > 0) {
            return (STProjectInstanceINIndicator) result.get(0);
        }
        return null;
    }

    @Override
    public Object getDataByProjectIdAndUserId(String projectId, String userId) throws PIException {
        String projectOid = PIProject.class.getName() + ":" + projectId;
        String userOid = PIUser.class.getName() + ":" + userId;
        ReferenceFactory referenceFactory = new ReferenceFactory();
        PIUser user = (PIUser) referenceFactory.getReference(userOid).getObject();
        PIProject project = (PIProject) referenceFactory.getReference(projectOid).getObject();

        List<PIResourceAssignment> assignments = (List<PIResourceAssignment>) PIAssignmentHelper.service.getResourceAssignmentsByUser(user, project).persistableCollection();
        List planactivities = new ArrayList();
        if (assignments.size() > 0) {
            for (PIResourceAssignment assignment : assignments) {
                PIPlannable plannable = assignment.getPlannable();
                planactivities.add(plannable);
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        if (planactivities.size() > 0) {
            for (Object planable : planactivities) {
                if (planable instanceof PIPlanActivity) {
                    result.add(getAllDataByAct((PIPlanActivity) planable));
                }
            }
        }
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);

    }
}