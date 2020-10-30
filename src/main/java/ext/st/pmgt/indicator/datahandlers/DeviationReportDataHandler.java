package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.doc.model.PIDocument;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIRole;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.select.SelectElement;
import com.pisx.tundra.netfactory.mvc.handler.DataHandlerHelper;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.misc.Option;
import com.pisx.tundra.pmgt.cost.model.PIRoleRate;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.resource.PIResourceHelper;
import com.pisx.tundra.pmgt.resource.model.PIObs;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STDeviation;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DeviationReportDataHandler
 * @Description:
 * @Author hma
 * @Date 2020/10/30
 * @Version V1.0
 **/
public class DeviationReportDataHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {


        STProjectInstanceOTIndicator ot = null;
        List<STDeviation> deviations = null;
        if (datum != null && datum instanceof STProjectInstanceOTIndicator) {
            ot = (STProjectInstanceOTIndicator) datum;
            deviations = (List<STDeviation>) STIndicatorHelper.service.getDeviationByOTCode(ot.getCode());
        }

        List<Option> options = new ArrayList<>();
        if (deviations != null) {
            for (STDeviation deviation : deviations) {
                Option option = new Option().setLabel(deviation.getOid()).setValue(NumberFormat.getPercentInstance().format(deviation.getValue()));
                options.add(option);
            }
        }

        SelectElement selectElement0 = SelectElement.instance(columnName);
        selectElement0.setOptions(options);
        if (ot != null) {
            selectElement0.setDefaultOption(new Option().setLabel(ot.getOid()).setValue(NumberFormat.getPercentInstance().format(ot.getDeviationReport())));
        }
        selectElement0.attribute(elementAttribute -> elementAttribute.addStyle("width:150px;"));


        return selectElement0;
//        }
//        return DataHandlerHelper.getDefaultDataValue(columnName, datum, params);
    }
}