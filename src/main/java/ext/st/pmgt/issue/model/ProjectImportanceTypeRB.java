package ext.st.pmgt.issue.model;

import com.pisx.tundra.foundation.util.resource.PIListResourceBundle;
import com.pisx.tundra.foundation.util.resource.RBEntry;
import com.pisx.tundra.foundation.util.resource.RBUUID;

@RBUUID("ext.st.pmgt.issue.model.ProjectImportanceTypeRB")
public class ProjectImportanceTypeRB extends PIListResourceBundle {

    @RBEntry("important")
    public static final String important = "important.display";
    @RBEntry("important")
    public static final String important_01 = "important.shortDescription";
    @RBEntry("50")
    public static final String important_02 = "important.order";

    @RBEntry("ordinary")
    public static final String ordinary = "ordinary.display";
    @RBEntry("ordinary")
    public static final String ordinary_01 = "ordinary.shortDescription";
    @RBEntry("50")
    public static final String ordinary_02 = "ordinary.order";

}
