package ext.st.pmgt.indicator.builders;

import com.alibaba.fastjson.JSONObject;
import com.cetc.pbi.model.CetcAttribute;
import com.cetc.pbi.model.CetcAttributeValue;
import com.cetc.pbi.model.CetcProductConfig;
import com.cetc.pbi.processors.DataTypeSwitcher;
import com.cetc.pbi.service.CetcService;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentAssistant;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.Row;
import com.pisx.tundra.netfactory.util.beans.NfCommandBean;
import com.pisx.tundra.netfactory.util.beans.NfOid;
import com.pisx.tundra.netfactory.util.misc.Collections;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.netfactory.util.misc.Strings;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STDeliverableType;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.*;

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


            for (STProjectInstanceOTIndicator ot : ots) {
                Row row = new Row();
                row.put("code", ot.getCode());
                row.put("description", ot.getDescription());
                row.put("deviationReport", NumberFormat.getPercentInstance().format(ot.getDeviationReport()));
                row.put("difficultyReport", NumberFormat.getPercentInstance().format(ot.getDifficultyReport()));
                row.put("standardDeviationValue", NumberFormat.getPercentInstance().format(ot.getStandardDeviationValue()));
                row.put("standardDifficultyValue", NumberFormat.getPercentInstance().format(ot.getStandardDifficultyValue()));
                row.put("reportTime", ot.getReportTime().toString());
                row.setRowKey(ot.getOid());
                rows.add(row);
            }


        }
        JSONObject table = ajaxData.getJSONObject("componentsData").getJSONObject("ot_step").getJSONObject("o_t_table");
        table.put("rows", rows);
        ResponseWrapper responseWrapper = new ResponseWrapper(ResponseWrapper.SUCCESS, "", ajaxData);


        return responseWrapper;
    }


}
