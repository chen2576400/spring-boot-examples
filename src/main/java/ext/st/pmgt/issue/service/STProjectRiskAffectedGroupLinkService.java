package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectReference;

import java.util.Collection;

public interface STProjectRiskAffectedGroupLinkService {
    PICollection findByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA, ObjectReference referenceB);

}
