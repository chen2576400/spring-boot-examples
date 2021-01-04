package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.dao.STProjectRiskAffectedGroupLinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class STProjectRiskAffectedGroupLinkServiceImpl implements STProjectRiskAffectedGroupLinkService {
    @Autowired
    private STProjectRiskAffectedGroupLinkDao linkDao;

    @Override
    public Collection findByRoleAObjectRef(ObjectReference reference) {
        return linkDao.findByRoleAObjectRef(reference);
    }

    @Override
    public void deleteByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA, ObjectReference referenceB) {
        linkDao.deleteByRoleAObjectRefAndRoleBObjectRef(referenceA,referenceB);
    }

    @Override
    public void deleteByRoleAObjectRef(ObjectReference referenceA) {
        linkDao.deleteByRoleAObjectRef(referenceA);
    }
}
