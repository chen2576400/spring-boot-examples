package ext.st.pmgt.project.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.project.datahandlers.CalendarSelectDataHandler;
import com.pisx.tundra.pmgt.project.datahandlers.ProjectGroupDataHandler;
import com.pisx.tundra.pmgt.project.datahandlers.ProjectTemplateDataHandler;
import com.pisx.tundra.pmgt.project.model.PIProject;

public class EditProjectWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("editProjectWizard");

        try {
//            StepConfig step0 = wizardConfig.newStep();
//            step0.setId("createProjectStep0");
//            LayoutConfig layout0 = componentConfigFactory.newLayoutConfig(params);
//            layout0.setPrimaryClassName(PIProject.class);
//            layout0.setEntity((PIProject)componentData);
//            layout0.setId("createProjectLayout0");
//            layout0.setTitle("type");
//
//            List<Option> options = new ArrayList<>();
//            Option opt1 = new Option().setLabel("项目").setValue("project");
//            Option opt2 = new Option().setLabel("A项目").setValue("projectA");
//            options.add(opt1);
//            options.add(opt2);
//            SelectElement selectElement0 = SelectElement.instance("createProjectType");
//            selectElement0.setOptions(options);
//            selectElement0.setDefaultOption(opt1);
//            selectElement0.attribute(elementAttribute -> elementAttribute.setStyle("cursor: pointer;display:inline-block;" +
//                    "padding: 3px 6px;text-align: right;width: 100px;vertical-align: middle;"));
//
//            LabelElement labelElement0 = LabelElement.instance();
//            labelElement0.text("类型");
//            labelElement0.attribute(elementAttribute -> elementAttribute.setStyle("cursor: pointer;display:inline-block;" +
//                    "padding: 3px 6px;text-align: right;width: 100px;vertical-align: middle;"));
//            layout0.addElement(labelElement0);
//            layout0.addElement(selectElement0);
//
//            step0.addLayout(layout0);


            StepConfig step1 = wizardConfig.newStep();
            step1.setId("editProjectStep1");
            LayoutConfig layout1 = componentConfigFactory.newLayoutConfig(params);
            layout1.setPrimaryClassName(PIProject.class);
            layout1.setEntity((PIProject) componentData);
            layout1.setId("editProjectLayout1");
            layout1.setTitle("basicAttributes");
            layout1
                    //.addField("projectShortName")
                    .addField("projectAbbreviation")
                    .addField("projectName")
                    .addField("priorityNum")
                    .addField("strgyPriorityNum")
                    .addField("riskLevel")
                    .addField("calendarReference", new CalendarSelectDataHandler())
                    .addField("projectTemplateReference",new ProjectTemplateDataHandler())//项目模板
                    .addField("projectGroupReference", new ProjectGroupDataHandler())//所属组
//                    .addField("projectUrl")
                    .addField("description");


            LayoutConfig layout11 = componentConfigFactory.newLayoutConfig(params);
            layout11.setPrimaryClass(PIProject.class);
            layout11.setId("createProjectLayout11");
            layout11.setEntity((PIProject) componentData);
            layout11.setTitle("date");
            layout11.addField("planStartDate")
                    .addField("planEndDate")
                    .addField("forecastStartDate")
                    .addField("scheduledEndDate");

            step1.addLayout(layout1);
            step1.addLayout(layout11);

            StepConfig step3 = wizardConfig.newStep();
            step3.setId("editProjectStep3");
            LayoutConfig layout3 = componentConfigFactory.newLayoutConfig(params);
            layout3.setPrimaryClass(PIProject.class);
            layout3.setEntity((PIProject) componentData);
            layout3.setId("editProjectLayout3");
            layout3.setTitle("resource");
            layout3.addField("defaultRateType")
                    .addField("rsrcMultiAssignFlag");
            step3.addLayout(layout3);

            StepConfig step4 = wizardConfig.newStep();
            step4.setId("editProjectStep4");
            LayoutConfig layout4 = componentConfigFactory.newLayoutConfig(params);
            layout4.setPrimaryClass(PIProject.class);
            layout4.setEntity((PIProject) componentData);
            layout4.setId("editProjectLayout4");
            layout4.setTitle("setting");
            layout4.addField("wbsMaxSumLevel")
                    .addField("useProjectBaselineFlag")
                    .addField("criticalPathType")
                    .addField("criticalDurationHourCount");
            step4.addLayout(layout4);

            StepConfig step5 = wizardConfig.newStep();
            step5.setId("editProjectStep5");
            LayoutConfig layout5 = componentConfigFactory.newLayoutConfig(params);
            layout5.setPrimaryClass(PIProject.class);
            layout5.setEntity((PIProject) componentData);
            layout5.setId("editProjectLayout5");
            layout5.setTitle("calculation");
            layout5.addField("defaultCostPerQty");
            step5.addLayout(layout5);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}
