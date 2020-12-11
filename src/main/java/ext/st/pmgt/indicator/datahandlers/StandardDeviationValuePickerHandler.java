package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.select.SelectElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.misc.Option;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STDeviation;
import ext.st.pmgt.indicator.model.STProjectIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StandardDeviationValuePickerHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        List<Option> deviationOptions = new ArrayList<>();
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        if (sourceObject != null) {
            if (sourceObject instanceof STProjectIndicator) {

                String code = ((STProjectIndicator) sourceObject).getCode();
                List<STDeviation> deviations = (List) STIndicatorHelper.service.getDeviationByOTCode(code);
                if (deviations.size() > 0) {
                    deviationOptions = deviations.stream().map(item -> {
                        return new Option().setLabel(item.getDescription()).setValue(item.getValue());
                    }).collect(Collectors.toList());
                }
            }
        }
        SelectElement selectElement0 = SelectElement.instance(columnName);
        selectElement0.setOptions(deviationOptions);
        if (deviationOptions.size() > 0) {
            selectElement0.setDefaultOption(deviationOptions.get(0));
        }
        selectElement0.attribute(elementAttribute -> elementAttribute.addStyle("width:200px;"));
        return selectElement0;
    }
}
