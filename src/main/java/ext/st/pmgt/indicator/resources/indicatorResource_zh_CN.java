
package ext.st.pmgt.indicator.resources;


import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBComment;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("ext.st.pmgt.indicator.resources.indicatorResource_zh_CN")
public final class indicatorResource_zh_CN extends PIListResourceBundle {
    @RBEntry("OT指标")
    public static final String PIPlanDeliverable_01 = "OT_INDICATOR_TABLE";

    @RBEntry("IN指标")
    public static final String PIPlanDeliverable_02 = "IN_INDICATOR_TABLE";

    @RBEntry("指标评定")
    public static final String PIPlanDeliverable_03 = "INDICATOR_RATING_TABLE";

    @RBEntry("未评定指标")
    public static final String PIPlanDeliverable_04 = "NOT_RATING_INDICATOR_TABLE";
}
