package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.objectpicker.ObjectPickerConfig;

/**
 * @author: Mr.Chen
 * @create: 2021-01-25 16:36
 **/
public class STUserPickerWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        ObjectPickerConfig objectPickerConfig = componentConfigFactory.newObjectPickerConfig(params);
        objectPickerConfig.setId("userPicker");
        objectPickerConfig.setTitle("添加用户");
        objectPickerConfig.setTableSingleSelect(true);
        objectPickerConfig.setSearchObjectType(PIUser.class);
        return objectPickerConfig;
    }
}
