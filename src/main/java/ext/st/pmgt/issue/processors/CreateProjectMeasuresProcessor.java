package ext.st.pmgt.issue.processors;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.pisx.tundra.foundation.admin.AdministrativeDomainHelper;
import com.pisx.tundra.foundation.admin.model.AdminDomainRef;
import com.pisx.tundra.foundation.admin.model.AdministrativeDomain;
import com.pisx.tundra.foundation.content.model.ContentHolder;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.ObjectReference;
import com.pisx.tundra.foundation.inf.container.model.PIContainer;
import com.pisx.tundra.foundation.inf.team.ContainerTeamHelper;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeam;
import com.pisx.tundra.foundation.inf.team.model.ContainerTeamManaged;
import com.pisx.tundra.foundation.lifecycle.LifeCycleHelper;
import com.pisx.tundra.foundation.lifecycle.model.LifeCycleState;
import com.pisx.tundra.foundation.org.OrgHelper;
import com.pisx.tundra.foundation.org.model.*;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.foundation.util.PIPropertyVetoException;
import com.pisx.tundra.foundation.workflow.util.WorkflowUtil;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.netfactory.util.misc.StringUtils;
import com.pisx.tundra.pmgt.change.PIProjectChangeHelper;
import com.pisx.tundra.pmgt.project.model.PIProject;
import ext.st.pmgt.indicator.dingTalk.DingTalkUtils;
import ext.st.pmgt.issue.model.STProjectMeasures;
import ext.st.pmgt.issue.model.STProjectRisk;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.*;

@Component
public class CreateProjectMeasuresProcessor extends DefaultObjectFormProcessor {
    @SneakyThrows
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        STProjectRisk risk = (STProjectRisk) params.getNfCommandBean().getSourceObject();
        JSONObject jsonObject = params.getAjaxData().getJSONObject("componentsData").
                getJSONObject("create_project_measures_wizard_step1").
                getJSONObject("create_project_measures_layout").getJSONObject("fieldMeta");

        STProjectMeasures stProjectMeasures = getSTProjectMeasures(jsonObject, risk);
        PIPrincipalReference creator = PIPrincipalReference.newPIPrincipalReference(SessionHelper.service.getPrincipal());
        stProjectMeasures.setCreator(creator);

        Map<String, List<Part>> files = params.getFiles();
        List<Part> second = files.get("secondFile");//有数据都是新增的
        JSONObject ajaxData = params.getAjaxData();
        JSONObject componentsData = ajaxData.getJSONObject("componentsData");
        JSONObject createProjectmMasureStep2 = componentsData.getJSONObject("create_project_measure_step2");
        JSONObject contextHolderAttachmentsTable = createProjectmMasureStep2.getJSONObject("createoreditattachmentstable1");//编辑
        if (contextHolderAttachmentsTable == null) {
            contextHolderAttachmentsTable = createProjectmMasureStep2.getJSONObject("attachment_table");////创建
        }
        String rows = contextHolderAttachmentsTable.getJSONArray("rows").toJSONString();
        ContentHolder contentHolder = stProjectMeasures;

        PersistenceHelper.service.save(stProjectMeasures);
        PIProjectChangeHelper.service.addAndUpdateSecondData(contentHolder, creator, second, rows, null);

        //发送钉钉消息
        Set<PIUser> userSet =new HashSet<>();
        userSet.add(stProjectMeasures.getDutyUser());
        //得到项目经理
        PIContainer piContainer = ((PIProject)stProjectMeasures.getProjectReference().getObject()).getContainer();
        ContainerTeam containerTeam = ContainerTeamHelper.service.getContainerTeam((ContainerTeamManaged) piContainer);
        PIRole role = OrgHelper.service.getRoleByCode("PM", piContainer.getContainerReference());
        Enumeration teamPrincipalTarget = containerTeam.getPrincipalTarget(role);
        while (teamPrincipalTarget.hasMoreElements()) {
            PIPrincipalReference piPrincipalReference = (PIPrincipalReference) teamPrincipalTarget.nextElement();
            PIGroup group = (PIGroup) piPrincipalReference.getObject();
            Collection<PIUser> roleB = (List)PersistenceHelper.service.navigate(group, "roleB", MembershipLink.class, true);
            userSet.addAll(roleB);
        }
        userSet.removeAll(Collections.singleton(null));
        //得到需要发送消息的UseridList
        String userid = DingTalkUtils.getUseridList(userSet);

        //发送
        String message="风险："+((STProjectRisk)stProjectMeasures.getProjectRiskReference().getObject()).getRiskName()+",预防措施已创建，请尽快确认";
        PIUser piUser = (PIUser) SessionHelper.service.getPrincipalReference().getObject();
        List list1 = new ArrayList();
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        form.setKey("创建人:");
        form.setValue(piUser.getFullName());
        list1.add(form);
        DingTalkUtils.sendOAMessage(userid, false, "风险措施创建", "风险措施创建",
                list1, message, null, null, "重汽精细化管理平台");

        return new ResponseWrapper<>(ResponseWrapper.REGIONAL_FLUSH, null, null);
    }


    private STProjectMeasures getSTProjectMeasures(JSONObject object, STProjectRisk risk) throws PIException, PIPropertyVetoException {
        String name = null; //名称
        Boolean confirmStatus = false;  //项目经理确认
        Boolean involveGroupStatus = false; //涉及部门确认
        PIGroup involveGroup = null;//设计部门
        PIUser dutyUser = null;//责任人
        String precaution = null;//预防措施
        STProjectMeasures stProjectMeasures = STProjectMeasures.newSTProjectMeasures();

        if (object.get("name") != null) {
            name= object.get("name").toString();
        }
        if (object.get("precaution")!=null){
            precaution = object.get("precaution").toString();
        }

        String involveGroupOid = (object.getJSONObject("involveGroupReference").get("value").toString());
        if (StringUtils.isNotEmpty(involveGroupOid)) {
            involveGroup = (PIGroup) WorkflowUtil.getObjectByOid(involveGroupOid);
            stProjectMeasures.setInvolveGroupReference(ObjectReference.newObjectReference(involveGroup));
        }

        String dutyUserOid = (object.getJSONObject("dutyUserReference").get("value").toString());
        if (StringUtils.isNotEmpty(dutyUserOid)) {
            dutyUser = (PIUser) WorkflowUtil.getObjectByOid(dutyUserOid);
            stProjectMeasures.setDutyUserReference(ObjectReference.newObjectReference(dutyUser));
        }

        stProjectMeasures.setName(name);
        stProjectMeasures.setPrecaution(precaution);
        stProjectMeasures.setProjectReference(risk.getProjectReference());
        stProjectMeasures.setProjectRiskReference(ObjectReference.newObjectReference(risk));
        stProjectMeasures.setContainerReference(risk.getContainerReference());

        AdministrativeDomain domain = AdministrativeDomainHelper.service.getDomain("/Default", risk.getContainerReference());
        stProjectMeasures.setDomainRef(AdminDomainRef.newAdminDomainRef(domain));
        stProjectMeasures.setState(LifeCycleState.newLifeCycleState(LifeCycleHelper.service.getLcStates().get(1)));
        return stProjectMeasures;
    }
}



