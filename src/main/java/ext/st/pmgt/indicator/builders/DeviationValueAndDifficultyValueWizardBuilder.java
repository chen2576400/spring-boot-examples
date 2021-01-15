package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import ext.st.pmgt.indicator.datahandlers.StandardDeviationValuePickerHandler;
import ext.st.pmgt.indicator.datahandlers.StandardDifficultyValuePickerHandler;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;

public class DeviationValueAndDifficultyValueWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("deviationValueAndDifficultyValueWizard");
        try {
        StepConfig step = wizardConfig.newStep();
        step.setId("deviationValueAndDifficultyValueWizardStep");
        LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
        layout.setPrimaryClass(STProjectInstanceOTIndicator.class);
        layout.setId("adeviationValueAndDifficultyValueLayout1");
        layout.addField("standardDeviationValue", new StandardDeviationValuePickerHandler())
                .addField("standardDifficultyValue", new StandardDifficultyValuePickerHandler());
        step.addLayout(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}
