package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.model.ObjectReference;

import java.util.Collection;

public interface STProjectMeasuresService {
    Collection findByProjectRiskReference(ObjectReference riskReference);
//    void deleteByProjectRiskReference(ObjectReference riskReference);
}
