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
    public static final String act_01 = "pi-pmgt-act.editExpectedFinishTime.description";
    @RBEntry("预计完成时间填写")
    public static final String act_02 = "pi-pmgt-act.editExpectedFinishTime.tooltip";
    @RBEntry("img/pmgt/edit.gif")
    public static final String act_03 = "pi-pmgt-act.editExpectedFinishTime.icon";

    @RBEntry("pert报表")
    public static final String act_04 = "pi-pmgt-act.pertReport.description";
    @RBEntry("pert报表")
    public static final String act_05= "pi-pmgt-act.pertReport.tooltip";
    @RBEntry("img/pmgt/add.gif")
    public static final String act_06 = "pi-pmgt-act.pertReport.icon";

}