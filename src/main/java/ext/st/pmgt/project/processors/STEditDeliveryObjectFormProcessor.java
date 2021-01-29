package ext.st.pmgt.project.processors;

import com.alibaba.fastjson.JSONObject;
import com.pisx.tundra.foundation.fc.PersistenceHelper;
import com.pisx.tundra.foundation.fc.model.PIObject;
import com.pisx.tundra.foundation.meta.type.model.LTDTyped;
import com.pisx.tundra.foundation.org.model.PIPrincipalReference;
import com.pisx.tundra.foundation.session.SessionHelper;
import com.pisx.tundra.foundation.util.PIException;
import com.pisx.tundra.netfactory.mvc.components.ComponentParams;
import com.pisx.tundra.netfactory.mvc.components.DefaultUpdateFormProcessor;
import com.pisx.tundra.netfactory.util.beans.NfCommandBean;
import com.pisx.tundra.netfactory.util.misc.ResponseWrapper;
import com.pisx.tundra.pmgt.common.util.CommonUtils;
import com.pisx.tundra.pmgt.deliverable.model.PIPlanDeliverable;
import com.pisx.tundra.pmgt.deliverable.resources.deliverableResource;
import com.pisx.tundra.pmgt.plan.model.PIPlanActivity;
import com.pisx.tundra.pmgt.plan.model.PIPlannable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * edit deliverable attribute and association object
 * @author wyu
 *
 */
@Component
public class STEditDeliveryObjectFormProcessor extends DefaultUpdateFormProcessor {

	private static final String RESOURCE = deliverableResource.class.getName();
	private static final Logger LOGGER = LoggerFactory.getLogger(STEditDeliveryObjectFormProcessor.class.getName());
	
	@Override
	public ResponseWrapper<?> doOperation(ComponentParams params, List objectBeanList) throws PIException {
		LOGGER.debug(" enter in postProcess...");

		PIPrincipalReference principal= SessionHelper.service.getPrincipalReference();
		//FormResult formResult =new FormResult(FormProcessingStatus.SUCCESS);
		boolean flag=false;
		try{		
			//flag=SessionServerHelper.manager.setAccessEnforced(flag);
			NfCommandBean commandBean  = params.getNfCommandBean();
			if(objectBeanList.size()>0){
				PIPlanDeliverable deliverable=(PIPlanDeliverable)objectBeanList.get(0);
				
				//get page parameters
				//HashMap map=commandBean.getParameterMap();
				Map<String,Object> map=commandBean.getLayoutFields();
				
				//String typeName="";
				//Iterator iter=map.keySet().iterator();
				//while(iter.hasNext()) {
				//	String key=(String)iter.next();
				//	if(key.contains("typeHiddenId___textbox")) {
				//		String [] value=(String[])map.get(key);
				//		typeName=value[0];
				//		break;
				//	}
				//}
				//JSONObject deliverJson = (JSONObject) map.get("deliverableTypeReference");
				JSONObject subjectJson = (JSONObject) map.get("subjectReference");
	
				//String [] subject=(String[])map.get(CommonConstants.CONS_PICKER_SUBJECT_HIDDEN_ID);
				//String subjectOid=subject[0];
				String subjectOid= subjectJson.getString("value");
				//typeName = deliverJson.getString("value");

				//set subject object
				LTDTyped subObject=null;
				if(subjectOid!=null&&subjectOid.length()>0) {
					//NmOid nmOid=NmOid.newNmOid(subjectOid);
					//subObject=(WTObject)nmOid.getRefObject();
					subObject = (LTDTyped) CommonUtils.getPIObjectByOid(subjectOid);
					
					//TypeIdentifier partTypeidentifier = TypeIdentifierUtility.getTypeIdentifier(subObject);
					//String subTypeName = partTypeidentifier.getTypename();
					//String subTypeName = subObject.getLTDTypeDefinition().getName();
					//LOGGER.debug(" ###  edit deliverable subject object, subTypeName: "+subTypeName+"  typeName: "+typeName);
					//if (!subTypeName.contains(typeName)) {
					//	String error = PIMessage.getLocalizedMessage(RESOURCE, "THE_OBJECT_TYPE_IS_NOT_CONSISTENT_WITH_THE_DELIVERABLE_TYPE",
					//			null, SessionHelper.service.getLocale());
					//	throw new PIException(error);
					//}
				}
				deliverable.setSubject((PIObject) subObject);
				deliverable.setModifier(principal);
				
				//save deliverable
				PersistenceHelper.service.save(deliverable);
				
				PIPlannable plannable=deliverable.getParent();
				if(plannable instanceof PIPlanActivity){
					PIPlanActivity activity=(PIPlanActivity)plannable;
					activity.setModifier(principal);
					PersistenceHelper.service.save(activity);
				}
				
				//formResult.setNextAction(FormResultAction.REFRESH_OPENER);
			}else{
				throw new PIException(" get deliverable object error,refresh the page and try again");
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new PIException(e.toString());
		}finally{
			 //SessionServerHelper.manager.setAccessEnforced(flag);
		}
		LOGGER.debug(" Exist out postProcess...");
		return new ResponseWrapper(ResponseWrapper.REGIONAL_FLUSH, "", null);
		//return formResult ;
	}
	
	

}
