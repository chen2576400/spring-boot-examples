package com.st.pmgt.indicator.service;

import com.pisx.tundra.foundation.fc.collections.PIArrayList;
import com.pisx.tundra.foundation.fc.collections.PICollection;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.st.pmgt.indicator.dao.ProjectIndicatorDao;
import com.st.pmgt.indicator.dao.ProjectInstanceINIndicatorDao;
import com.st.pmgt.indicator.dao.ProjectInstanceOTIndicatorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @ClassName STServiceImpl
 * @Description:
 * @Author hma
 * @Date 2020/9/29
 * @Version V1.0
 **/
@Service
public class STIndicatorServiceImpl implements STIndicatorService {
    @Autowired
    private ProjectIndicatorDao projectIndicatorDao;

    @Autowired
    private ProjectInstanceINIndicatorDao projectINIndicatorDao;

    @Autowired
    private ProjectInstanceOTIndicatorDao projectOTIndicatorDao;


    @Override
    public PICollection findProjectINIndicatorByProject(PIProject project) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectINIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(project)));
        return result;
    }

    @Override
    public PICollection findProjectINIndicatorByPlan(PIPlan plan) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectINIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(plan)));
        return result;
    }

    @Override
    public PICollection findProjectINIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectINIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(planActivity)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByProject(PIProject project) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(project)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByPlan(PIPlan plan) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(plan)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByPlanActivity(PIPlanActivity planActivity) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByProjectReference(ObjectReference.newObjectReference(planActivity)));
        return result;
    }

    @Override
    public PICollection findProjectOTIndicatorByPlanDeliverable(PIPlanDeliverable planDeliverable) throws PIException {
        PIArrayList result = new PIArrayList();
        result.addAll(projectOTIndicatorDao.findByPlanDeliverableReference(ObjectReference.newObjectReference(planDeliverable)));
        return result;
    }
}