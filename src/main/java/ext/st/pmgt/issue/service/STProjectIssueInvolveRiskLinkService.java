package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.model.ObjectReference;

import java.util.Collection;

public interface STProjectIssueInvolveRiskLinkService {
    Collection findByRoleAObjectRef(ObjectReference reference);

    void deleteByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA, ObjectReference referenceB);

    void deleteAll();
}
