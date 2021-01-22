package ext.st.pmgt.issue.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.model.STProjectRiskPreRiskLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface STProjectRiskPreRiskLinkDao extends JpaRepository<STProjectRiskPreRiskLink, ObjectIdentifier> {
    Collection  findByRoleAObjectRefAndRoleBObjectRef(ObjectReference referenceA,ObjectReference referenceB);

}
