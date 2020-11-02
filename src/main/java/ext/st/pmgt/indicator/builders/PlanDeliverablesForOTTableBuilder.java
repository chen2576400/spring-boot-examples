package ext.st.pmgt.indicator.builders;

import com.google.common.collect.Lists;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.deliverable.model.deliverableResource;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PlanActivityType;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PlanDeliverablesForOTTableBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/29
 * @Version V1.0
 **/
public class PlanDeliverablesForOTTableBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        List result = new ArrayList();
        if (sourceObject instanceof STProjectInstanceOTIndicator) {
            STProjectInstanceOTIndicator ot = (STProjectInstanceOTIndicator) sourceObject;
            PIPlanDeliverable deliverable = ot.getPlanDeliverable();
            if (deliverable!=null){
                result.add(deliverable);
            }
        }
        return result;

    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentconfigfactory = ComponentConfigFactory.getInstance();
        TableConfig tableconfig = componentconfigfactory.newTableConfig(params);
        tableconfig.setId("planDeliverablesForOTTable");
        tableconfig.setEntities(componentData);
        tableconfig.setPrimaryObjectType(PIPlanDeliverable.class);
        tableconfig.setTableTitle(PIMessage.getLocalizedMessage(deliverableResource.class.getName(),"DELIVERABLES_TABLE_TITLE",null, params.getLocale()));
        tableconfig.enableSearch();
        tableconfig.enableSelect();

        ColumnConfig columnconfig = componentconfigfactory.newColumnConfig();
        columnconfig.setName("name");
//        columnconfig.haveInfoPageLink();
        tableconfig.addColumn(columnconfig);

        ColumnConfig columnconfig3 = componentconfigfactory.newColumnConfig();
        columnconfig3.setName("description");
        tableconfig.addColumn(columnconfig3);

        ColumnConfig columnconfig4 = componentconfigfactory.newColumnConfig();
        columnconfig4.setName("deliverableType");
        tableconfig.addColumn(columnconfig4);

        ColumnConfig columnconfig5 = componentconfigfactory.newColumnConfig();
        columnconfig5.setName("deliverableTemplate");
        tableconfig.addColumn(columnconfig5);

        ColumnConfig columnconfig6 = componentconfigfactory.newColumnConfig();
        columnconfig6.setName("subjectReference");
        columnconfig6.setLabel("目标对象");
        tableconfig.addColumn(columnconfig6);

//        ColumnConfig columnconfig7 = componentconfigfactory.newColumnConfig();
//        columnconfig7.setName("latestVersion");
//        tableconfig.addColumn(columnconfig7);

        ColumnConfig columnconfig8 = componentconfigfactory.newColumnConfig();
        columnconfig8.setName("subject.lifeCycleState.stateName");
        columnconfig8.setLabel("目标对象状态");
        tableconfig.addColumn(columnconfig8);

        ColumnConfig columnconfig9 = componentconfigfactory.newColumnConfig();
        columnconfig9.setName("url");
        tableconfig.addColumn(columnconfig9);

        ColumnConfig columnconfig10 = componentconfigfactory.newColumnConfig();
        columnconfig10.setName("necessity");
        tableconfig.addColumn(columnconfig10);

        ColumnConfig columnconfig11 = componentconfigfactory.newColumnConfig();
        columnconfig11.setName("creator.name");
        columnconfig11.setLabel("创建者");
        tableconfig.addColumn(columnconfig11);

        columnconfig11 = componentconfigfactory.newColumnConfig();
        columnconfig11.setName("modifier.name");
        columnconfig11.setLabel("修改者");
        tableconfig.addColumn(columnconfig11);
//
        ColumnConfig columnconfig12= componentconfigfactory.newColumnConfig();
        columnconfig12.setName("persistInfo.createStamp");
        columnconfig12.setLabel("创建时间");
        tableconfig.addColumn(columnconfig12);

        columnconfig12 = componentconfigfactory.newColumnConfig();
        columnconfig12.setName("persistInfo.updateStamp");
        columnconfig12.setLabel("上次修改时间");
        tableconfig.addColumn(columnconfig12);

        return tableconfig;
    }
}