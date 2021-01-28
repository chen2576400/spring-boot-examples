package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.meta.type.datahandlers.LTDTypePickerDataHandler;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.deliverable.datahandlers.PIDocumentPickerHandler;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import ext.st.pmgt.indicator.datahandlers.DeliverablePickerHandler;

public class EditDeliverableWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("editDeliverableWizard");
        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("editDeliverableWizardStep1");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setPrimaryClass(PIPlanDeliverable.class);
            layout.setId("editDeliverableLayout");
            layout.setEntity((PIPlanDeliverable)componentData);
            //layout.setTitle("编辑交付对象");
            layout.addField("name")
                    .addField("description")
                    .addField("url")
                    .addField("subjectReference",new DeliverablePickerHandler())
                    .addField("necessity");
            step.addLayout(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}
