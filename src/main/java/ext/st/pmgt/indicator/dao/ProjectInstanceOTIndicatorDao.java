package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface ProjectInstanceOTIndicatorDao extends JpaRepository<STProjectInstanceOTIndicator, ObjectIdentifier> {
    Collection findByProjectReference(ObjectReference projectRef);
    Collection findByPlanReference(ObjectReference planRef);
    Collection findByDeliverableTypeCodeAndPlanActivityReference(String deliverableTypeCode,ObjectReference actRef);
    Collection findByPlanActivityReference(ObjectReference planActivityRef);
    Collection findByPlanDeliverableReference(ObjectReference planDeliverableRef);
    Collection findByPlanActivityReferenceAndPlanReference(ObjectReference planActivityRef,ObjectReference planRef);
    STProjectInstanceOTIndicator findByCode(String name);
}
