package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STExpectedFinishTime;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.resources.indicatorResource;

/**
 * @ClassName ReportPlanActivitySTExpectedFinishTimeWizardBuilder
 * @Description:
 * @Author LiuMX
 * @Date 2020/10/19
 * @Version V1.0
 **/
public class ReportPlanActivitySTExpectedFinishTimeWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("reportPlanActivitySTExpectedFinishTime");
        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("reportPlanActivitySTExpectedFinishTimeWizardBuilderStep1");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setPrimaryClassName(STExpectedFinishTime.class);
            layout.setId("reportPlanActivitySTExpectedFinishTimeWizardBuilderLayout");
            layout.setTitle("reportPlanActivitySTExpectedFinishTime");

            layout.addField("expectedFinishTime","预计完成时间");
            step.addLayout(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}