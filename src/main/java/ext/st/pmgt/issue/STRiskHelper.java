package ext.st.pmgt.issue;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.issue.model.STProjectRiskAffectedGroupLink;
import ext.st.pmgt.issue.service.STProjectRiskAffectedGroupLinkService;
import ext.st.pmgt.issue.service.STProjectRiskAffectedGroupLinkServiceImpl;
import ext.st.pmgt.issue.service.STRiskService;
import ext.st.pmgt.issue.service.STRiskServiceImpl;

import java.io.Serializable;

public class STRiskHelper implements Serializable {

    public static STRiskService service;
    public static STProjectRiskAffectedGroupLinkService linkService;
    static {
        service = (STRiskService) ApplicationContextUtil.getApplicationContext().getBean(STRiskServiceImpl.class);
        linkService=(STProjectRiskAffectedGroupLinkService)ApplicationContextUtil.getApplicationContext().getBean(STProjectRiskAffectedGroupLinkService.class);
    }
    public STRiskHelper() {
    }
}