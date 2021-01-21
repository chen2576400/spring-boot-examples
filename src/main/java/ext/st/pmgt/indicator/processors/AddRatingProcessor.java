package ext.st.pmgt.indicator.processors;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.Persistable;
import com.pisx.tundra.foundation.fc.service.ReferenceFactory;
import com.pisx.tundra.foundation.org.model.PIPrincipal;
import com.pisx.tundra.foundation.org.model.PIUser;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultObjectFormProcessor;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.assignment.PIAssignmentHelper;
import com.pisx.tundra.pmgt.assignment.model.PIResourceAssignment;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import ext.st.pmgt.indicator.STIndicatorHelper;
import ext.st.pmgt.indicator.dingTalk.DingTalkUtils;
import ext.st.pmgt.indicator.model.STProjectInstanceINIndicator;
import ext.st.pmgt.indicator.model.STProjectInstanceOTIndicator;
import ext.st.pmgt.indicator.model.STRating;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName AddRatingProcessor
 * @Description:
 * @Author hma
 * @Date 2020/11/9
 * @Version V1.0
 **/
@Component
public class AddRatingProcessor extends DefaultObjectFormProcessor {
    @Override
    public ResponseWrapper<?> doOperation(ComponentParams params, List list) throws PIException {
        boolean flag = havePrivilege(params);
        if (!flag) {//资源没有分配到任务 无法添加评定
            return new ResponseWrapper(ResponseWrapper.FAILED, "没有汇报的权限！", null);
        }

        Persistable sourceObject = params.getNfCommandBean().getSourceObject();
        STProjectInstanceOTIndicator ot = (STProjectInstanceOTIndicator) sourceObject;
        PIPlanActivity activity=null;
        STProjectInstanceINIndicator in = null;
        String[] parentPageOids = params.getParamMap().get("parentPageOid");
        if (parentPageOids != null && parentPageOids.length > 0) {
            String parentPageOid = parentPageOids[0];
             activity = (PIPlanActivity) new ReferenceFactory().getReference(parentPageOid).getObject();
            in = STIndicatorHelper.service.getInByOT(ot, activity);
        }


        if (in == null) {
            return new ResponseWrapper(ResponseWrapper.FAILED, "没有找到对应的in指标！", null);
        }
        Map<String, Object> layoutFields = params.getNfCommandBean().getLayoutFields();

        JSONObject obj = (JSONObject) layoutFields.get("otRating");
        Double otRating = Double.valueOf(obj.get("value").toString());
        String description = (String) layoutFields.get("description");
        STRating stRating = STRating.newSTRating();
        stRating.setOtRating(otRating);
        stRating.setDescription(description);
        stRating.setInIndicator(in);
        PIPrincipal principal = SessionHelper.service.getPrincipal();
        stRating.setRater((PIUser) principal);
        stRating.setReportTime(new Timestamp(System.currentTimeMillis()));
        STRating rating = PersistenceHelper.service.save(stRating);
//        保存评定汇报差异
        boolean b = STIndicatorHelper.service.saveSTProjectIndicatorReportDifference(in, rating);
//        如果汇报和评定发生差异，则发送钉钉消息

        if(b){
            PIUser reviewer = activity.getReviewer();
            if(reviewer!=null){
                try {
                    String useridBymobile = DingTalkUtils.getUseridBymobile(reviewer.getTelephone());
                    PIUser piUser = (PIUser)SessionHelper.service.getPrincipalReference().getObject();
                    String message=activity.getName()+"的输入指标："+in.getOtCode()+"，指标评定与汇报发生差异";
                    List list1 = new ArrayList();
                    OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
                    form.setKey("评定人:");
                    form.setValue(piUser.getFullName());
                    list1.add(form);
                    DingTalkUtils.sendOAMessage(useridBymobile,false,"IN指标评定","IN指标评定",
                            list1,message,null,null,"重汽精细化管理平台" );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
//        return new ResponseWrapper(ResponseWrapper.PAGE_FLUSH, "添加成功！", null);
        return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "添加成功！", null);
    }

    private boolean havePrivilege(ComponentParams params) throws PIException {
        String[] parentPageOids = params.getParamMap().get("parentPageOid");
        if (parentPageOids != null && parentPageOids.length > 0) {
            PIUser currentUser = (PIUser) SessionHelper.service.getPrincipal();
            String parentPageOid = parentPageOids[0];
            PIPlanActivity activity = (PIPlanActivity) new ReferenceFactory().getReference(parentPageOid).getObject();
            List<PIResourceAssignment> assignments = (List) PIAssignmentHelper.service.getResourceAssignments(activity);
            List<PIUser> users = assignments.stream().map(item -> item.getRsrc().getUser()).collect(Collectors.toList());
            if (!users.contains(currentUser)) {//资源没有分配到任务 无法汇报指标
                return false;
            }
        }
        return true;
    }
}