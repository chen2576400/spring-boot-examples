package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.content.model.DataFormat;
import com.pisx.tundra.foundation.enterprise.model.RevisionControlled;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.actionmodel.NfAction;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.img.ImgElement;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.mvc.components.select.SelectElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.action.NfActionHelper;
import com.pisx.tundra.netfactory.util.misc.Option;
import com.pisx.tundra.netfactory.util.misc.URLFactory;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STDeviation;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RatingReportDataHandler extends DefaultDataHandler {

    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        List<Option> deviationOptions = new ArrayList<>();
        String code = null;
        if (params.getNfCommandBean().getSourceObject() instanceof STProjectInstanceOTIndicator) {
            code = ((STProjectInstanceOTIndicator) params.getNfCommandBean().getSourceObject()).getCode();
        }
        if (params.getNfCommandBean().getSourceObject() instanceof STProjectInstanceINIndicator) {
            code = ((STProjectInstanceINIndicator) params.getNfCommandBean().getSourceObject()).getOtCode();
        }
        List<STDeviation> deviations = (List) STIndicatorHelper.service.getDeviationByOTCode(code);
        if (deviations.size() > 0) {
            deviationOptions = deviations.stream().map(item -> {
                return new Option().setLabel(item.getDescription()).setValue(item.getValue());
            }).collect(Collectors.toList());
        }

        SelectElement selectElement0 = SelectElement.instance(columnName);
        selectElement0.setOptions(deviationOptions);
        if (deviationOptions.size() > 0) {
            selectElement0.setDefaultOption(deviationOptions.get(0));
        }
        selectElement0.attribute(elementAttribute -> elementAttribute.addStyle("width:300px;"));
        return selectElement0;

    }

}