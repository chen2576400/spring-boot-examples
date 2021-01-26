package ext.st.pmgt.project.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.attributePanel.AttributePanelConfig;
import com.pisx.tundra.netfactory.mvc.components.div.DivElement;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PIProjectAttributesPanelBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIProject project = null;
        Persistable obj = params.getNfCommandBean().getSourceObject();
        if (obj instanceof PIProjectContainer) {
            project = PIProjectHelper.service.getProjectFromContainer((PIProjectContainer) obj);
        }
        //else if(obj instanceof WorkItem){
        //	WorkItem workItem=(WorkItem)obj;
        //	Persistable persistable = workItem.getPrimaryBusinessObject().getObject();
        //	if(persistable instanceof PIProject){
        //		project=(PIProject)persistable;
        //	}
        //}
        else {
            project = (PIProject) obj;
        }
        return project;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) {
        log.debug(">>>>>>buildComponentConfig(....)---componentData=" + componentData);
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        AttributePanelConfig attributePanelConfig = componentConfigFactory.newAttributePanelConfig(params);

        //分割线
        DivElement line = DivElement.instance();
        line.attribute(elementAttribute -> elementAttribute.setStyle("height: 1px;border-top: 1px solid #ddd;text-align: center;"));

        try {
            attributePanelConfig.setId("piproject_attribute_panel00");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setId("projectAttributesPanel0");
            layout.setEntity((PIProject) componentData);
            layout.setPrimaryClass(PIProject.class);
            layout.setTitle("basicAttributes");
            layout.addField("projectName").coordinate(0, 0)
                    .addField("priorityNum").coordinate(1, 0)
                    .addField("strgyPriorityNum").coordinate(2, 0)
                    .addField("riskLevel").coordinate( 3, 0)
                    .addField("calendar.calendarName", "日历").coordinate( 4, 0)
                    .addField("projectTemplate.projectName", "项目模板").coordinate( 5, 0)
                    .addField("projectGroup.name", "所属组").coordinate( 6, 0)
                    .addField("obs.name", "组织结构" ).coordinate( 7, 0)
                    .addField("projectUrl").coordinate( 8, 0)
                    .addField("checkoutUser.name", "检出者").coordinate( 9, 0)
                    .addField("description").coordinate( 10, 0)
                    .addField("state.state.stateName","状态").coordinate( 11, 0);
            layout.readonly();
//            layout.addElement(line);
            attributePanelConfig.readonly();
            attributePanelConfig.addComponentConfig(layout, 1);
            attributePanelConfig.addComponentConfig();


            LayoutConfig layout0 = componentConfigFactory.newLayoutConfig(params);
            layout0.setId("projectAttributesPanel1");
            layout0.setEntity((PIProject) componentData);
            layout0.setPrimaryClass(PIProject.class);
            layout0.setTitle("date");
            layout0.addField("planStartDate").coordinate( 0, 0)
                    .addField("planEndDate").coordinate( 1, 0)
                    .addField("forecastStartDate").coordinate( 2, 0)
                    .addField("scheduledEndDate").coordinate( 3, 0);
            layout0.readonly();
//            layout0.addElement(line);
            attributePanelConfig.readonly();
            attributePanelConfig.addComponentConfig(layout0, 2);
            attributePanelConfig.addComponentConfig();

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("<<<<<<buildComponentConfig(....)---componentData=" + componentData);
        return attributePanelConfig;
    }

}
