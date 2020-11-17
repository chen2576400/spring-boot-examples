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
import ext.st.pmgt.indicator.datahandlers.TimeDataHandler;

/**
 * @ClassName TimeRangeWizardBuilder
 * @Description:
 * @Author hma
 * @Date 2020/11/17
 * @Version V1.0
 **/
public class TimeRangeWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("timeRangeWizard");

        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("timeRangeStep");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setTitle("时间范围选择");
            layout.addField("startTime","开始时间",new TimeDataHandler())
                    .addField("endTime","结束时间",new TimeDataHandler());
            step.addLayout(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}