package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import ext.st.pmgt.indicator.datahandlers.ExpectedFinishTimeDateHandler;
import ext.st.pmgt.indicator.datahandlers.TimeDataHandler;
import ext.st.pmgt.indicator.model.STExpectedFinishTime;

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
//        return params.getNfCommandBean().getSourceObject();
        return null;
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
            layout.setTitle("汇报预计完成时间");

            layout.addField("expectedFinishTime",new TimeDataHandler());
            step.addLayout(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}