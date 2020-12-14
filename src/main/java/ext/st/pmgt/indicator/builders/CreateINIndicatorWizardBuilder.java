package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.resources.indicatorResource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreateINIndicatorWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        PIPlanActivity piPlanActivity = (PIPlanActivity) params.getNfCommandBean().getSourceObject();
        Set allresult = new HashSet<>();
//        得到计划下的所有OT指标
        if (piPlanActivity != null) {
            PIPlan piplan = (PIPlan) piPlanActivity.getRootReference().getObject();
            allresult.addAll(STIndicatorHelper.service.findProjectOTIndicatorByPlan((PIPlan) piplan));
        }
        Collection ins = STIndicatorHelper.service.findProjectINIndicatorByPlanActivity(piPlanActivity);
        Set result = new HashSet<>();
//        得到当前任务存在in指标
        for (Object in : ins) {
            result.addAll(STIndicatorHelper.service.getOTByIN((STProjectInstanceINIndicator) in));
        }
        allresult.removeAll(result);
//        防止指标循环使用
        Set activitySet = new HashSet();
        activitySet.add(piPlanActivity);
        getOTbyActivity(piPlanActivity, allresult, activitySet);
        return allresult;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("creatINIndicatorWizard");

        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("creatinindicatorwizardstep1");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setPrimaryClassName(STProjectInstanceINIndicator.class);
            layout.setId("creatinindicatorlayout");
            layout.setTitle("新增IN指标");
            layout.addField("weights");
            step.addLayout(layout);
            step.children(buildTable(componentData, params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }


    private TableConfig buildTable(Object componentData, ComponentParams params) {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setEntities(componentData);
        tableConfig.setPrimaryObjectType(STProjectInstanceOTIndicator.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(), "IN_INDICATOR_TABLE", null, params.getLocale()));
        tableConfig.haveStripe();
        tableConfig.enableSearch();
        tableConfig.setId("INIndicatorPickerTreeTable");//表格id
        tableConfig.enableSelect();
        tableConfig.setSingleSelect(true);//true为单选radio false为多选
        tableConfig.setPageSize(50);

        ColumnConfig columnconfig = componentConfigFactory.newColumnConfig();
        columnconfig.setName("code");
        columnconfig.haveInfoPageLink();
        tableConfig.addColumn(columnconfig);

        ColumnConfig columnconfig2 = componentConfigFactory.newColumnConfig();
        columnconfig2.setName("description");
        tableConfig.addColumn(columnconfig2);

        ColumnConfig columnconfig3 = componentConfigFactory.newColumnConfig();
        columnconfig3.setName("definition");
        tableConfig.addColumn(columnconfig3);


        ColumnConfig columnconfig32 = componentConfigFactory.newColumnConfig();
        columnconfig32.setName("standardDeviationValue");
        tableConfig.addColumn(columnconfig32);

        ColumnConfig columnconfig33 = componentConfigFactory.newColumnConfig();
        columnconfig33.setName("standardDifficultyValue");
        tableConfig.addColumn(columnconfig33);

        ColumnConfig columnconfig6 = componentConfigFactory.newColumnConfig();
        columnconfig6.setName("deliverableTypeCode");
        tableConfig.addColumn(columnconfig6);

        return tableConfig;
    }


    private void getOTbyActivity(PIPlanActivity piPlanActivity, Set result, Set planActivityset) throws PIException {
//      得到当前任务的所有输出指标
        Collection<STProjectInstanceOTIndicator> projectOTIndicatorByPlanActivity = STIndicatorHelper.service.findProjectOTIndicatorByPlanActivity(piPlanActivity);
//       该输出指标不能作为新增in指标
        result.removeAll(projectOTIndicatorByPlanActivity);
//        如果任务的没有输出指标，则停止递归
        if (projectOTIndicatorByPlanActivity != null && projectOTIndicatorByPlanActivity.size() > 0) {
//            遍历所有的输出指标，找到对应的输入指标的任务
            for (STProjectInstanceOTIndicator stProjectInstanceOTIndicator : projectOTIndicatorByPlanActivity) {
                Collection<STProjectInstanceINIndicator> byOtCode = STIndicatorHelper.service.findINIndicatorByOtCode(stProjectInstanceOTIndicator.getCode(),stProjectInstanceOTIndicator.getPlanReference());
                if (byOtCode != null && byOtCode.size() > 0) {
                    for (STProjectInstanceINIndicator stProjectInstanceINIndicator : byOtCode) {
                        PIPlanActivity planActivity = (PIPlanActivity) stProjectInstanceINIndicator.getPlanActivity();
//                       如果任务出现过 则表示几个输出指标作为同一个任务的输入指标或指标循环使用
                        if (planActivity != null && !planActivityset.contains(planActivity)) {
                            planActivityset.add(planActivity);
                            getOTbyActivity(planActivity, result, planActivityset);
                        }
                    }
                }
            }
        }
    }
}