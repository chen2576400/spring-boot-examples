package ext.st.pmgt.issue.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.model.STProjectIssueInvolveRiskLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface STProjectIssueInvolveRiskLinkDao extends JpaRepository<STProjectIssueInvolveRiskLink, ObjectIdentifier> {
    Collection findByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA,ObjectReference referenceB);
}
