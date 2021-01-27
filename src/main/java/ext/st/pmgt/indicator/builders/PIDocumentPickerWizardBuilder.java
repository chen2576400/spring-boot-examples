package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.doc.model.PIDocument;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.objectpicker.ObjectPickerConfig;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;

public class PIDocumentPickerWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        ObjectPickerConfig objectPickerConfig = componentConfigFactory.newObjectPickerConfig(params);
        objectPickerConfig.setId("documentPicker");
        objectPickerConfig.setTitle("文档搜索");
        objectPickerConfig.setTableSingleSelect(true);
        objectPickerConfig.setSearchObjectType(PIDocument.class);
        return objectPickerConfig;
    }
}
