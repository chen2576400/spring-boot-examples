package ext.st.pmgt.indicator.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
        JSONObject ajaxData = params.getAjaxData();
//        return new ResponseWrapper(ResponseWrapper.SUCCESS, "", null);

//        //判断当前登录用户和任务所属用户时否为同一人
//        PIPrincipal principal = SessionHelper.service.getPrincipal();
////        List<PIResourceAssignment> allResourceAssignments = (List) PIAssignmentHelper.service.getAllResourceAssignments(act);
////        List<PIUser> actUsers = allResourceAssignments.stream().map(item -> item.getRsrc().getUser()).collect(Collectors.toList());
////        if (!actUsers.contains(principal)) {
////            return new ResponseWrapper(ResponseWrapper.FAILED, "无法修改", null);
////        }

        List<Map<String, Object>> tableRows = (List<Map<String, Object>>) params.getAjaxData().getJSONObject("componentsData").getJSONObject("indicator_report_step4").getJSONObject("o_t_table").get("rows");

        for (Map<String, Object> tableRow : tableRows) {
            ReferenceFactory factory = new ReferenceFactory();
            STProjectInstanceOTIndicator ot = (STProjectInstanceOTIndicator) factory.getReference(tableRow.get("pi_row_key").toString()).getObject();
            JSONObject deviationObj = (JSONObject) tableRow.get("deviationReport");
            JSONObject difficultyObj = (JSONObject) tableRow.get("difficultyReport");
            String deviationReport = deviationObj.get("value").toString();
            String difficultyReport = difficultyObj.get("value").toString();

            if (!ot.getDeviationReport().toString().equals(deviationReport)||!ot.getDifficultyReport().toString().equals(difficultyReport)) {

                STProjectInstanceOTIndicator newOT = STProjectInstanceOTIndicator.newSTProjectInstanceOTIndicator();
                if (ot.getContainerReference() != null) {
                    newOT.setContainerReference(ot.getContainerReference());
                }
                if (ot.getProjectReference() != null) {
                    newOT.setProjectReference(ot.getProjectReference());
                }
                if (ot.getPlanActivityReference() != null) {
                    newOT.setPlanActivityReference(ot.getPlanActivityReference());
                }
                if (ot.getCompetenceReference() != null) {
                    newOT.setCompetenceReference(ot.getCompetenceReference());
                }
                if (ot.getPlanReference() != null) {
                    newOT.setPlanReference(ot.getPlanReference());
                }
                if (ot.getDescription() != null) {
                    newOT.setDecription(ot.getDescription());
                }
                if (ot.getDefinition() != null) {
                    newOT.setDefinition(ot.getDefinition());
                }
                if (ot.getDeliverableTypeReference() != null) {
                    newOT.setDeliverableTypeReference(ot.getDeliverableTypeReference());
                }
                if (ot.getBreadth() != null) {
                    newOT.setBreadth(ot.getBreadth());
                }
                if (ot.getCode() != null) {
                    newOT.setCode(ot.getCode());
                }
                if (ot.getCriticality() != null) {
                    newOT.setCriticality(ot.getCriticality());
                }
                if (ot.getStandardDifficultyValue() != null) {
                    newOT.setStandardDifficultyValue(ot.getStandardDifficultyValue());
                }
                if (ot.getStandardDeviationValue() != null) {
                    newOT.setStandardDeviationValue(ot.getStandardDeviationValue());
                }
                if (ot.getCompletionStatus() != null) {
                    newOT.setCompletionStatus(ot.getCompletionStatus());
                }
                if (ot.getPlanDeliverableReference() != null) {
                    newOT.setPlanDeliverableReference(ot.getPlanDeliverableReference());
                }

                newOT.setDeviationReport(Double.valueOf(deviationReport));
                newOT.setDifficultyReport(Double.valueOf(difficultyReport));
                newOT.setReportTime(new Timestamp(System.currentTimeMillis()));
                newOT.setReporter(SessionHelper.service.getPrincipalReference());
                newOT.setCreator(SessionHelper.service.getPrincipalReference());
                newOT.setCreateTimestamp(new Timestamp(System.currentTimeMillis()));
                PersistenceHelper.service.save(newOT);
            }
        }
        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "汇报成功！", null);
    }


}