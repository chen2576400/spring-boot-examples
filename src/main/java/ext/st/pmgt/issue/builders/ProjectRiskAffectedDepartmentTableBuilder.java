package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.workflow.util.WorkflowUtil;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectIssueInvolveGroupLink;
import ext.st.pmgt.issue.model.STProjectRisk;
import ext.st.pmgt.issue.model.STProjectRiskAffectedGroupLink;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectRiskAffectedDepartmentTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectRisk stProjectRisk = null;
        if (sourceObject instanceof STProjectRisk) {
            stProjectRisk = (STProjectRisk) sourceObject;
            if (stProjectRisk == null) {
                return null;
            }
        }
        Collection collection = STRiskHelper.linkService.findByRoleAObjectRef(ObjectReference.newObjectReference(stProjectRisk));
        List<PIGroup> piGroups = piGroups(collection);
        return piGroups;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        if (!isSelect(params)){
           return  null;
        }
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("ProjectRiskAffectedDepartmentTable");
        tableConfig.setPrimaryObjectType(PIGroup.class);
        tableConfig.setTableTitle("标题");
        tableConfig.enableSelect();
        tableConfig.setSingleSelect(false);//true为单选radio false为多选


        tableConfig.setToolbarActionModel("AffectedDepartmentDesignToolbarSet");//操作按钮
        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("name");
        column1.setLabel("名称");
        tableConfig.addColumn(column1);

        return tableConfig;
    }


    private List<PIGroup> piGroups(Collection collection) {
        if (collection.isEmpty()) return null;
        List<STProjectRiskAffectedGroupLink> groupLinks = (List<STProjectRiskAffectedGroupLink>) collection;
        List<PIGroup> piGroups = groupLinks.stream().map(stProjectRiskAffectedGroupLink -> {
            return stProjectRiskAffectedGroupLink.getRoleBObject();
        }).collect(Collectors.toList());
        return piGroups;
    }
    
    private  boolean  isSelect(ComponentParams params) throws PIException {
        String sourceOid =  params.getNfCommandBean().getSourceOid().toString();
        Persistable persistable = WorkflowUtil.getObjectByOid(sourceOid);
        if (persistable instanceof STProjectRisk){
              return true;
        }
        return  false;
    }
}
