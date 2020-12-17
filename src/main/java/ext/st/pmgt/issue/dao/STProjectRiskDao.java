package ext.st.pmgt.issue.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface STProjectRiskDao extends JpaRepository<STProjectRisk, ObjectIdentifier> {
    Collection findByProjectReference(ObjectReference projectRef);
}
