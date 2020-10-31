<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="wca"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w"%>

<fmt:setLocale value="${localeBean.locale}" />
<fmt:setBundle basename="com.pisx.pmgt.plan.plannableResource" />

<wca:wizard type="clerk" title="" >
  	 <wca:wizardStep action="primaryAttributes2" type="pi-pmgt-act"/>
    <wca:wizardStep action="attachments"               type="pi-pmgt-enterprise"/>
    <wca:wizardStep action="resourceAssignments" type="pi-pmgt-act"/>
    <wca:wizardStep action="relationships" type="pi-pmgt-act"/>
     <wca:wizardStep action="resourceHours" type="pi-pmgt-act"/>
     <wca:wizardStep action="listDeliverablesForActivity" type="pi-pmgt-act"/>
     <wca:wizardStep action="relatedReferenceDocuments"  type="pi-pmgt-act"/>
     <wca:wizardStep action="planActivityExpenses" type="pi-pmgt-act"/>
     <wca:wizardStep action="baselines" type="pi-pmgt-act"/>
     <wca:wizardStep action="plannableCostSummary" type="pi-pmgt-cost"/>  
     <wca:wizardStep action="listProjectIssuesForActivity" type="pi-pmgt-act"/>
     <wca:wizardStep action="listProjectChangeRequestsForActivity" type="pi-pmgt-act"/>
     <wca:wizardStep action="listProjectRisksForAct" type="pi-pmgt-risk"/>
     <wca:wizardStep action="listPlanActivityShareLinksForSource" type="pi-pmgt-act"/>   	
</wca:wizard>


<%@ include file="/netmarkets/jsp/util/end.jspf"%>

<style text="text/css">
#wiz_step_contents {
    width: 100%;
    height: 600px;
}
	#titleBar{
	display:none
}
.x-toolbar-right td .x-form-field-trigger-wrap{
		width:170px !important;
}
.ext-strict .frame_outer .x-small-editor input.x-form-text{
	width:155px !important;
}


	</style>
	
<script language="javascript" type="text/javascript">
 Ext.onReady(function(){
 		
 			document.getElementById('PJL_wizard_ok').style.display="none";;
 			document.getElementById('PJL_wizard_cancel').style.display="none";
 		
 			document.title="";
 	})

	</script>	
