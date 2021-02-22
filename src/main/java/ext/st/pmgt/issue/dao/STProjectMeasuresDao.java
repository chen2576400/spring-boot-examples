package ext.st.pmgt.issue.dao;

import com.pisx.tundra.foundation.fc.model.ObjectIdentifier;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.model.STProjectMeasures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface STProjectMeasuresDao extends JpaRepository<STProjectMeasures, ObjectIdentifier> {
    Collection findByProjectRiskReference(ObjectReference riskReference);

    @Query(value = "select s from STProjectMeasures s where s.confirmStatus=false")
    List<STProjectMeasures> getNotRecognizedMeasures();
}
