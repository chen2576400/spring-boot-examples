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

    @RBEntry("addOT")
    public static final String act_0421 = "st-pmgt-ot.addOT.description";
    @RBEntry("addOT")
    public static final String act_0532 = "st-pmgt-ot.addOT.tooltip";
    @RBEntry("img/pmgt/add.gif")
    public static final String act_0643 = "st-pmgt-ot.addOT.icon";

    @RBEntry("saveOT")
    public static final String act_04211 = "st-pmgt-ot.saveOT.description";
    @RBEntry("saveOT")
    public static final String act_05322 = "st-pmgt-ot.saveOT.tooltip";
    @RBEntry("img/pmgt/save.gif")
    public static final String act_06433 = "st-pmgt-ot.saveOT.icon";
}