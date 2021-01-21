package ext.st.pmgt.issue.builders;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.AbstractComponentBuilder;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfig;
import com.pisx.tundra.netfactory.mvc.components.ComponentConfigFactory;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.wizard.LayoutConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.StepConfig;
import com.pisx.tundra.netfactory.mvc.components.wizard.WizardConfig;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.issue.datahandlers.*;
import ext.st.pmgt.issue.model.STProjectIssue;

import java.sql.Timestamp;

public class CreateOREditProjectIssueWizardBuilder extends AbstractComponentBuilder {
    @Override
    public Object buildComponentData(ComponentParams params) throws PIException {
        return params.getNfCommandBean().getSourceObject();
    }

    @Override
    public ComponentConfig buildComponentConfig(Object componentData, ComponentParams params) throws PIException {
        ComponentConfigFactory componentConfigFactory = ComponentConfigFactory.getInstance();
        WizardConfig wizardConfig = componentConfigFactory.newWizardConfig(params);
        wizardConfig.setId("createProjectIssueWizard");

        try {
            StepConfig step = wizardConfig.newStep();
            step.setId("createProjectIssueStep1");
            LayoutConfig layout = componentConfigFactory.newLayoutConfig(params);
            STProjectIssue issue = null;
            //为编辑
            if (componentData instanceof STProjectIssue) {
                issue = ((STProjectIssue) componentData);
            }
            //为新建
            if (issue == null) {
                issue = new STProjectIssue();
                issue.setAddDate(new Timestamp(System.currentTimeMillis()));
                if (componentData instanceof PIPlanActivity){  //如果创建界面是活动界面  活动列默认为当前活动
                    PIPlanActivity activity=(PIPlanActivity)componentData;
                    issue.setPlanActivity(activity);
                }
            }
            layout.setEntity(issue);
            layout.setPrimaryClassName(STProjectIssue.class);
            layout.setId("createProjectIssueLayout");
            layout.setTitle("创建项目问题");

            layout.addField("name")
                    .addField("issueType")
                    .addField("priorityType")
                    .addField("addDate")
                    .addField("expectedSolutionDate")
                    .addField("planActivityReference", new PlanActivityExpandDataHandler())
                    .addField("rsrcReference", new ResourceExpandDataHandler())
                    .addField("responsibleUserReference", new UserPickerExpandDataHandler())
                    .addField("description")
//                    .addField("confirmStatus")//是否确认
                    .addField("treatmentPlan", new TextAreaDataHandler())//处理方案
                    .addField("dutyGroupReference", new DutyGroupDataHandler())//责任部门
                    .addField("importanceType")//重要度
                    .addField("urgencyType")//紧急度
//                    .addField("closeStamp")//关闭时间
//                    .addField("projectManagerUserReference", new ProjectManagerUserDataHandler())//项目经理
            ;


            step.addLayout(layout);

            StepConfig stepConfig2 = wizardConfig.newStep();
            stepConfig2.setId("createProjectIssueStep2");
            stepConfig2.setTitle("设置附件");
            setStep(componentData, stepConfig2);
//            stepConfig2.setStepAction("attachments", "createOrEditAttachments");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wizardConfig;
    }


    private void setStep(Object componentData, StepConfig stepConfig) {
        if (componentData instanceof STProjectIssue) {//编辑
            stepConfig.setStepAction("attachments", "createOrEditAttachments");
        } else {
            stepConfig.setStepAction("contentHolder", "uploadAttachment");
        }
//        else if (componentData instanceof PIProject) { //创建
//            stepConfig.setStepAction("contentHolder", "uploadAttachment");
//        }else if (componentData instanceof PIPlanActivity) { //创建
//            stepConfig.setStepAction("contentHolder", "uploadAttachment");
//        }

    }

}