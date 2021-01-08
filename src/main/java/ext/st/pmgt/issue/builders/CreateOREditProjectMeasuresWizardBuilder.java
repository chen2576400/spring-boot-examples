package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import ext.st.pmgt.issue.datahandlers.DutyGroupDataHandler;
import ext.st.pmgt.issue.datahandlers.UserPickerExpandDataHandler;
import ext.st.pmgt.issue.model.STProjectMeasures;

public class CreateOREditProjectMeasuresWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("createProjectMeasuresWizard");
        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("createProjectMeasuresWizardStep1");

            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            STProjectMeasures measures = null;
            if (componentData instanceof STProjectMeasures) {//编辑
                measures = (STProjectMeasures) componentData;
            }
            //新建，初始化值
            if (measures == null) {
                measures = new STProjectMeasures();
            }
            layout.setEntity(measures);
            layout.setPrimaryClassName(STProjectMeasures.class);
            layout.setId("createProjectMeasuresLayout");
            layout.setTitle("创建风险措施");
            layout.addField("name")
                    .addField("involveGroupReference", new DutyGroupDataHandler())
                    .addField("precaution")
                    .addField("confirmStatus")
                    .addField("involveGroupStatus")
                    .addField("dutyUserReference", new UserPickerExpandDataHandler())
            ;
            step.addLayout(layout);

            StepConfig stepConfig2 = wizardConfig.newStep();
            stepConfig2.setId("createProjectMeasureStep2");
            stepConfig2.setTitle("设置附件");
            stepConfig2.setStepAction("attachments", "createOrEditAttachments");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}
