package ext.st.pmgt.issue.datahandlers;

import com.google.common.collect.Lists;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.radio.RadioGroupElement;
import com.pisx.tundra.netfactory.mvc.handler.DefaultDataHandler;
import com.pisx.tundra.netfactory.util.misc.Option;

/**
 * @author: Mr.Chen
 * @create: 2021-01-18 14:58
 **/
public class ConfirmRadioDataHandler extends DefaultDataHandler {
    @Override
    public Object getDataValue(String columnName, Object datum, ComponentParams params) throws PIException {
        RadioGroupElement radioGroup = RadioGroupElement.instance(columnName);
        radioGroup.setOptions(Lists.newArrayList(new Option(true, "确认"), new Option(false, "关闭")));
        radioGroup.setDefaultOption(new Option(true, "确认"));
        return radioGroup;
    }


}
