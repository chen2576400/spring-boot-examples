package ext.st.pmgt.indicator.datahandlers;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.datepicker.DatePickerAttribute;
import com.pisx.tundra.netfactory.mvc.components.datepicker.DatePickerElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;

/**
 * @ClassName TimeDataHandler
 * @Description:
 * @Author hma
 * @Date 2020/11/17
 * @Version V1.0
 **/
public class TimeDataHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        DatePickerElement element = DatePickerElement.instance(columnName, DatePickerAttribute.DateType.Date);
        element.attribute(elementAttribute -> elementAttribute.addStyle("width:150px"));
        element.setDefaultItem(null, DatePickerElement.Pattern.yyyyMMdd);
        return element;
    }
}