package ext.st.pmgt.issue.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.model.STProjectIssueInvolveGroupLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface STProjectIssueInvolveGroupLinkDao extends JpaRepository<STProjectIssueInvolveGroupLink, ObjectIdentifier> {
   Collection findByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA,ObjectReference referenceB);
}
