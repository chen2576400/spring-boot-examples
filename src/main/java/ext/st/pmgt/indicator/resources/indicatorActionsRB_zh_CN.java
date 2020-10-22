package ext.st.pmgt.indicator.resources;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;


@RBUUID("ext.st.pmgt.indicator.resources.indicatorActionsRB_zh_CN")
public class indicatorActionsRB_zh_CN extends PIListResourceBundle {

    @RBEntry("OT指标")
    public static final String Indicator_01 = "st-pmgt-indicator.planActivityOTIndicator.description";
    @RBEntry("OT指标")
    public static final String Indicator_02 = "st-pmgt-indicator.planActivityOTIndicator.tooltip";

    @RBEntry("IN指标")
    public static final String Indicator_03 = "st-pmgt-indicator.planActivityINIndicator.description";
    @RBEntry("IN指标")
    public static final String Indicator_04 = "st-pmgt-indicator.planActivityINIndicator.tooltip";

    @RBEntry("预计完成时间填写")
    public static final String act_01 = "st-pmgt-act.reportExpectedFinishTime.description";
    @RBEntry("预计完成时间填写")
    public static final String act_02 = "st-pmgt-act.reportExpectedFinishTime.tooltip";
    @RBEntry("img/pmgt/edit.gif")
    public static final String act_03 = "st-pmgt-act.reportExpectedFinishTime.icon";

    @RBEntry("pert报表")
    public static final String act_04 = "st-pmgt-act.pertReport.description";
    @RBEntry("pert报表")
    public static final String act_05= "st-pmgt-act.pertReport.tooltip";
    @RBEntry("img/pmgt/add.gif")
    public static final String act_06 = "st-pmgt-act.pertReport.icon";


    @RBEntry("创建交付对象")
    public static final String act_042 = "st-pmgt-act.createDeliverable.description";
    @RBEntry("创建交付对象")
    public static final String act_053 = "st-pmgt-act.createDeliverable.tooltip";
    @RBEntry("img/pmgt/add.gif")
    public static final String act_064 = "st-pmgt-act.createDeliverable.icon";

    @RBEntry("新增OT指标")
    public static final String act_0421 = "st-pmgt-ot.addOT.description";
    @RBEntry("新增OT指标")
    public static final String act_0532 = "st-pmgt-ot.addOT.tooltip";
    @RBEntry("img/pmgt/add.gif")
    public static final String act_0643 = "st-pmgt-ot.addOT.icon";

    @RBEntry("保存")
    public static final String act_04211 = "st-pmgt-ot.saveOT.description";
    @RBEntry("保存")
    public static final String act_05322 = "st-pmgt-ot.saveOT.tooltip";
    @RBEntry("img/pmgt/save.gif")
    public static final String act_06433 = "st-pmgt-ot.saveOT.icon";
}