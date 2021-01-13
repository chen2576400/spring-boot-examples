package ext.st.pmgt.issue.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.admin.AdministrativeDomainHelper;
import com.pisx.tundra.foundation.admin.model.AdminDomainRef;
import com.pisx.tundra.foundation.admin.model.AdministrativeDomain;
import com.pisx.tundra.foundation.content.model.ContentHolder;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.inf.container.model.PIContainerRef;
import com.pisx.tundra.foundation.lifecycle.LifeCycleHelper;
import com.pisx.tundra.foundation.lifecycle.model.LifeCycleState;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultCreateFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.change.PIProjectChangeHelper;
import com.pisx.tundra.pmgt.plan.model.PIPlan;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.project.PIProjectHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import com.pisx.tundra.pmgt.project.model.PIProjectContainer;
import ext.st.pmgt.issue.model.STProjectIssue;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
@Component("createProjectIssueProcessorCopy")
public class CreateProjectIssueProcessor extends DefaultCreateFormProcessor {

    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        List<STProjectIssue> riskList = (List<STProjectIssue>) list.stream().filter(item -> item instanceof STProjectIssue).collect(Collectors.toList());
        STProjectIssue issue = riskList.get(0);
        Persistable contextObj = params.getNfCommandBean().getSourceObject();
        PIPlanActivity act = null;
        PIProject piProject = null;
        PIContainerRef ref = null;
        if (contextObj instanceof PIProject) {
            piProject = (PIProject) contextObj;
            ref = piProject.getContainerReference();
        } else if (contextObj instanceof PIProjectContainer) {
            PIProjectContainer piProjectContainer = (PIProjectContainer) contextObj;
            piProject = PIProjectHelper.service.getProjectFromContainer(piProjectContainer);
            ref = piProject.getContainerReference();
        } else if (contextObj instanceof PIPlanActivity) {
            act = (PIPlanActivity) contextObj;
            piProject = act.getProject();
            ref = piProject.getContainerReference();
        }else if (contextObj instanceof PIResourceAssignment) {
            PIResourceAssignment assignment = (PIResourceAssignment) contextObj;
            piProject = assignment.getProject();
            ref = piProject.getContainerReference();
        }else if(contextObj instanceof PIPlan) {
            PIPlan plan=(PIPlan)contextObj;
            piProject=plan.getProject();
            ref=piProject.getContainerReference();
        }

        AdministrativeDomain domain = AdministrativeDomainHelper.service.getDomain("/Default", ref);
        issue.setDomainRef(AdminDomainRef.newAdminDomainRef(domain));

        PIUser owner = piProject.getOwner();
        PIPlan piPlan = null;
        if (act != null) {
            piPlan = (PIPlan) act.getRootReference().getObject();
        }
        issue.setProject(piProject);
        if (piPlan!=null){
            issue.setRoot(piPlan);
        }
        issue.setResponsibleUser(owner);
        PIPrincipalReference creator = PIPrincipalReference.newPIPrincipalReference(SessionHelper.service.getPrincipal());
        issue.setCreator(creator);

        //todo 部分字段页面没有填入但不能为空，先固定
        issue.setState(LifeCycleState.newLifeCycleState(LifeCycleHelper.service.getLcStates().get(2)));
        issue.setIssueNumber("PISU000000" + new Random().nextInt(1000));


        Map<String, List<Part>> files = params.getFiles();
        List<Part> second = files.get("secondFile");//有数据都是新增的
        JSONObject ajaxData = params.getAjaxData();
        JSONObject componentsData = ajaxData.getJSONObject("componentsData");
        JSONObject createProjectRiskStep2 = componentsData.getJSONObject("create_project_issue_step2");
        JSONObject contextHolderAttachmentsTable = createProjectRiskStep2.getJSONObject("createoreditattachmentstable1");
        if (contextHolderAttachmentsTable == null) {
            contextHolderAttachmentsTable = createProjectRiskStep2.getJSONObject("attachment_table");////创建
        }
        String  rows = contextHolderAttachmentsTable.getJSONArray("rows").toJSONString();

        issue = (STProjectIssue) PersistenceHelper.service.save(issue);
        ContentHolder contentHolder=issue;
        PIProjectChangeHelper.service.addAndUpdateSecondData(contentHolder,creator, second,rows,null);
        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, null, null);
    }
}
