package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.collections.PIHashSet;
import com.pisx.tundra.foundation.fc.collections.PISet;
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
    public PICollection findByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA, ObjectReference referenceB) {
        PISet set = new PIHashSet();
        set.addAll(linkDao.findByRoleAObjectRefAndRoleBObjectRef(referenceA, referenceB));
        return set;
    }
}
