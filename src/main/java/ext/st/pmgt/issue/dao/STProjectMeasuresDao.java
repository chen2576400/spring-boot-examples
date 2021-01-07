package ext.st.pmgt.issue.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.model.STProjectMeasures;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface STProjectMeasuresDao extends JpaRepository<STProjectMeasures, ObjectIdentifier> {
    Collection findByProjectRiskReference(ObjectReference riskReference);
}
