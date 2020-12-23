package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("ext.st.pmgt.issue.model.ProjectUrgencyTypeRB")
public class ProjectUrgencyTypeRB extends PIListResourceBundle {
    @RBEntry("urgent")
    public static final String urgent = "urgent.display";
    @RBEntry("urgent")
    public static final String urgent_01 = "urgent.shortDescription";
    @RBEntry("50")
    public static final String urgent_02 = "urgent.order";

    @RBEntry("ordinary")
    public static final String ordinary = "ordinary.display";
    @RBEntry("ordinary")
    public static final String ordinary_01 = "ordinary.shortDescription";
    @RBEntry("50")
    public static final String ordinary_02 = "ordinary.order";
}
