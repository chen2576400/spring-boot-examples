package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PIArrayList;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.workflow.util.WorkflowUtil;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import ext.st.pmgt.issue.STProjectIssueHelper;
import ext.st.pmgt.issue.model.STProjectIssue;
import ext.st.pmgt.issue.model.STProjectIssueInvolveGroupLink;
import ext.st.pmgt.issue.model.STProjectIssueInvolveRiskLink;
import ext.st.pmgt.issue.model.STProjectRisk;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 该问题相关风险展示列表
 */
public class ProjectRiskListTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {

        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectIssue stProjectIssue = null;
        if (sourceObject instanceof STProjectIssue) {
            stProjectIssue = (STProjectIssue) sourceObject;
            //Collection collection = STProjectIssueHelper.riskLinkService.findByRoleAObjectRef(ObjectReference.newObjectReference(stProjectIssue));
            //return riskList(collection);
            Collection qr = PersistenceHelper.service.navigate(stProjectIssue, "roleB", STProjectIssueInvolveRiskLink.class, true);
            return qr;
        }


        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {

        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("projectRiskListTableBuilder");
        tableConfig.setPrimaryObjectType(STProjectRisk.class);
        tableConfig.enableSelect();
        tableConfig.setSingleSelect(false);//true为单选radio false为多选
        if (isSelect(params)) {
            tableConfig.setToolbarActionModel("riskDesignToolbarSet");//操作按钮

        }
        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("riskName");
        column1.haveInfoPageLink();
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("riskCode");
        tableConfig.addColumn(column2);


        ColumnConfig column7 = componentConfigFactory.newColumnConfig();
        column7.setName("addDate");
        tableConfig.addColumn(column7);

        return tableConfig;
    }

    private boolean isSelect(ComponentParams params) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        if (persistable instanceof STProjectIssue) {
            return true;
        }
        return false;
    }


//    private List<STProjectRisk> riskList(Collection collection) throws PIException {
//        if (collection.isEmpty()) return null;
//        List<STProjectIssueInvolveRiskLink> riskLinks = (List<STProjectIssueInvolveRiskLink>) collection;
//
//        PIArrayList removeLists = new PIArrayList(){{addAll(riskLinks.stream().filter(riskLink -> riskLink.getRoleBObject() == null).collect(Collectors.toList()));}};
//        PersistenceHelper.service.delete(removeLists);
//
//        List<STProjectRisk> riskList = riskLinks.stream().filter(riskLink -> riskLink.getRoleBObject() != null).map(riskLink -> {
//            return riskLink.getRoleBObject();
//        }).collect(Collectors.toList());
//        return riskList;
//    }


}
