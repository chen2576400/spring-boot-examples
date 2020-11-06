package ext.st.pmgt.indicator.builders;

import com.pisx.tundra.foundation.doc.model.PIDocument;
import com.pisx.tundra.foundation.enterprise.model.RevisionControlled;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.vc.VersionControlHelper;
import com.pisx.tundra.foundation.vc.model.Versioned;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.label.LabelElement;
import com.pisx.tundra.netfactory.mvc.components.select.SelectElement;
import com.pisx.tundra.netfactory.mvc.components.table.config.ColumnConfig;
import com.pisx.tundra.netfactory.mvc.components.table.config.TableConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.netfactory.util.misc.Option;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIPmgtBaselineType;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.datahandlers.DeliverablePickerHandler;
import ext.st.pmgt.indicator.datahandlers.DeviationReportDataHandler;
import ext.st.pmgt.indicator.datahandlers.DifficultyReportDataHandler;
import ext.st.pmgt.indicator.model.STDeliverableType;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName IndicatorReportWizardBuilder
 * @Description:
 * @Author hma
 * @Date 2020/10/30
 * @Version V1.0
 **/
public class IndicatorReportWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("IndicatorReportWizard");
        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("IndicatorReportStep1");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            layout.setPrimaryClassName(PIPlanDeliverable.class);
            layout.setId("IndicatorReportLayout1");
            layout.setTitle("当前交付物");
            layout.setEntity((PIPlanDeliverable) componentData);
            layout.addField("name")
                    .addField("description")
//                    .addField("url")
//                    .addField("deliverableTypeReference", new DeliverableTypePickerHandler())
                    .addField("subjectReference", new DeliverablePickerHandler())
//                    .addField("deliverableTemplate")
                    .addField("necessity");
            step.addLayout(layout);
            ////////////////////

            StepConfig step0 = wizardConfig.newStep();
            step0.setId("IndicatorReportStep2");
            LayoutConfig layout1 = componentConfigFactory.newLayoutConfig(params);
            layout1.setId("IndicatorReportLayout2");
            layout1.setTitle("其他版本");

            PIPlanDeliverable sourceObj = (PIPlanDeliverable) componentData;
            RevisionControlled revisionControlled = (RevisionControlled) sourceObj.getSubject();
            List<PIDocument> versions = (List<PIDocument>) VersionControlHelper.service.allIterationsOf(revisionControlled.getMaster());

            List<Option> options = new ArrayList<>();
            for (PIDocument version : versions) {
                Option opt1 = new Option().setLabel(version.getVersionIdentifier().getVersionId() + "." + version.getVersionIdentifier().getVersionLevel()).setValue(version.getOid());
                options.add(opt1);
            }
            SelectElement selectElement0 = SelectElement.instance("currentVersion");
            selectElement0.setOptions(options);
            selectElement0.setDefaultOption(new Option().setLabel(revisionControlled.getVersionIdentifier().getVersionId() + "." + revisionControlled.getVersionIdentifier().getVersionLevel()).setValue(revisionControlled.getOid()));
            selectElement0.attribute(elementAttribute -> elementAttribute.setStyle("cursor: pointer;display:inline-block;" +
                    "padding: 3px 6px;text-align: right;width: 200px;vertical-align: middle;"));

            LabelElement labelElement0 = LabelElement.instance();
            labelElement0.text("当前版本");
            labelElement0.attribute(elementAttribute -> elementAttribute.setStyle("cursor: pointer;display:inline-block;" +
                    "padding: 3px 6px;text-align: center;width: 70px;vertical-align: middle;"));
            layout1.addElement(labelElement0);
            layout1.addElement(selectElement0);

            step0.addLayout(layout1);

///////////////////////
            //step2
            StepConfig step1 = wizardConfig.newStep();
            step1.setId("IndicatorReportStep3");

            TableConfig tableConfig = componentConfigFactory.newTableConfig(params);
            tableConfig.setId("deliverableTypeTable");
//            tableConfig.setEntities(getDeliverable(params));
            tableConfig.setEntities(STIndicatorHelper.service.getDeliverableTypeByAct((PIPlanActivity) sourceObj.getParent()));
            tableConfig.setTableTitle("交付物类别");
            tableConfig.haveBorder();
            tableConfig.setPrimaryObjectType(STDeliverableType.class);
            tableConfig.enableSelect();
            tableConfig.setSingleSelect(true);
            tableConfig.setHeight("450px");

            ColumnConfig column1 = componentConfigFactory.newColumnConfig();
            column1.setName("name");
            tableConfig.addColumn(column1);

            ColumnConfig column2 = componentConfigFactory.newColumnConfig();
            column2.setName("code");
            tableConfig.addColumn(column2);

            step1.children(tableConfig);
            step1.setComponentAssistantBeanName("OTTableComponentAssistant");

            //step3
            StepConfig step3 = wizardConfig.newStep();
            step3.setId("IndicatorReportStep4");
            TableConfig tableConfig1 = componentConfigFactory.newTableConfig(params);
            tableConfig1.setId("OTTable");
            tableConfig1.setTableTitle("OT指标");
            tableConfig1.haveBorder();
            tableConfig1.setPrimaryObjectType(STProjectInstanceOTIndicator.class);
            tableConfig1.setHeight("450px");
//            tableConfig1.setToolbarActionModel("otTableToolBarSet", params);

            ColumnConfig column11 = componentConfigFactory.newColumnConfig();
            column11.setName("code");
            tableConfig1.addColumn(column11);

            ColumnConfig column22 = componentConfigFactory.newColumnConfig();
            column22.setName("description");
            tableConfig1.addColumn(column22);

            ColumnConfig column221 = componentConfigFactory.newColumnConfig();
            column221.setName("standardDeviationValue");
            tableConfig1.addColumn(column221);

            ColumnConfig column222 = componentConfigFactory.newColumnConfig();
            column222.setName("standardDifficultyValue");
            column222.enableSort();
            tableConfig1.addColumn(column222);

            ColumnConfig column3 = componentConfigFactory.newColumnConfig();
            column3.setName("deviationReport");
            column3.setDataHandler(new DeviationReportDataHandler());
            column3.enableEdit();
            tableConfig1.addColumn(column3);

            ColumnConfig column4 = componentConfigFactory.newColumnConfig();
            column4.setName("difficultyReport");
            column4.setDataHandler(new DifficultyReportDataHandler());
            column4.enableEdit();
            tableConfig1.addColumn(column4);

            ColumnConfig column5 = componentConfigFactory.newColumnConfig();
            column5.setName("reportTime");
            column5.enableSort();
            column5.isSortable();
            tableConfig1.addColumn(column5);

            step3.children(tableConfig1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}