package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.indicator.model.STExpectedFinishTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ExpectedFinishTimeDao extends JpaRepository<STExpectedFinishTime, ObjectIdentifier> {
    Collection findByPlanActivityReference(ObjectReference actRef);
}
