package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.collections.PIHashSet;
import com.pisx.tundra.foundation.fc.collections.PISet;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.dao.STProjectIssueInvolveRiskLinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class STProjectIssueInvolveRiskLinkServiceImpl implements STProjectIssueInvolveRiskLinkService {
    @Autowired
    private STProjectIssueInvolveRiskLinkDao dao;

    @Override
    public PICollection findByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA, ObjectReference referenceB) {
        PISet set = new PIHashSet();
        set.addAll(dao.findByRoleAObjectRefAndRoleBObjectRef(referenceA, referenceB));
        return set;
    }
}
