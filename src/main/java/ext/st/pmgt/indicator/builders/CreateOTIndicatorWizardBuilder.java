package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.PIGroup;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.model.STProjectIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.resources.indicatorResource;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CreateOTIndicatorWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        Set<STProjectInstanceOTIndicator> otResult = new HashSet<>();
        PIPlanActivity piPlanActivity = null;
        if (sourceObject instanceof PIPlanActivity) {
            piPlanActivity = (PIPlanActivity) sourceObject;
        }
//        得到本计划中所有使用了的ot指标
        PIPlan piplan = (PIPlan) piPlanActivity.getRootReference().getObject();
        otResult.addAll(STIndicatorHelper.service.findProjectOTIndicatorByPlan((PIPlan) piplan));
//        得到当前资源
        PIPrincipalReference principalReference = SessionHelper.service.getPrincipalReference();
//        通过当前资源得到当前职能
        Collection<PIGroup> groups = OrgHelper.service.getImmediateParentGroups(principalReference.getObject(), false);
//         得到相应专业能力的指标
        Set<STProjectIndicator> indicatorResult = new HashSet<>();
        if (CollectionUtils.isNotEmpty(groups)) {
            for (PIGroup group : groups) {
                Collection indicator = STIndicatorHelper.service.getAllIndicatorByCompetence(group,true);
                if(CollectionUtils.isNotEmpty(indicator)){
                    indicatorResult.addAll(indicator);
                }
            }
        }
        Set result =new HashSet();
        if (CollectionUtils.isNotEmpty(indicatorResult)) {
            for (STProjectIndicator stProjectIndicator : indicatorResult) {
                if (CollectionUtils.isNotEmpty(otResult)) {
                    for (STProjectInstanceOTIndicator stProjectInstanceOTIndicator : otResult) {
                        if (stProjectInstanceOTIndicator.getCode().equals(stProjectIndicator.getCode())) {
                            result.add(stProjectIndicator);
                            break;
                        }
                    }
                }
            }
        }
        indicatorResult.removeAll(result);
        return indicatorResult;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("createOTIndicatorWizard");
        StepConfig step = wizardConfig.newStep();
        step.setId("createOTIndicatorStep");

        TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
        tableConfig.setId("indicatortable");
        tableConfig.setEntities(componentData);
//        tableConfig.enableSelect();
//        tableConfig.setSingleSelect(true);
        tableConfig.enableSearch();
        tableConfig.setPrimaryObjectType(STProjectIndicator.class);
        tableConfig.setTableTitle(PIMessage.getLocalizedMessage(indicatorResource.class.getName(), "OT_INDICATOR_TABLE", null, params.getLocale()));
        tableConfig.setPageSize(50);
        tableConfig.setRightMenuName("DeviationValueAndDifficultyValueMenu",params);

        ColumnConfig column1 = componentConfigFactory.newColumnConfig();
        column1.setName("code");
        column1.enableFilter();
        column1.enableSort();
        tableConfig.addColumn(column1);

        ColumnConfig column2 = componentConfigFactory.newColumnConfig();
        column2.setName("decription");
        tableConfig.addColumn(column2);

        ColumnConfig column3 = componentConfigFactory.newColumnConfig();
        column3.setName("definition");
        tableConfig.addColumn(column3);

        ColumnConfig column4 = componentConfigFactory.newColumnConfig();
        column4.setName("deliverableTypeCode");
        tableConfig.addColumn(column4);

        ColumnConfig column5 = componentConfigFactory.newColumnConfig();
        column5.setName("competence.name");
        column5.setLabel("专业能力");
        tableConfig.addColumn(column5);
        step.addComponentConfig(tableConfig);

        return wizardConfig;
    }


}
