package ext.st.pmgt.indicator.resources;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("com.st.pmgt.indicator.resources.indicatorActionsRB")
public class indicatorActionsRB extends PIListResourceBundle {

    @RBEntry("OTIndicator")
    public static final String Indicator_01 = "st-pmgt-indicator.planActivityOTIndicator.description";
    @RBEntry("OTIndicator")
    public static final String Indicator_02 = "st-pmgt-indicator.planActivityOTIndicator.tooltip";

    @RBEntry("INIndicator")
    public static final String Indicator_03 = "st-pmgt-indicator.planActivityINIndicator.description";
    @RBEntry("INIndicator")
    public static final String Indicator_04 = "st-pmgt-indicator.planActivityINIndicator.tooltip";


}