package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.indicator.model.STProjectIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface STProjectIssueDao extends JpaRepository<STProjectIssue, ObjectIdentifier> {

    Collection findByPlanActivityReference(ObjectReference objectReference);
}
