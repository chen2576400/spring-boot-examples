package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.OrgContainer;
import com.pisx.tundra.foundation.org.model.MembershipLink;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.session.SessionHelper;
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
import com.pisx.tundra.pmgt.access.resources.accessResource;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 风险提出问题部门展示
 */
public class ProjectRiskProposingGroupTableBulider extends AbstractComponentBuilder {

    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIUser currentUser = null;      //(PIUser) SessionHelper.service.getPrincipal();//当前用户
        {
            String s = "com.pisx.tundra.foundation.org.model.PIUser:49312";//测试用户
            ObjectIdentifier objectIdentifier = new ObjectIdentifier(s);
            ObjectReference objectReference = ObjectReference.newObjectReference(objectIdentifier);
            if (objectReference != null) {
                currentUser = (PIUser) objectReference.getObject();
            }
        }
        //  parm1查询条件  parm2查询对象   parm3 link表  parm4   fasle(查询的是link对象) true或者不写差的是parm2对象
        Collection qr = PersistenceHelper.service.navigate(currentUser, "roleA", MembershipLink.class,true);
        List<PIGroup> groups = null;
        if (!CollectionUtils.isEmpty(qr)) {
            groups = (List) qr;
            groups.removeIf(piGroup -> piGroup.getInternal() == true);//移除
        }
        if (!CollectionUtils.isEmpty(groups)){
            List<Row> rowList = new ArrayList<>();
            Iterator iter = groups.iterator();
            while (iter.hasNext()) {
                PIGroup group = (PIGroup) iter.next();
                Row row = new Row();
                row.setRowSingleEntity(group);
                row.put("name", group.getName());
                row.put("id", group.getObjectIdentifier().getId());
                row.put("containerReference", ((OrgContainer) group.getContainerReference().getObject()).getOrganizationName());
                rowList.add(row);
            }
            return rowList;
        }
          return  null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("ProposingDepartmentPickerTableWizard");
        StepConfig stepConfig = wizardConfig.newStep();
        stepConfig.children(getTableConfig(componentData, params, componentConfigFactory));
        return wizardConfig;
    }

    private TableConfig getTableConfig(Object componentData, ComponentParams params, ComponentConfigFactory componentConfigFactory) {
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("groupsPickerTableBuilder");
        tableConfig.setEntities(componentData);
        tableConfig.enableSelect();
        tableConfig.enableSearch();
        tableConfig.setPrimaryObjectType(PIGroup.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(accessResource.class.getName(), "GROUPS_TABLE_TITLE", null, params.getLocale()));
        tableConfig.disablePaginate();
        tableConfig.setSingleSelect(true);//单选

        ColumnConfig column0 = componentConfigFactory.newColumnConfig();
        column0.setLabel("编号");
        column0.setName("id");
        column0.enableSort();
        column0.haveInfoPageLink();
        tableConfig.addColumn(column0);


        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
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
