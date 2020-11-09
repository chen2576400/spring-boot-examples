package ext.st.pmgt.indicator.builders;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentAssistant;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.Row;
import com.pisx.tundra.netfactory.util.beans.NfCommandBean;
import com.pisx.tundra.netfactory.util.beans.NfOid;
import com.pisx.tundra.netfactory.util.misc.*;
import com.pisx.tundra.netfactory.util.misc.Collections;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STDeliverableType;
import ext.st.pmgt.indicator.model.STDeviation;
import ext.st.pmgt.indicator.model.STDifficulty;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullListAsEmpty;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;

@Component("OTTableComponentAssistant")
public class OTTableComponentAssistant implements ComponentAssistant {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public ResponseWrapper<?> execute(ComponentParams params) throws PIException {
        JSONObject ajaxData = params.getAjaxData();

        List<Row> rows = new LinkedList<>();

        if (params.getNfCommandBean().getSelectedObjects().size() > 0) {
            STDeliverableType selectDT = (STDeliverableType) params.getNfCommandBean().getSelectedObjects().get(0);
            List<STProjectInstanceOTIndicator> ots = (List) STIndicatorHelper.service.getOTByDeliverableType(selectDT);
//            ots = ots.stream().sorted(Comparator.comparing(STProjectInstanceOTIndicator::getReportTime)).collect(Collectors.toList());
//            STProjectInstanceOTIndicator ot = ots.get(ots.size() - 1);//获取修改时间最新的一条
            for (STProjectInstanceOTIndicator ot : ots) {
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
        }
        JSONObject table = ajaxData.getJSONObject("componentsData").getJSONObject("indicator_report_step4").getJSONObject("o_t_table");
        table.put("rows", rows);
        ResponseWrapper responseWrapper = new ResponseWrapper(ResponseWrapper.SUCCESS, "", ajaxData);


        return responseWrapper;
    }


}
