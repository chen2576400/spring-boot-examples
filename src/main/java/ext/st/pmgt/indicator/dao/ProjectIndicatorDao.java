package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import ext.st.pmgt.indicator.model.STProjectIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProjectIndicatorDao extends JpaRepository<STProjectIndicator, ObjectIdentifier> {
//    Collection findByProjectReference(ObjectReference projectRef);
//    Collection findByPlanReference(ObjectReference planRef);
}
