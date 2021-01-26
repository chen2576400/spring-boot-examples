package ext.st.pmgt.project.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.project.datahandlers.*;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.project.datahandlers.PriorityDataHandler;

/**
 * @ClassName CreateProjectWizardBuilder
 * @Description:
 * @Author hma
 * @Date 2020/8/13
 * @Version V1.0
 **/
public class CreateProjectWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return null;
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("createProjectWizard");

        try {
            StepConfig step0 = wizardConfig.newStep();
            step0.setId("createProjectStep0");
            LayoutConfig layout0 = componentConfigFactory.newLayoutConfig(params);
            layout0.setPrimaryClass(PIProject.class);
            layout0.setId("createProjectLayout0");
            layout0.setTitle("type");
            layout0.addField("createProjectType", new GetProjectAllTypeDataHandler());
            //List<Option> options = new ArrayList<>();
            //Option opt1 = new Option().setLabel("项目").setValue("project");
            //options.add(opt1);
            //SelectElement selectElement0 = SelectElement.instance("createProjectType");
            //selectElement0.setOptions(options);
            //selectElement0.setDefaultOption(opt1);
            //selectElement0.attribute(elementAttribute -> elementAttribute.setStyle("cursor: pointer;display:inline-block;" +
            //        "padding: 3px 6px;text-align: right;width: 100px;vertical-align: middle;"));
            //selectElement0.vueDirectives(vueDirectives -> vueDirectives.addVueOn("select", "pickerSearch('projectTemplateComponentAssistant')"));
            //LabelElement labelElement0 = LabelElement.instance();
            //labelElement0.text("类型");
            //labelElement0.attribute(elementAttribute -> elementAttribute.setStyle("cursor: pointer;display:inline-block;" +
            //        "padding: 3px 6px;text-align: right;width: 100px;vertical-align: middle;"));
            //layout0.addElement(labelElement0);
            //layout0.addElement(selectElement0);

            step0.addLayout(layout0);
            step0.setComponentAssistantBeanName("projectTemplateComponentAssistant");

            StepConfig step1 = wizardConfig.newStep();
            step1.setId("createProjectStep1");
            LayoutConfig layout1 = componentConfigFactory.newLayoutConfig(params);
            layout1.setPrimaryClass(PIProject.class);
            layout1.setId("createProjectLayout1");
            layout1.setTitle("basicAttributes");
            layout1.addField("projectShortName",new PIProjectNumberDataHandler())
                    .addField("projectAbbreviation")
                    .addField("projectName")
                    .addField("priorityNum",new PriorityDataHandler())
                    .addField("strgyPriorityNum",new PriorityDataHandler())
                    .addField("riskLevel")
                    .addField("calendarReference", new CalendarSelectDataHandler())
                    .addField("projectTemplateReference",new ProjectTemplateDataHandler())//项目模板
                    //.addField("projectTemplateReference")//项目模板
                    .addField("projectGroupReference",new ProjectGroupDataHandler())//所属组
//                    .addField("projectUrl")
                    .addField("description");
            //step1.setComponentAssistantBeanName("projectTemplateComponentAssistant");


            LayoutConfig layout11 = componentConfigFactory.newLayoutConfig(params);
            layout11.setPrimaryClass(PIProject.class);
            layout11.setId("createProjectLayout11");
            layout11.setTitle("date");
            layout11.addField("planStartDate")
                    .addField("planEndDate")
                    .addField("forecastStartDate")
                    .addField("scheduledEndDate");

            step1.addLayout(layout1);
            step1.addLayout(layout11);

//            StepConfig step2 = wizardConfig.newStep();
//            step2.setId("createProjectStep2");
//            LayoutConfig layout2 = componentConfigFactory.newLayoutConfig(params);
//            layout2.setPrimaryClass(PIProject.class);
//            layout2.setId("createProjectLayout2");
//            layout2.setTitle("defaultValueAttribute");
//            layout2.addField("defaultDurationType")
//                    .addField("defaultCompletePercentType")
//                    .addField("defaultTaskType");
//            step2.addLayout(layout2);

            StepConfig step3 = wizardConfig.newStep();
            step3.setId("createProjectStep3");
            LayoutConfig layout3 = componentConfigFactory.newLayoutConfig(params);
            layout3.setPrimaryClass(PIProject.class);
            layout3.setId("createProjectLayout3");
            layout3.setTitle("resource");
            layout3.addField("defaultRateType")
                    .addField("rsrcMultiAssignFlag");
            step3.addLayout(layout3);

            StepConfig step4 = wizardConfig.newStep();
            step4.setId("createProjectStep4");
            LayoutConfig layout4 = componentConfigFactory.newLayoutConfig(params);
            layout4.setPrimaryClass(PIProject.class);
            layout4.setId("createProjectLayout4");
            layout4.setTitle("setting");
            layout4.addField("wbsMaxSumLevel")
                    .addField("useProjectBaselineFlag")
                    .addField("criticalPathType")
                    .addField("criticalDurationHourCount");
            step4.addLayout(layout4);

//            StepConfig step5 = wizardConfig.newStep();
//            step5.setId("createProjectStep5");
//            LayoutConfig layout5 = componentConfigFactory.newLayoutConfig(params);
//            layout5.setPrimaryClass(PIProject.class);
//            layout5.setId("createProjectLayout5");
//            layout5.setTitle("calculation");
//            layout5.addField("defaultCostPerQty");
//            step5.addLayout(layout5);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }
}