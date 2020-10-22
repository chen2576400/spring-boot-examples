package ext.st.pmgt.indicator.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import ext.st.pmgt.indicator.model.STProCompetence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProCompetenceDao extends JpaRepository<STProCompetence, ObjectIdentifier> {
    STProCompetence findByNameEquals(String name);
}
