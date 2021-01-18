package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.*;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.netfactory.util.misc.AlertType;
import ext.st.pmgt.issue.datahandlers.ConfirmRadioDataHandler;
import ext.st.pmgt.issue.model.STProjectIssue;

/**
 * @author: Mr.Chen
 * @create: 2021-01-18 14:25
 **/
public class ConfirmProjectIssueWizardBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {



        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("confirmProjectIssueWizard");

        if (componentData instanceof STProjectIssue){
            STProjectIssue issue=(STProjectIssue)componentData;
            if (issue.getConfirmStatus()){
                NotifySupport.alert(AlertType.WARN, "该问题已被确认开启，不能再次操作");
                return wizardConfig;
            }
        }

        StepConfig step = wizardConfig.newStep();
        step.setId("confirmProjectIssueWizardStep1");

        LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
        layout.setId("confirmProjectIssueLayout");
        layout.setPrimaryClassName(STProjectIssue.class);

        layout.addField("confirmStatus",new ConfirmRadioDataHandler());
        step.addLayout(layout);
        return wizardConfig;
    }
}
