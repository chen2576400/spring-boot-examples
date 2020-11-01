package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.project.model.PIPmgtBaselineType;

/**
 * @ClassName PERTWizardBulder
 * @Description:
 * @Author hma
 * @Date 2020/10/21
 * @Version V1.0
 **/
public class ReportWizardBulder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("reportWizard");

        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("reportStep");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setPrimaryClassName(PIPmgtBaselineType.class);
            layout.setId("reportLayout");
            step.addLayout(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}