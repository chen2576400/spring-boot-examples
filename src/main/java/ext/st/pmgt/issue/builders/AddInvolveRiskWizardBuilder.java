package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.workflow.util.WorkflowUtil;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectRisk;

public class AddInvolveRiskWizardBuilder  extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        String sourceOid = params.getNfCommandBean().getSourceOid().getValue();
        Persistable persistable = WorkflowUtil.getObjectByOid(sourceOid);
        STProjectIssue stProjectIssue=null;
        if (persistable instanceof STProjectIssue){
            stProjectIssue= (STProjectIssue) persistable;
        }
        PIProject project = stProjectIssue.getProject();
        return STRiskHelper.service.getProjectRisks(project);
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("addInvolveRiskBuilder");
        tableConfig.setEntities(componentData);
        tableConfig.enableSelect();
        tableConfig.enableSearch();
        tableConfig.disablePaginate();
        tableConfig.setPrimaryObjectType(STProjectRisk.class);
        tableConfig.setTableTitle("风险列表");

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("riskName");
//        column1.haveInfoPageLink();
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("riskCode");
        tableConfig.addColumn(column2);



        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("addInvolveRiskWizard");
        StepConfig step = wizardConfig.newStep();
        step.setId("addInvolveRiskstep");
        step.children(tableConfig);

        return wizardConfig;
    }
}
