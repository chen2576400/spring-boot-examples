package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.input.InputElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;

public class DefaultWeightValueDataHandle extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        InputElement inputElement =InputElement.instance(columnName);
        inputElement.setDefaultItem("1");
        inputElement.attribute(attr->attr.addStyle("width:150px;"));
        return inputElement;
    }
}
