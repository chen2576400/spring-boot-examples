package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.indicator.model.STProCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProCompetenceDao extends JpaRepository<STProCompetence, ObjectIdentifier> {
    STProCompetence findByNameEquals(String name);
    STProCompetence findByDepartmentReference(ObjectReference departmentReference);
}
