package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.resource.datahandlers.UserPickerDataHandler;
import com.pisx.tundra.pmgt.risk.datahandlers.ResourceDataHandler;
import com.pisx.tundra.pmgt.risk.datahandlers.RiskTypeDataHandler;
import ext.st.pmgt.issue.model.STProjectRisk;

import java.sql.Timestamp;

public class CreateOREditProjectRiskWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("createProjectRiskWizard");

        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("createProjectRiskStep1");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            STProjectRisk risk=null;
            if (componentData instanceof STProjectRisk) {//编辑
                risk=(STProjectRisk) componentData;
            }
            //新建，初始化值
            if(risk==null){
                risk=new STProjectRisk();
                risk.setAddDate(new Timestamp(System.currentTimeMillis()));
            }
            layout.setEntity(risk);
            layout.setPrimaryClassName(STProjectRisk.class);
            layout.setId("createRiskTypeLayout");
            layout.setTitle("attribute");

            layout.addField("riskName")
                    .addField("riskDescription")
                    .addField("riskToType")
                    .addField("identifiedByReference",new UserPickerDataHandler())
                    .addField("riskTypeReference",new RiskTypeDataHandler())
                    .addField("riskCause")
                    .addField("riskEffect")
                    .addField("rsrcReference",new ResourceDataHandler())
                    .addField("notes")
                    .addField("addDate")
                    .addField("responseType")
                    .addField("responseText")
                    .addField("preRspProbability")
                    .addField("preRspSchdProbability")
                    .addField("preRspCostProbability")
                    .addField("postRspProbability")
                    .addField("postRspSchdProbability")
                    .addField("postRspCostProbability");
            step.addLayout(layout);

            StepConfig stepConfig2 = wizardConfig.newStep();
            stepConfig2.setId("createProjectRiskStep2");
            stepConfig2.setTitle("设置附件");
            stepConfig2.setStepAction("attachments","createOrEditAttachments");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}
