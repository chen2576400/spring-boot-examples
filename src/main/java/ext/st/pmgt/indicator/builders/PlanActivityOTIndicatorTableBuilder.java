package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.resources.indicatorResource;

import java.util.List;

/**
 * @ClassName PlanActivityOTIndicatorTableBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/13
 * @Version V1.0
 **/
public class PlanActivityOTIndicatorTableBuilder extends AbstractComponentBuilder {


    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIPlanActivity piPlanActivity = (PIPlanActivity) params.getNfCommandBean().getSourceObject();
        List<STProjectInstanceOTIndicator> ots = (List)STIndicatorHelper.service.findProjectOTIndicatorByPlanActivity(piPlanActivity);
        if (ots.size()>0){
            ots = STIndicatorHelper.service.getLatestOt(ots);
        }
        return ots;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
//        tableConfig.setToolbarActionModel("allUsersToolbarSet");
        tableConfig.setId("planActivityOTIndicator");
        tableConfig.setPrimaryObjectType(STProjectInstanceOTIndicator.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(),"OT_INDICATOR_TABLE",null,params.getLocale()));
        tableConfig.enableSelect();
        tableConfig.setPageSize(50);
//        tableConfig.setToolbarActionModel("deliverablesForPlanToolBarSet");
//        判断任务是否分配或完成度是否为0%
//        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
//        PIPlanActivity piPlanActivity = null;
//        if (sourceObject instanceof PIPlanActivity) {
//            piPlanActivity = (PIPlanActivity) sourceObject;
//        }
//        if(piPlanActivity.getPhysicalCompletePercent()==0) {
            tableConfig.setToolbarActionModel("OTIndicatorToolbar");
//        }
        ColumnConfig columnconfig = componentConfigFactory.newColumnConfig();
        columnconfig.setName("code");
        columnconfig.enableSort();
        columnconfig.haveInfoPageLink();
        tableConfig.addColumn(columnconfig);

        ColumnConfig columnconfig3 = componentConfigFactory.newColumnConfig();
        columnconfig3.setName("description");
        columnconfig3.enableSort();
        tableConfig.addColumn(columnconfig3);


        ColumnConfig columnconfig32 = componentConfigFactory.newColumnConfig();
        columnconfig32.setName("standardDeviationValue");
        columnconfig32.enableSort();
        tableConfig.addColumn(columnconfig32);

        ColumnConfig columnconfig33 = componentConfigFactory.newColumnConfig();
        columnconfig33.setName("standardDifficultyValue");
        columnconfig33.enableSort();
        tableConfig.addColumn(columnconfig33);

        ColumnConfig columnconfig4 = componentConfigFactory.newColumnConfig();
        columnconfig4.setName("deviationReport");
        columnconfig4.enableSort();
        tableConfig.addColumn(columnconfig4);

        ColumnConfig columnconfig5 = componentConfigFactory.newColumnConfig();
        columnconfig5.setName("difficultyReport");
        columnconfig5.enableSort();
        tableConfig.addColumn(columnconfig5);

        ColumnConfig columnconfig6 = componentConfigFactory.newColumnConfig();
        columnconfig6.setName("planDeliverable.name");
        columnconfig6.enableSort();
        columnconfig6.setLabel("交付物");
        columnconfig6.haveInfoPageLink();
        tableConfig.addColumn(columnconfig6);

        ColumnConfig columnconfig7 = componentConfigFactory.newColumnConfig();
        columnconfig7.setName("reportTime");
        columnconfig7.enableSort();
        tableConfig.addColumn(columnconfig7);

        return tableConfig;
    }
}