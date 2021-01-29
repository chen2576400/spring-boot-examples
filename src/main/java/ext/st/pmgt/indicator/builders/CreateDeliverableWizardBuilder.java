package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.project.datahandlers.DeliverablePickerHandler;

import java.util.Collection;
import java.util.List;

public class CreateDeliverableWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("createDeliverableWizard");
        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("createDeliverableWizardStep1");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setPrimaryClassName(PIPlanDeliverable.class);
            layout.setId("createDeliverableLayout");
            layout.setTitle("新建交付对象");
            layout.addField("name")
                    .addField("description")
                    .addField("url")
//                    .addField("deliverableTypeReference", new DeliverableTypePickerHandler())
                    .addField("subjectReference", new DeliverablePickerHandler())
//                    .addField("deliverableTemplate")
                    .addField("necessity");
            step.addLayout(layout);


//            //step2
//            StepConfig step1 = wizardConfig.newStep();
//            step1.setId("deliverableTypeStep");
//
//            TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
//            tableConfig.setId("deliverableTypeTable");
////            tableConfig.setEntities(getDeliverable(params));
//            tableConfig.setEntities(STIndicatorHelper.service.getDeliverableTypeByAct((PIPlanActivity) params.getNfCommandBean().getSourceObject()));
//            tableConfig.setTableTitle("交付物类别");
//            tableConfig.haveBorder();
//            tableConfig.setPrimaryObjectType(STDeliverableType.class);
//            tableConfig.enableSelect();
//            tableConfig.setSingleSelect(true);
//            tableConfig.setHeight("450px");
//
//            ColumnConfig column1 = componentConfigFactory.newColumnConfig();
//            column1.setName("name");
//            tableConfig.addColumn(column1);
//
//            ColumnConfig column2 = componentConfigFactory.newColumnConfig();
//            column2.setName("code");
//            tableConfig.addColumn(column2);
//
//            step1.children(tableConfig);
//            step1.setComponentAssistantBeanName("OTTableComponentAssistant");
//
//            //step3
//            StepConfig step3 = wizardConfig.newStep();
//            step3.setId("otStep");
//            TableConfig tableConfig1 = componentConfigFactory.newTableConfig(params);
//            tableConfig1.setId("OTTable");
////            tableConfig1.setTableTitle("OT指标");
//            tableConfig1.haveBorder();
//            tableConfig1.setPrimaryObjectType(STProjectInstanceOTIndicator.class);
//            tableConfig1.enableSelect();
//            tableConfig1.setSingleSelect(true);
//            tableConfig1.setHeight("450px");
//            tableConfig1.setToolbarActionModel("otTableToolBarSet",params);
//
//            ColumnConfig column11 = componentConfigFactory.newColumnConfig();
//            column11.setName("code");
////            column11.enableEdit();
//            tableConfig1.addColumn(column11);
//
//            ColumnConfig column22 = componentConfigFactory.newColumnConfig();
//            column22.setName("description");
////            column22.enableEdit();
//            tableConfig1.addColumn(column22);
//
//            ColumnConfig column3 = componentConfigFactory.newColumnConfig();
//            column3.setName("deviationReport");
//            column3.enableEdit();
//            tableConfig1.addColumn(column3);
//
//            ColumnConfig column4 = componentConfigFactory.newColumnConfig();
//            column4.setName("difficultyReport");
//            column4.enableEdit();
//            tableConfig1.addColumn(column4);
//
//            step3.children(tableConfig1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }

    private List getDeliverable(ComponentParams params) throws PIException {
        PIPlanActivity planAct = (PIPlanActivity) params.getNfCommandBean().getPagePrimaryObject();
        Collection result = STIndicatorHelper.service.findProjectOTIndicatorByPlanActivityAndPlan(planAct, (PIPlan) planAct.getRoot());
        return (List) result;
    }
}
