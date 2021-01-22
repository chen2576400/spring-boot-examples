package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.dao.STProjectIssueInvolveRiskLinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class STProjectIssueInvolveRiskLinkServiceImpl implements STProjectIssueInvolveRiskLinkService{
    @Autowired
    private STProjectIssueInvolveRiskLinkDao dao;
//    @Override
//    public Collection findByRoleAObjectRef(ObjectReference reference) {
//        return dao.findByRoleAObjectRef(reference);
//    }

    @Override
    public void deleteByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA, ObjectReference referenceB) {
        dao.deleteByRoleAObjectRefAndRoleBObjectRef(referenceA,referenceB);
    }

    @Override
    public void deleteByRoleAObjectRef(ObjectReference referenceA) {
        dao.deleteByRoleAObjectRef(referenceA);
    }
}
