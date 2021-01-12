package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.team.ContainerTeamHelper;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeam;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeamManaged;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.*;
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

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;

public class ProjectManagerUserTableWizardBulider extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Collection<Persistable> persistables=new HashSet<>();
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        PIContainer piContainer=null;
        if (sourceObject instanceof PIProject) {
            piContainer=((PIProject) sourceObject).getContainer();
            ContainerTeam containerTeam= ContainerTeamHelper.service.getContainerTeam((ContainerTeamManaged) piContainer);
            PIRole role= OrgHelper.service.getRoleByCode("yfdb",piContainer.getContainerReference());
            Enumeration teamPrincipalTarget = containerTeam.getPrincipalTarget(role);
            while (teamPrincipalTarget.hasMoreElements()){
                PIPrincipalReference piPrincipalReference = (PIPrincipalReference) teamPrincipalTarget.nextElement();
                PIGroup group = (PIGroup) piPrincipalReference.getObject();
                Collection<Persistable> roleB = PersistenceHelper.service.navigate(group, "roleB", MembershipLink.class, true);
                persistables.addAll(roleB);
            }
        }
        return persistables;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("projectManagerUserTableWizard");
        StepConfig stepConfig = wizardConfig.newStep();
        stepConfig.children(getTableConfig(componentData,params,componentConfigFactory));
        return wizardConfig;

    }



    private TableConfig getTableConfig(Object componentData, ComponentParams params, ComponentConfigFactory componentConfigFactory){
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("projectManagerUserTable");
        tableConfig.setTableTitle("筛选结果");
        tableConfig.setEntities(componentData);
        tableConfig.enableSearch();
        tableConfig.enableSelect();
        tableConfig.disablePaginate();
        tableConfig.setSingleSelect(true);//单选
        tableConfig.setPrimaryObjectType(PIUser.class);

        ColumnConfig column1= componentConfigFactory.newColumnConfig();
        column1.setName("fullName");
        column1.enableSort();
        tableConfig.addColumn(column1);

        ColumnConfig column2= componentConfigFactory.newColumnConfig();
        column2.setName("name");
        column2.enableSort();
        tableConfig.addColumn(column2);

        ColumnConfig column3= componentConfigFactory.newColumnConfig();
        column3.setName("email");
        column3.enableSort();
        tableConfig.addColumn(column3);

        ColumnConfig column4= componentConfigFactory.newColumnConfig();
        column4.setName("organizationReference");
        column4.enableSort();
        tableConfig.addColumn(column4);
        return tableConfig;

    }
}
