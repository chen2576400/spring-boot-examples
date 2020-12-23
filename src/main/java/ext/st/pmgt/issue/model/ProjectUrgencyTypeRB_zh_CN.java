package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("ext.st.pmgt.issue.model.ProjectUrgencyTypeRB_zh_CN")
public class ProjectUrgencyTypeRB_zh_CN extends PIListResourceBundle {
    @RBEntry("紧急")
    public static final String urgent = "urgent.display";
    @RBEntry("紧急")
    public static final String urgent_01 = "urgent.shortDescription";
    @RBEntry("50")
    public static final String urgent_02 = "urgent.order";

    @RBEntry("普通")
    public static final String ordinary = "ordinary.display";
    @RBEntry("普通")
    public static final String ordinary_01 = "ordinary.shortDescription";
    @RBEntry("50")
    public static final String ordinary_02 = "ordinary.order";
}
