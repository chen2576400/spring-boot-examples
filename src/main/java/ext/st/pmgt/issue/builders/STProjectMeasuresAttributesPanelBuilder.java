package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.attributePanel.AttributePanelConfig;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.util.misc.ComponentException;
import ext.st.pmgt.issue.model.STProjectMeasures;

public class STProjectMeasuresAttributesPanelBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Object source = params.getNfCommandBean().getSourceObject();
        return source;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws ComponentException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        AttributePanelConfig attributePanelConfig = componentConfigFactory.newAttributePanelConfig(params);

//        //分割线
//        DivElement line = DivElement.instance();
//        line.attribute(elementAttribute -> elementAttribute.setStyle("height: 1px;border-top: 1px solid #ddd;text-align: center;"));

        try {
            attributePanelConfig.setId("PIProjectMeasures_attribute_panel00");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setEntity((Persistable) componentData);
            layout.setPrimaryClassName(STProjectMeasures.class);
            layout.setTitle("属性");
            layout.addField("name", 1)
                    .addField("involveGroup.name","涉及部门", 2)
                    .addField("precaution", 3)
                    .addField("confirmStatus", 4)
                    .addField("involveGroupStatus", 5)
                    .addField("dutyUser.fullName","责任人", 6)
                    .addField("persistInfo.createStamp","创建日期", 7)
            ;
            layout.readonly();
//            layout.addElement(line);
            attributePanelConfig.readonly();
            attributePanelConfig.addComponentConfig(layout, 1);
            attributePanelConfig.addComponentConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attributePanelConfig;
    }
}
