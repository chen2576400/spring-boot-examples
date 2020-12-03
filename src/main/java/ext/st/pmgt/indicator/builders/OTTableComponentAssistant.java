package ext.st.pmgt.indicator.builders;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentAssistant;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.Row;
import com.pisx.tundra.netfactory.util.misc.*;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STDeliverableType;
import ext.st.pmgt.indicator.model.STDeviation;
import ext.st.pmgt.indicator.model.STDifficulty;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component("OTTableComponentAssistant")
public class OTTableComponentAssistant implements ComponentAssistant {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public ResponseWrapper<?> execute(ComponentParams params) throws PIException {
        JSONObject ajaxData = params.getAjaxData();
        PIPlanDeliverable deliverable = null;
        PIPlanActivity act = null;
        if (params.getNfCommandBean().getSourceObject()!=null) {
            deliverable = (PIPlanDeliverable) params.getNfCommandBean().getSourceObject();
        }
        if (deliverable!=null&&deliverable.getParent()!=null){
            act = (PIPlanActivity) deliverable.getParent();
        }
        List<Row> rows = new LinkedList<>();
        ResponseWrapper responseWrapper= null;
        List<Persistable> selectedObjects = params.getNfCommandBean().getSelectedObjects();
        if (selectedObjects!=null&&selectedObjects.size() > 0) {
            STDeliverableType selectDT = (STDeliverableType) selectedObjects.get(0);
            List<STProjectInstanceOTIndicator> ots = (List) STIndicatorHelper.service.getOTByDeliverableTypeCodeAndPlanActivity(selectDT.getCode(),act);
            if (ots.isEmpty()){
                return new ResponseWrapper(ResponseWrapper.FAILED, "未找到对应OT指标", null);
            }
            List<STProjectInstanceOTIndicator> newOts = STIndicatorHelper.service.getLatestOt(ots);
            for (STProjectInstanceOTIndicator ot : newOts) {
                NumberFormat numFormat = NumberFormat.getPercentInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Row row = new Row();
                row.put("code", ot.getCode());
                row.put("description", ot.getDescription());
                row.put("deviationReport", new Option(ot.getDeviationReport(), numFormat.format(ot.getDeviationReport())));
                row.put("difficultyReport", new Option(ot.getDifficultyReport(), numFormat.format(ot.getDifficultyReport())));
                row.put("standardDeviationValue", numFormat.format(ot.getStandardDeviationValue()));
                row.put("standardDifficultyValue", numFormat.format(ot.getStandardDifficultyValue()));
                row.put("reportTime", sdf.format(ot.getReportTime()));

                List<STDeviation> deviations = (List<STDeviation>) STIndicatorHelper.service.getDeviationByOTCode(ot.getCode());
                List<STDifficulty> difficulties = (List<STDifficulty>) STIndicatorHelper.service.getDifficultyByOTCode(ot.getCode());
                List<Option> deviationOptions = deviations.stream().map(item -> {
                    return new Option().setLabel(numFormat.format(item.getValue())).setValue(item.getValue());
                }).collect(Collectors.toList());
                List<Option> difficulteiOptions = difficulties.stream().map(item -> {
                    return new Option().setLabel(numFormat.format(item.getValue())).setValue(item.getValue());
                }).collect(Collectors.toList());


                row.put("deviationReportOptions", deviationOptions);
                row.put("difficultyReportOptions", difficulteiOptions);
                row.setRowKey(ot.getOid());
                rows.add(row);

            }
            JSONObject table = ajaxData.getJSONObject("componentsData").getJSONObject("indicator_report_step4").getJSONObject("o_t_table");
            table.put("rows", rows);
            responseWrapper = new ResponseWrapper(ResponseWrapper.CONFIRM, "", ajaxData);
        }else {
            responseWrapper = new ResponseWrapper(ResponseWrapper.FAILED, "请选择一种交付物类型！", null);
        }

        return responseWrapper;
    }

     /**
      * @MethodName:
      * @Description: 返回最新的ot
      * @Param:
      * @Return:
      * @Author: hma
      * @Date: 2020/11/10
     **/

    private List<STProjectInstanceOTIndicator> getLatestOt(List<STProjectInstanceOTIndicator> ots){
        List<STProjectInstanceOTIndicator> result = new ArrayList<>();
        Map<String, List<STProjectInstanceOTIndicator>> map = ots.stream().collect(Collectors.groupingBy(STProjectInstanceOTIndicator::getCode));
        for (Map.Entry<String, List<STProjectInstanceOTIndicator>> entry : map.entrySet()) {
            List<STProjectInstanceOTIndicator> value = entry.getValue();
            value.sort((t1, t2) -> t2.getReportTime().compareTo(t1.getReportTime()));
            result.add(value.get(0));
        }
        return result;
    }


}
