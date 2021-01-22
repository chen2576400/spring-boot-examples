package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PIArrayList;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectIssueInvolveGroupLink;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 问题相关涉及部门展示
 */
public class ProjectInvolvedDepartmentTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectIssue stProjectIssue = null;
        if (sourceObject instanceof STProjectIssue) {
            stProjectIssue = (STProjectIssue) sourceObject;
              Collection qr = PersistenceHelper.service.navigate(stProjectIssue, "roleB", STProjectIssueInvolveGroupLink.class, true);
//            Collection collection = STProjectIssueHelper.linkService.findByRoleAObjectRef(ObjectReference.newObjectReference(stProjectIssue));
//            List<PIGroup> piGroups = piGroups(collection);
            return qr;
        }
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("projectInvolvedDepartmentTable");
        tableConfig.setPrimaryObjectType(PIGroup.class);
        tableConfig.enableSelect();
        tableConfig.setSingleSelect(false);//true为单选radio false为多选
        if (isSelect(params)) {
            tableConfig.setToolbarActionModel("departmentDesignToolbarSet");//操作按钮
        }
        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("name");
        column1.haveInfoPageLink();
        tableConfig.addColumn(column1);


        ColumnConfig column3 = componentConfigFactory.newColumnConfig();
        column3.setName("persistInfo.createStamp");
        tableConfig.addColumn(column3);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("description");
        tableConfig.addColumn(column2);

        return tableConfig;
    }


//    private List<PIGroup> piGroups(Collection collection) throws PIException {
//        if (collection.isEmpty()) return null;
//        List<STProjectIssueInvolveGroupLink> groupLinks = (List<STProjectIssueInvolveGroupLink>) collection;
//
//        PIArrayList removeLists = new PIArrayList(){{addAll(groupLinks.stream().filter(groupLink -> groupLink.getRoleBObject() == null).collect(Collectors.toList()));}};
//        PersistenceHelper.service.delete(removeLists);
//
//        List<PIGroup> piGroups = groupLinks.stream().filter(groupLink -> groupLink.getRoleBObject() != null).map(stProjectIssueInvolveGroupLink -> {
//            return stProjectIssueInvolveGroupLink.getRoleBObject();
//        }).collect(Collectors.toList());
//        return piGroups;
//    }


    private boolean isSelect(ComponentParams params) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        if (persistable instanceof STProjectIssue) {
            return true;
        }
        return false;
    }
}
