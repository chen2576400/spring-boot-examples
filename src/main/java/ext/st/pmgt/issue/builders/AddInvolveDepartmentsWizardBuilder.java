package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.access.PmgtAccessHelper;
import com.pisx.tundra.pmgt.resource.PIResourceHelper;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;

/**
 * 添加相关设计部门
 */
public class AddInvolveDepartmentsWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Collection collection=new HashSet();
        Collection orgContainers = PIResourceHelper.service.getOrgContainers();
        for (Object container: orgContainers){
            OrgContainer orgContainer=(OrgContainer)container;
            Collection  orgContainerGroups= PmgtAccessHelper.service.getOrgContainerGroups(orgContainer);
            if(!CollectionUtils.isEmpty(orgContainerGroups)){
                collection.addAll(orgContainerGroups);
            }
        }

        return collection;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("addInvolveDepartmentsBuilder");
        tableConfig.setEntities(componentData);
        tableConfig.enableSelect();
        tableConfig.enableSearch();
        tableConfig.setPrimaryObjectType(PIGroup.class);
        tableConfig.setTableTitle("部门列表");
        tableConfig.disablePaginate();
//        tableConfig.setSingleSelect(true);//单选

        ColumnConfig column0 = componentConfigFactory.newColumnConfig();
        column0.setLabel("编号");
        column0.setName("objectIdentifier.id");
        column0.enableSort();
        column0.haveInfoPageLink();
        tableConfig.addColumn(column0);

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        // column1.setLabel("名称");
        column1.setName("name");
        column1.enableSort();
        column1.haveInfoPageLink();
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setLabel("上下文");
        column2.setName("container.name");
        column2.enableSort();
        tableConfig.addColumn(column2);

        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("addInvolveDepartmentsWizard");
        StepConfig step = wizardConfig.newStep();
        step.setId("involveDepartmentstep");
        step.children(tableConfig);

        return wizardConfig;
    }
}
