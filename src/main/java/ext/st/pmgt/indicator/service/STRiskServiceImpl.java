package ext.st.pmgt.indicator.service;


import com.pisx.tundra.foundation.fc.model.ObjectReference;

import com.pisx.tundra.foundation.util.PIException;

import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.risk.dao.PIPmgtRiskTypeDao;

import ext.st.pmgt.indicator.dao.STProjectRiskDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class STRiskServiceImpl implements STRiskService {
    @Autowired
    PIPmgtRiskTypeDao riskTypeDao;

    @Autowired
    STProjectRiskDao stProjectRiskDao;


    @Override
    public Collection getProjectRisks(PIProject project) throws PIException {
        Collection risks = stProjectRiskDao.findByProjectReference(ObjectReference.newObjectReference(project));
        return risks;

    }
}
