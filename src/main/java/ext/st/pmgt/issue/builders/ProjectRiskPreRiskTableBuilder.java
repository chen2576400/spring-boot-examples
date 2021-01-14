package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.collections.PIArrayList;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.model.MembershipLink;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import ext.st.pmgt.issue.STRiskHelper;
import ext.st.pmgt.issue.model.STProjectRisk;
import ext.st.pmgt.issue.model.STProjectRiskPreRiskLink;
import ext.st.pmgt.issue.service.STProjectRiskPreRiskLinkService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 风险的前置风险
 */
public class ProjectRiskPreRiskTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectRisk stProjectRisk = null;
        if (sourceObject instanceof STProjectRisk) {
            stProjectRisk = (STProjectRisk) sourceObject;

            Collection collection = STRiskHelper.preRiskLinkService.findByRoleAObjectRef(ObjectReference.newObjectReference(stProjectRisk));

//            Collection qr = PersistenceHelper.service.navigate(stProjectRisk, "roleB", STProjectRiskPreRiskLink.class,false);

            List<STProjectRisk> riskList = stProjectRisks(collection);
            return riskList;
        }
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setPrimaryObjectType(STProjectRisk.class);

        if (isSelect(params)) {
            tableConfig.setToolbarActionModel("preRiskToolbarSet");//操作按钮
        }
        tableConfig.enableSelect();
        tableConfig.setSingleSelect(false);//true为单选radio false为多选

        ColumnConfig column0 = componentConfigFactory.newColumnConfig();
        column0.setName("riskName");
        column0.haveInfoPageLink();
        tableConfig.addColumn(column0);


        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("riskCode");
        tableConfig.addColumn(column1);


        ColumnConfig column7 = componentConfigFactory.newColumnConfig();
        column7.setName("addDate");
        tableConfig.addColumn(column7);
        return tableConfig;
    }


    private List<STProjectRisk> stProjectRisks(Collection collection) throws PIException {
        if (collection.isEmpty()) return null;
        List<STProjectRiskPreRiskLink> riskLinks = (List<STProjectRiskPreRiskLink>) collection;

        PIArrayList removeLists = new PIArrayList() {{
            addAll(riskLinks.stream().filter(riskLink -> riskLink.getRoleBObject() == null).collect(Collectors.toList()));
        }};
        PersistenceHelper.service.delete(removeLists);

        List<STProjectRisk> riskList = riskLinks.stream().filter(riskLink -> riskLink.getRoleBObject() != null).map(STProjectRiskPreRiskLink -> {
            return STProjectRiskPreRiskLink.getRoleBObject();
        }).collect(Collectors.toList());
        return riskList;
    }

    private boolean isSelect(ComponentParams params) throws PIException {
        Persistable persistable = params.getNfCommandBean().getSourceObject();
        if (persistable instanceof STProjectRisk) {
            return true;
        }
        return false;
    }
}
