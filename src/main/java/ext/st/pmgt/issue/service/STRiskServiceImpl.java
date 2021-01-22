package ext.st.pmgt.issue.service;


import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.pmgt.plan.model.AbstractPIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.risk.dao.PIPmgtRiskTypeDao;
import ext.st.pmgt.issue.dao.STProjectRiskDao;
import ext.st.pmgt.issue.model.STPIPlanActivityRiskLink;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
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

    @Override
    public Collection getProjectRisks(AbstractPIPlanActivity act) throws PIException {
        return PersistenceHelper.service.navigate(act,"roleB", STPIPlanActivityRiskLink.class,true);
    }
    @Override
    public Collection getAllProjectRisks(PIPlan plan) throws PIException {
        EntityManager em = PersistenceHelper.service.getEntityManager();
        List result = new ArrayList();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = cb.createQuery();
            Root root = criteriaQuery.from(STPIPlanActivityRiskLink.class);
            Root root2 = criteriaQuery.from(STProjectRisk.class);

            Path key = root.get("rootReference").get("key");
            Path key2 = root.get("roleBObjectRef").get("key");
            Path key3 = root2.get("objectIdentifier");

            criteriaQuery.select(root2).where(cb.equal(key, plan.getObjectIdentifier()),cb.equal(key2,key3));
            result = em.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //    /**
//     * 删除风险时 删除相关受影响部门、前置风险、措施详情
//     * @param stProjectRisk
//     */
//    @Override
//    public void deleteAssociationLink(STProjectRisk stProjectRisk) throws PIException {
//        STRiskHelper.linkService.deleteByRoleAObjectRef(ObjectReference.newObjectReference(stProjectRisk));
//        STProjectMeasuresHelper.measuresService.deleteByProjectRiskReference(ObjectReference.newObjectReference(stProjectRisk));
//
//        PICollection collection = getAllProjectIssues(stProjectRisk);
//        PersistenceHelper.service.delete(collection);
//    }
//
//
//    /**
//     * 该风险所有 RoleA/B   STProjectRiskPreRiskLink对象集合
//     * @param stProjectRisk
//     * @return
//     * @throws PIException
//     */
//    public PICollection getAllProjectIssues(STProjectRisk stProjectRisk) throws PIException {
//        PIArrayList result = new PIArrayList();
//        EntityManager em = PersistenceHelper.service.getEntityManager();
//        try {
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery criteriaQuery = cb.createQuery();
//            Root root = criteriaQuery.from(STProjectRiskPreRiskLink.class);
//            Path key1 = root.get("roleAObjectRef").get("key").get("id");
//            Path key2 = root.get("roleBObjectRef").get("key").get("id");
//            List<Predicate> predicates = new ArrayList<>();
//
//            Predicate p1 =(cb.equal(key1,stProjectRisk.getObjectIdentifier().getId()));
//            Predicate p2=(cb.equal(key2, stProjectRisk.getObjectIdentifier().getId()));
//            predicates.add(cb.or(p1, p2));
//
//            criteriaQuery.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
//
//            TypedQuery query = em.createQuery(criteriaQuery);
//            result.addAll(query.getResultList());
//            if (!CollectionUtils.isEmpty(result)){
//                return  result;
//            }
//        } catch (Exception e) {
//            throw new PIException(e);
//        } finally {
//            //em.close();
//        }
//        return null;
//    }
}
