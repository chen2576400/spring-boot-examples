package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.*;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.util.misc.AlertType;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.resources.indicatorResource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName PlanActivityINIndicatorTableBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/13
 * @Version V1.0
 **/
public class PlanActivityINIndicatorTableBuilder extends AbstractComponentBuilder {


    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIPlanActivity piPlanActivity = (PIPlanActivity) params.getNfCommandBean().getSourceObject();
        Collection ins = STIndicatorHelper.service.findProjectINIndicatorByPlanActivity(piPlanActivity);
        Set result = new HashSet<>();
        List<STProjectInstanceINIndicator> warningIN = new ArrayList<>();
        for (Object in : ins) {
            List<STProjectInstanceOTIndicator> ots = (List<STProjectInstanceOTIndicator>) STIndicatorHelper.service.getOTByIN((STProjectInstanceINIndicator) in);
            if (ots.size() > 0) {
                result.addAll(STIndicatorHelper.service.getLatestOt(ots));
            } else {
                warningIN.add((STProjectInstanceINIndicator) in);
            }
        }
        if (warningIN.size()>0) {
            String insCode = getInsCode(warningIN);
            NotifySupport.alert(AlertType.SUCCESS, "当前需求的IN指标" + insCode + "没有在本计划任何任务中产出，请联系PM或者PMO！",10);
        }
        return result;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setId("planActivityINIndicator");
        tableConfig.setPrimaryObjectType(STProjectInstanceOTIndicator.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(), "IN_INDICATOR_TABLE", null, params.getLocale()));
        tableConfig.enableSelect();
        tableConfig.setToolbarActionModel("inTableToolBar");
        tableConfig.setRightMenuName("INTableMenu", params);
        tableConfig.setPageSize(50);

        ColumnConfig columnconfig = componentConfigFactory.newColumnConfig();
        columnconfig.setName("code");
        columnconfig.haveInfoPageLink();
        columnconfig.enableSort();
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
        columnconfig6.setLabel("交付物");
        columnconfig6.haveInfoPageLink();
        columnconfig6.enableSort();
        tableConfig.addColumn(columnconfig6);


        ColumnConfig columnconfig7 = componentConfigFactory.newColumnConfig();
        columnconfig7.setName("reportTime");
        columnconfig7.enableSort();
        tableConfig.addColumn(columnconfig7);

        return tableConfig;
    }

    private String getInsCode(List<STProjectInstanceINIndicator> ins){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ins.size(); i++) {
            if (i==ins.size()-1){
                str.append(ins.get(i).getOtCode());
            }else {
                str.append(ins.get(i).getOtCode()+"、");
            }
        }
        return str.toString();
    }
}