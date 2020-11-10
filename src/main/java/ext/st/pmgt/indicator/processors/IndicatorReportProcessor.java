package ext.st.pmgt.indicator.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.*;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.SerializableCloner;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
                }
                PersistenceHelper.service.save(changedOT);
            } else {//版本变了，没有修改指标，那么需要将最新的指标全部复制一份保存
                List<STProjectInstanceOTIndicator> newOT1 = updateLatestOt(params);
                if (newOT1.size() > 0) {
                    for (STProjectInstanceOTIndicator newOT : newOT1) {
                        newOT.setCompletionStatus(newOT.getCompletionStatus() + 1);
                    }
                    PersistenceHelper.service.save(newOT1);
                }
            }
        } else {//版本未改变
            List<STProjectInstanceOTIndicator> changedOT = getUpdatedOT(params);
            if (changedOT.size() > 0) { //ot发生更改
                for (STProjectInstanceOTIndicator newOT : changedOT) {
                    newOT.setCompletionStatus(newOT.getCompletionStatus() + 1);
                }
                PersistenceHelper.service.save(changedOT);
            }

        }


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


}