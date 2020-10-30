package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.select.SelectElement;
import com.pisx.tundra.netfactory.mvc.handler.DataHandlerHelper;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.misc.Option;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STDeviation;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DifficultyReportDataHandler
 * @Description:
 * @Author hma
 * @Date 2020/10/30
 * @Version V1.0
 **/
public class DifficultyReportDataHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        if (sourceObject instanceof PIPlanDeliverable) {
            STProjectInstanceOTIndicator ot = null;
            if (datum != null && datum instanceof STProjectInstanceOTIndicator) {
                ot = (STProjectInstanceOTIndicator) datum;
            }

            List<STDeviation> deviations = (List<STDeviation>) STIndicatorHelper.service.getDifficultyByOTCode(ot.getCode());
            List<Option> options = new ArrayList<>();
            for (STDeviation deviation : deviations) {
                Option option = new Option().setLabel(deviation.getOid()).setValue(NumberFormat.getPercentInstance().format(deviation.getValue()));
                options.add(option);
            }

            SelectElement selectElement0 = SelectElement.instance(columnName);
            selectElement0.setOptions(options);
            selectElement0.setDefaultOption(new Option().setValue(NumberFormat.getPercentInstance().format(ot.getDeviationReport())));
//            selectElement0.attribute(elementAttribute -> elementAttribute.setStyle("cursor: pointer;display:inline-block;" +
//                    "padding: 3px 6px;text-align: right;width: 200px;vertical-align: middle;"));


            return selectElement0;
        }
        return DataHandlerHelper.getDefaultDataValue(columnName, datum, params);
    }
}