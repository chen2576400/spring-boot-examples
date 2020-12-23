package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("ext.st.pmgt.issue.model.ProjectImportanceTypeRB_zh_CN")
public class ProjectImportanceTypeRB_zh_CN extends PIListResourceBundle {
    @RBEntry("重要")
    public static final String important = "important.display";
    @RBEntry("重要")
    public static final String important_01 = "important.shortDescription";
    @RBEntry("50")
    public static final String important_02 = "important.order";

    @RBEntry("普通")
    public static final String ordinary = "ordinary.display";
    @RBEntry("普通")
    public static final String ordinary_01 = "ordinary.shortDescription";
    @RBEntry("50")
    public static final String ordinary_02 = "ordinary.order";


}
