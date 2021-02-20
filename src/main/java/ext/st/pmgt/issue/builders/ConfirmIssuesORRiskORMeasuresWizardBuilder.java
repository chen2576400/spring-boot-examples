package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.*;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.netfactory.util.misc.AlertType;
import ext.st.pmgt.issue.datahandlers.ConfirmRadioDataHandler;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;

/**
 * @author: Mr.Chen
 * @create: 2021-01-18 14:25
 **/
public class ConfirmIssuesORRiskORMeasuresWizardBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        Class<? extends Persistable> beanClass = null;

        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("confirmProjectIssueWizard");

        if (componentData instanceof STProjectIssue) {
            beanClass = STProjectIssue.class;
            STProjectIssue issue = (STProjectIssue) componentData;
            if (issue.getConfirmStatus() != null) {
                String msg = issue.getConfirmStatus() == false ? "关闭" : "开启";
                NotifySupport.alert(AlertType.WARN, "该问题已被确认" + msg + "，不能再次操作");
                return wizardConfig;
            }
        } else if (componentData instanceof STProjectRisk) {
            beanClass = STProjectRisk.class;
            STProjectRisk risk = (STProjectRisk) componentData;
            if (risk.getConfirmStatus() != null) {
                String msg = risk.getConfirmStatus() == false ? "关闭" : "开启";
                NotifySupport.alert(AlertType.WARN, "该风险已被确认" + msg + "，不能再次操作");
                return wizardConfig;
            }
        } else if (componentData instanceof STProjectMeasures) {
            beanClass = STProjectMeasures.class;
            STProjectMeasures measures = (STProjectMeasures) componentData;
            PIUser piUser = (PIUser) SessionHelper.service.getPrincipal();
            if (measures.getDutyUser() == null) {
                NotifySupport.alert(AlertType.WARN, "请绑定责任人");
                return wizardConfig;
            } else if (!measures.getDutyUser().getFullName().equals(piUser.getFullName())) {
                NotifySupport.alert(AlertType.WARN, "您不是该措施责任人无权操作");
                return wizardConfig;
            } else if (measures.getConfirmStatus() != null) {
                String msg = measures.getConfirmStatus() == false ? "关闭" : "开启";
                NotifySupport.alert(AlertType.WARN, "该措施已被确认" + msg + "，不能再次操作");
                return wizardConfig;
            }
        }

        StepConfig step = wizardConfig.newStep();
        step.setId("confirmProjectIssueWizardStep1");

        LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
        layout.setId("confirmProjectIssueLayout");
        layout.setPrimaryClassName(beanClass);

        layout.addField("confirmStatus", new ConfirmRadioDataHandler());
        step.addLayout(layout);
        return wizardConfig;
    }
}
