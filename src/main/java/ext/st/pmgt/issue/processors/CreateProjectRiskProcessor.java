package ext.st.pmgt.issue.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.admin.AdministrativeDomainHelper;
import com.pisx.tundra.foundation.admin.model.AdminDomainRef;
import com.pisx.tundra.foundation.admin.model.AdministrativeDomain;
import com.pisx.tundra.foundation.content.dao.ApplicationDataDao;
import com.pisx.tundra.foundation.content.model.ContentHolder;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.lifecycle.LifeCycleHelper;
import com.pisx.tundra.foundation.lifecycle.model.LifeCycleState;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIMessage;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultCreateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.change.PIProjectChangeHelper;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import com.pisx.tundra.pmgt.risk.model.PIPlanActivityRiskLink;
import com.pisx.tundra.pmgt.risk.resources.riskResource;
import ext.st.pmgt.issue.model.STPIPlanActivityRiskLink;
import ext.st.pmgt.issue.model.STProjectRisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component("createProjectRiskProcessorCopy")
public class CreateProjectRiskProcessor extends DefaultCreateFormProcessor {
    private static final String RESOURCE = riskResource.class.getName();
    @Autowired
    private ApplicationDataDao applicationDataDao;

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        List<STProjectRisk> riskList = (List<STProjectRisk>) list.stream().filter(item -> item instanceof STProjectRisk).collect(Collectors.toList());
        STProjectRisk risk = riskList.get(0);
        PIContainerRef ref = null;
        PIProject piProject = null;
        PIPlanActivity pipa = null;


        Map<String, List<Part>> files = params.getFiles();
        List<Part> second = files.get("secondFile");//有数据都是新增的
        JSONObject ajaxData = params.getAjaxData();
        JSONObject componentsData = ajaxData.getJSONObject("componentsData");
        JSONObject createProjectRiskStep2 = componentsData.getJSONObject("create_project_risk_step2");
        JSONObject contextHolderAttachmentsTable = createProjectRiskStep2.getJSONObject("createoreditattachmentstable1");
        if (contextHolderAttachmentsTable == null) {
            contextHolderAttachmentsTable = createProjectRiskStep2.getJSONObject("attachment_table");////创建
        }
        String  rows = contextHolderAttachmentsTable.getJSONArray("rows").toJSONString();

        PIPrincipalReference creator = SessionHelper.service.getPrincipalReference();


        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        if (sourceObject instanceof PIProjectContainer) {
            piProject = PIProjectHelper.service.getProjectFromContainer((PIContainer) sourceObject);
            ref = piProject.getContainerReference();
        } else if (sourceObject instanceof PIProject) {
            piProject = (PIProject) sourceObject;
            ref = piProject.getContainerReference();
        } else if (sourceObject instanceof PIPlanActivity) {
            pipa = (PIPlanActivity) sourceObject;
            piProject = pipa.getProject();
            ref = piProject.getContainerReference();
        } else if (sourceObject instanceof PIPlan) {
            throw new PIException(PIMessage.getLocalizedMessage(RESOURCE, "ENCODING_ERR_FOR_EDIT"));
        }

        PIPrincipalReference ownerRef = piProject.getOwnership().getOwner();
        AdministrativeDomain domain = AdministrativeDomainHelper.service.getDomain("/Default", ref);
        risk.setDomainRef(AdminDomainRef.newAdminDomainRef(domain));
        risk.setProject(piProject);
        risk.setIdentifiedByReference(ownerRef);

        risk.setCreator(PIPrincipalReference.newPIPrincipalReference(creator));
        risk.setModifier(PIPrincipalReference.newPIPrincipalReference(creator));

        //todo state暂时随机
        risk.setRiskCode("RISK_"+System.currentTimeMillis());
        risk.setState(LifeCycleState.newLifeCycleState(LifeCycleHelper.service.getLcStates().get(3)));
        ///////////

        risk = (STProjectRisk) PersistenceHelper.service.save(risk);

        if(pipa!=null) {
            STPIPlanActivityRiskLink riskLink = STPIPlanActivityRiskLink.newPIPlanActivityRiskLink(pipa, risk);
            riskLink = PersistenceHelper.service.save(riskLink);
        }
        ContentHolder contentHolder=risk;
        PIProjectChangeHelper.service.addAndUpdateSecondData(contentHolder,creator, second,rows,null);
        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, null, null);
    }
}
