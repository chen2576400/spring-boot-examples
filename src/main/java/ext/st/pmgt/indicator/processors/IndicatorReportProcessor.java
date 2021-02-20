package ext.st.pmgt.indicator.processors;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.PIReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.SerializableCloner;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.assignment.PIAssignmentHelper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.dingTalk.DingTalkUtils;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName IndicatorReportProcessor
 * @Description:
 * @Author hma
 * @Date 2020/10/30
 * @Version V1.0
 **/
@Component
public class IndicatorReportProcessor extends DefaultObjectFormProcessor {

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
//        boolean flag = havePrivilege(params);
//        if (!flag) {//资源没有分配到任务 无法汇报指标
//            return new ResponseWrapper(ResponseWrapper.FAILED, "没有汇报的权限！", null);
//        }
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        PIPlanDeliverable deliverable = null;
        if (sourceObject instanceof PIPlanDeliverable) {
            deliverable = (PIPlanDeliverable) sourceObject;
        }

        JSONObject versionObj = (JSONObject) params.getNfCommandBean().getComponentData("indicator_report_layout2").getLayoutFields().get("currentVersion");
        String currentVersion = versionObj.get("value").toString();
        PIReference reference = new ReferenceFactory().getReference(currentVersion);

        if (!reference.equals(deliverable.getSubjectReference())) {//版本发生改变
            deliverable.setSubjectReference((ObjectReference) reference);
            PersistenceHelper.service.save(deliverable);
            List<STProjectInstanceOTIndicator> changedOT = getUpdatedOT(params);//第四部修改过的ot
            if (changedOT.size() > 0) {//版本变了，ot指标也进行了修改,按照修改之后的更新ot指标
                for (STProjectInstanceOTIndicator newOT : changedOT) {
                    newOT.setCompletionStatus(newOT.getCompletionStatus() + 1);//更新过版本之后，完成状态+1
                    newOT.setPlanDeliverableReference(ObjectReference.newObjectReference(deliverable));
                }

                PersistenceHelper.service.save(changedOT);
            } else {//版本变了，没有修改指标，那么需要将最新的指标全部复制一份保存
                List<STProjectInstanceOTIndicator> newOT1 = updateLatestOt(params);
                if (newOT1.size() > 0) {
                    for (STProjectInstanceOTIndicator newOT : newOT1) {
                        newOT.setCompletionStatus(newOT.getCompletionStatus() + 1);
                        newOT.setPlanDeliverableReference(ObjectReference.newObjectReference(deliverable));
                    }
                    PersistenceHelper.service.save(newOT1);
                }
            }
        } else {//版本未改变
            List<STProjectInstanceOTIndicator> changedOT = getUpdatedOT(params);
            if (changedOT.size() > 0) { //ot发生更改
                for (STProjectInstanceOTIndicator newOT : changedOT) {
                    newOT.setCompletionStatus(newOT.getCompletionStatus() + 1);
                    newOT.setPlanDeliverableReference(ObjectReference.newObjectReference(deliverable));
                }
                PersistenceHelper.service.save(changedOT);
            }

        }

//        return new ResponseWrapper(ResponseWrapper.PAGE_FLUSH, "汇报成功！", null);
        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "汇报成功！", null);
    }

    private List<STProjectInstanceOTIndicator> getUpdatedOT(ComponentParams params) throws PIException {
        List<STProjectInstanceOTIndicator> result = new ArrayList<>();
        List<Map<String, Object>> tableRows = params.getNfCommandBean().getComponentData("o_t_table").getTableRows();
        for (Map<String, Object> tableRow : tableRows) {
            ReferenceFactory factory = new ReferenceFactory();
            STProjectInstanceOTIndicator ot = (STProjectInstanceOTIndicator) factory.getReference(tableRow.get("pi_row_key").toString()).getObject();
            JSONObject deviationObj = (JSONObject) tableRow.get("deviationReport");
            JSONObject difficultyObj = (JSONObject) tableRow.get("difficultyReport");
            String deviationReport = deviationObj.get("value").toString();
            String difficultyReport = difficultyObj.get("value").toString();

            //2.汇报偏差，汇报困难度发生改变
            if (!ot.getDeviationReport().toString().equals(deviationReport) || !ot.getDifficultyReport().toString().equals(difficultyReport)) {

                try {
                    STProjectInstanceOTIndicator newOT = (STProjectInstanceOTIndicator) SerializableCloner.copy(ot);
                    newOT.getObjectIdentifier().setId(0L);
                    newOT.getPersistInfo().setPersisted(Boolean.FALSE);

                    newOT.setDeviationReport(Double.valueOf(deviationReport));
                    newOT.setDifficultyReport(Double.valueOf(difficultyReport));
                    newOT.setReportTime(new Timestamp(System.currentTimeMillis()));
                    newOT.setReporter(SessionHelper.service.getPrincipalReference());
                    // 更新汇报差异
                    STIndicatorHelper.service.saveSTProjectIndicatorReportDifference(newOT);

                    //发送钉钉消息
                    PIPlanActivity activity = (PIPlanActivity) newOT.getPlanActivity();
                    Set activitySet = new HashSet();
                    Set<PIUser> userSet = new HashSet();
                    List<STProjectInstanceINIndicator> inByOTAndPlan = (List<STProjectInstanceINIndicator>) STIndicatorHelper.service.getInByOTAndPlan(newOT, newOT.getPlan());
                    if (!CollectionUtils.isEmpty(inByOTAndPlan)) {
                        for (STProjectInstanceINIndicator stProjectInstanceINIndicator : inByOTAndPlan) {
                            activitySet.add(stProjectInstanceINIndicator.getPlanActivity());
                        }
                    }
                    if (!CollectionUtils.isEmpty(activitySet)) {
                        for (Object o : activitySet) {
                            List<PIResourceAssignment> resourceAssignments = (List<PIResourceAssignment>) PIAssignmentHelper.service.getResourceAssignments((PIPlanActivity) o);
                            if (!CollectionUtils.isEmpty(resourceAssignments)) {
                                for (PIResourceAssignment resourceAssignment : resourceAssignments) {
                                    userSet.add(resourceAssignment.getRsrc().getUser());
                                }
                            }
                        }
                    }
                    //得到需要发送消息的UseridList
                    StringBuffer sb = new StringBuffer();
                    if (!CollectionUtils.isEmpty(userSet)) {
                        for (PIUser user : userSet) {
                            try {
                                sb.append("," + DingTalkUtils.getUseridBymobile(user.getTelephone()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    String userid = sb.toString().substring(1);

                    //发送消息
                    try {
                        PIUser piUser = (PIUser) SessionHelper.service.getPrincipalReference().getObject();
                        String message = activity.getName() + "的输出指标：" + newOT.getCode() + "，指标汇报完成";
                        List list1 = new ArrayList();
                        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
                        form.setKey("汇报人:");
                        form.setValue(piUser.getFullName());
                        list1.add(form);
                        DingTalkUtils.sendOAMessage(userid, false, "OT指标汇报", "OT指标汇报",
                                list1, message, null, null, "重汽精细化管理平台");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    result.add(newOT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    private List<STProjectInstanceOTIndicator> updateLatestOt(ComponentParams params) throws PIException {
        List<STProjectInstanceOTIndicator> result = new ArrayList<>();
        List<Map<String, Object>> tableRows = params.getNfCommandBean().getComponentData("o_t_table").getTableRows();
        for (Map<String, Object> tableRow : tableRows) {
            ReferenceFactory factory = new ReferenceFactory();
            STProjectInstanceOTIndicator ot = (STProjectInstanceOTIndicator) factory.getReference(tableRow.get("pi_row_key").toString()).getObject();
            try {
                STProjectInstanceOTIndicator newOT = (STProjectInstanceOTIndicator) SerializableCloner.copy(ot);
                newOT.getObjectIdentifier().setId(0L);
                newOT.getPersistInfo().setPersisted(Boolean.FALSE);

                newOT.setReportTime(new Timestamp(System.currentTimeMillis()));
                newOT.setReporter(SessionHelper.service.getPrincipalReference());
                result.add(newOT);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private boolean havePrivilege(ComponentParams params) throws PIException {
        String[] parentPageOids = params.getParamMap().get("parentPageOid");
        if (parentPageOids != null && parentPageOids.length > 0) {
            PIUser currentUser = (PIUser) SessionHelper.service.getPrincipal();
            String parentPageOid = parentPageOids[0];
            PIPlanActivity activity = (PIPlanActivity) new ReferenceFactory().getReference(parentPageOid).getObject();
            List<PIResourceAssignment> assignments = (List) PIAssignmentHelper.service.getResourceAssignments(activity);
            List<PIUser> users = assignments.stream().map(item -> item.getRsrc().getUser()).collect(Collectors.toList());
            if (!users.contains(currentUser)) {//资源没有分配到任务 无法汇报指标
                return false;
            }
        }
        return true;
    }


}