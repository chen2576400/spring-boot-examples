package ext.st.pmgt.indicator.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.netfactory.util.misc.StringUtils;
import com.pisx.tundra.pmgt.assignment.PIAssignmentHelper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName SaveOTIndicatorProcessor
 * @Description:
 * @Author hma
 * @Date 2020/10/22
 * @Version V1.0
 **/
@Component
public class SaveOTIndicatorProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        //判断当前登录用户和任务所属用户时否为同一人
        PIPrincipal principal = SessionHelper.service.getPrincipal();
//        List<PIResourceAssignment> allResourceAssignments = (List) PIAssignmentHelper.service.getAllResourceAssignments(act);
//        List<PIUser> actUsers = allResourceAssignments.stream().map(item -> item.getRsrc().getUser()).collect(Collectors.toList());
//        if (!actUsers.contains(principal)) {
//            return new ResponseWrapper(ResponseWrapper.FAILED, "无法修改", null);
//        }

        List<Map<String, Object>> tableRows = params.getNfCommandBean().getTableRows();
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
                if (ot.getDeliverableTypeCode() != null) {
                    newOT.setDeliverableTypeCode(ot.getDeliverableTypeCode());
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
        return new ResponseWrapper(ResponseWrapper.SUCCESS, "", null);
    }
}