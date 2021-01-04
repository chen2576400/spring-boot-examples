package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
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
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

public class AddPreRiskWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        PIProject project = null;
        if (sourceObject instanceof STProjectRisk) {
            project = ((STProjectRisk) sourceObject).getProject();
        } else if (sourceObject instanceof PIProject) {
            project = (PIProject) sourceObject;
        }
        Collection projectRisks = STRiskHelper.service.getProjectRisks(project);
        if (!CollectionUtils.isEmpty(projectRisks)) {
            List<STProjectRisk> filterRisks = getFilterRisks(projectRisks, sourceObject);
            return filterRisks;
        }
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("addPreRiskBuilder");
        tableConfig.setEntities(componentData);
        tableConfig.enableSearch();
        tableConfig.enableSelect();
        tableConfig.enablePaginate(false);
        tableConfig.setPrimaryObjectType(STProjectRisk.class);
        tableConfig.setTableTitle("风险列表");

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("riskName");
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("riskCode");
        tableConfig.addColumn(column2);


        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("addPreRiskWizard");
        StepConfig step = wizardConfig.newStep();
        step.setId("addPreRiskstep");
        step.children(tableConfig);

        return wizardConfig;
    }

    private List<STProjectRisk> getFilterRisks(Collection collection, Persistable sourceObject) {
        List<STProjectRisk> riskList = (List<STProjectRisk>) collection;
        if (sourceObject instanceof STProjectRisk) {
            STProjectRisk risk = (STProjectRisk) sourceObject;
            riskList.removeIf(stProjectRisk -> stProjectRisk.getRiskName().equals(risk.getRiskName()));
            return riskList;
        }
        return  null;
    }
}
