package ext.st.pmgt.indicator;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.indicator.service.STProjectIssueService;

public class STProjectIssueHelper {
    public static STProjectIssueService service;

    static {
        service = (STProjectIssueService) ApplicationContextUtil.getApplicationContext().getBean(STProjectIssueService.class);
    }

    public STProjectIssueHelper() {

    }
}
