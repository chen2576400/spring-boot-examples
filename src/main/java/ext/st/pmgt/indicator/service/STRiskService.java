package ext.st.pmgt.indicator.service;

import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.project.model.PIProject;
import java.util.Collection;
public interface STRiskService {

    Collection getProjectRisks(PIProject project) throws PIException;


}
