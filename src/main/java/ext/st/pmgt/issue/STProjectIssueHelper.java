package ext.st.pmgt.issue;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.issue.service.STProjectIssueInvolveGroupLinkService;
import ext.st.pmgt.issue.service.STProjectIssueService;

public class STProjectIssueHelper {
    public static STProjectIssueService service;
    public static STProjectIssueInvolveGroupLinkService linkService;

    static {
        service = (STProjectIssueService) ApplicationContextUtil.getApplicationContext().getBean(STProjectIssueService.class);
        linkService=(STProjectIssueInvolveGroupLinkService) ApplicationContextUtil.getApplicationContext().getBean(STProjectIssueInvolveGroupLinkService.class);
    }

    public STProjectIssueHelper() {

    }
}
