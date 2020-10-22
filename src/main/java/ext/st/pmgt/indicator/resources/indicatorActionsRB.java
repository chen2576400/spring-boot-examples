package ext.st.pmgt.indicator.resources;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("ext.st.pmgt.indicator.resources.indicatorActionsRB")
public class indicatorActionsRB extends PIListResourceBundle {

    @RBEntry("OTIndicator")
    public static final String Indicator_01 = "st-pmgt-indicator.planActivityOTIndicator.description";
    @RBEntry("OTIndicator")
    public static final String Indicator_02 = "st-pmgt-indicator.planActivityOTIndicator.tooltip";

    @RBEntry("INIndicator")
    public static final String Indicator_03 = "st-pmgt-indicator.planActivityINIndicator.description";
    @RBEntry("INIndicator")
    public static final String Indicator_04 = "st-pmgt-indicator.planActivityINIndicator.tooltip";

    @RBEntry("reportExpectedFinishTime")
    public static final String act_01 = "st-pmgt-act.reportExpectedFinishTime.description";
    @RBEntry("reportExpectedFinishTime")
    public static final String act_02 = "st-pmgt-act.reportExpectedFinishTime.tooltip";
    @RBEntry("img/pmgt/edit.gif")
    public static final String act_03 = "st-pmgt-act.reportExpectedFinishTime.icon";

    @RBEntry("pertReport")
    public static final String act_04 = "st-pmgt-act.pertReport.description";
    @RBEntry("pertReport")
    public static final String act_05= "st-pmgt-act.pertReport.tooltip";
    @RBEntry("img/pmgt/add.gif")
    public static final String act_06 = "st-pmgt-act.pertReport.icon";


    @RBEntry("createDeliverable")
    public static final String act_042 = "st-pmgt-act.createDeliverable.description";
    @RBEntry("createDeliverable")
    public static final String act_053 = "st-pmgt-act.createDeliverable.tooltip";
    @RBEntry("img/pmgt/add.gif")
    public static final String act_064 = "st-pmgt-act.createDeliverable.icon";
}