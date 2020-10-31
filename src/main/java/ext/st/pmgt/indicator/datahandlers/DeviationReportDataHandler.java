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

        List<Option> options = new ArrayList<>();
        SelectElement selectElement0 = SelectElement.instance(columnName);
        selectElement0.setOptions(options);
        selectElement0.attribute(elementAttribute -> elementAttribute.addStyle("width:100px;"));
        return selectElement0;

    }
}