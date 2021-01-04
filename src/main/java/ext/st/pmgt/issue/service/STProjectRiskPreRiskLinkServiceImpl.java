package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.dao.STProjectRiskPreRiskLinkDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class STProjectRiskPreRiskLinkServiceImpl implements STProjectRiskPreRiskLinkService {
    @Autowired
    private STProjectRiskPreRiskLinkDao dao;

    @Override
    public Collection findByRoleAObjectRef(ObjectReference reference) {
        return dao.findByRoleAObjectRef(reference);
    }

    @Override
    public void deleteByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA, ObjectReference referenceB) {
        dao.deleteByRoleAObjectRefAndRoleBObjectRef(referenceA, referenceB);
    }
}
