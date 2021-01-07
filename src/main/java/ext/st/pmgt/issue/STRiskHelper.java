package ext.st.pmgt.issue;

import com.pisx.tundra.foundation.util.ApplicationContextUtil;
import ext.st.pmgt.issue.model.STProjectRiskAffectedGroupLink;
import ext.st.pmgt.issue.service.*;

import java.io.Serializable;

public class STRiskHelper implements Serializable {

    public static STRiskService service;
    public static STProjectRiskAffectedGroupLinkService linkService;
    public static STProjectRiskPreRiskLinkService preRiskLinkService;
    static {
        service = (STRiskService) ApplicationContextUtil.getApplicationContext().getBean(STRiskService.class);
        linkService=(STProjectRiskAffectedGroupLinkService)ApplicationContextUtil.getApplicationContext().getBean(STProjectRiskAffectedGroupLinkService.class);
        preRiskLinkService=(STProjectRiskPreRiskLinkService)ApplicationContextUtil.getApplicationContext().getBean(STProjectRiskPreRiskLinkService.class);
    }
    public STRiskHelper() {
    }
}