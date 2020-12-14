package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.indicator.model.STProjectRisk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface STProjectRiskDao extends JpaRepository<STProjectRisk, ObjectIdentifier> {
    Collection findByProjectReference(ObjectReference projectRef);
}
