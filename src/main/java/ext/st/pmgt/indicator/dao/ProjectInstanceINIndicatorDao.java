package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * @ClassName ProjectInstanceINIndicatorDao
 * @Description:
 * @Author hma
 * @Date 2020/9/29
 * @Version V1.0
 **/
public interface ProjectInstanceINIndicatorDao extends JpaRepository<STProjectInstanceINIndicator, ObjectIdentifier> {
    Collection findByProjectReference(ObjectReference projectRef);
    Collection findByPlanReference(ObjectReference planRef);
    Collection findByPlanActivityReference(ObjectReference planActivityRef);
    Collection findByPlanActivityReferenceAndPlanReference(ObjectReference actRef,ObjectReference planRef);

}