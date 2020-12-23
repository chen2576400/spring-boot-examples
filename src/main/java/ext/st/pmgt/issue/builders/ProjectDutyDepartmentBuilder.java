package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.Row;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.access.PmgtAccessHelper;
import com.pisx.tundra.pmgt.access.resources.accessResource;
import com.pisx.tundra.pmgt.resource.PIResourceHelper;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class ProjectDutyDepartmentBuilder extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Collection collection=new HashSet();
        Collection orgContainers = PIResourceHelper.service.getOrgContainers();
        Iterator iterator = orgContainers.iterator();
        while (iterator.hasNext()){
            OrgContainer orgContainer=(OrgContainer)iterator.next();
            Collection  orgContainerGroups= PmgtAccessHelper.service.getOrgContainerGroups(orgContainer);
            if(!CollectionUtils.isEmpty(orgContainerGroups)){
                collection.addAll(orgContainerGroups);
            }
        }
        List<Row> rowList=new ArrayList<>();
        Iterator iter = collection.iterator();
        while (iter.hasNext()){
            PIGroup group = (PIGroup) iter.next();
            Row row=new Row();
            row.setRowSingleEntity(group);
            row.put("name",group.getName());
//            row.put("id",group.getOid());
            row.put("id",group.getObjectIdentifier().getId());
            row.put("containerReference",((OrgContainer)group.getContainerReference().getObject()).getOrganizationName());
            rowList.add(row);

        }
        return rowList;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("groupsPickerTableWizard");
        StepConfig stepConfig = wizardConfig.newStep();
        stepConfig.children(getTableConfig(componentData,params,componentConfigFactory));
        return wizardConfig;
    }

    private TableConfig getTableConfig(Object componentData, ComponentParams params, ComponentConfigFactory componentConfigFactory){
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("groupsPickerTableBuilder");
        tableConfig.setEntities(componentData);
        tableConfig.enableSelect();
        tableConfig.enableSearch();
        tableConfig.setPrimaryObjectType(PIGroup.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(accessResource.class.getName(),"GROUPS_TABLE_TITLE",null,params.getLocale()));
        tableConfig.enablePaginate(false);
        tableConfig.setSingleSelect(true);//单选

        ColumnConfig column0 = componentConfigFactory.newColumnConfig();
        column0.setLabel("编号");
        column0.setName("id");
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
        //column2.setLabel("上下文");
        column2.setName("containerReference");
        column2.enableSort();
        tableConfig.addColumn(column2);

        return tableConfig;

    }
}
