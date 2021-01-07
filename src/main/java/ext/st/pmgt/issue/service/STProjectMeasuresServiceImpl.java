package ext.st.pmgt.issue.service;

import com.pisx.tundra.foundation.fc.model.ObjectReference;
import ext.st.pmgt.issue.dao.STProjectMeasuresDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class STProjectMeasuresServiceImpl implements  STProjectMeasuresService{
    @Autowired
    private STProjectMeasuresDao dao;
    @Override
    public Collection findByProjectRiskReference(ObjectReference riskReference) {
        return dao.findByProjectRiskReference(riskReference);
    }
}
