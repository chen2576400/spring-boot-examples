package ext.st.pmgt.issue;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.issue.service.STProjectIssueService;

public class STProjectIssueHelper {
    public static STProjectIssueService service;

    static {
        service = (STProjectIssueService) ApplicationContextUtil.getApplicationContext().getBean(STProjectIssueService.class);
    }

    public STProjectIssueHelper() {

    }
}
