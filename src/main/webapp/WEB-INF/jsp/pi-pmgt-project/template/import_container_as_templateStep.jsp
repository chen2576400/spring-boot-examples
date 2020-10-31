<%--<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/wrappers" prefix="w"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt"%>--%>
<%@ page pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>			
<%@page import="com.pisx.tundra.pmgt.project.projectResource"%>

					

<%--<fmt:setLocale value="${localeBean.locale}"/>
<fmt:setBundle basename="com.pisx.pmgt.project.projectResource" />
<fmt:message var="CONTAINER"       key="CONTAINER" />
<fmt:message var="ATTRIBUTE"       key="ATTRIBUTE" />
<fmt:message var="SELECT_FILE_IMPORT"    key="SELECT_FILE_IMPORT"/>
<fmt:message var="IMPORT_FILE_TYPES"    key="IMPORT_FILE_TYPES"/> --%>

<%! private static final String OBJECT_IMPORTSTEP_RESOURCE = "com.pisx.pmgt.project.projectResource";%>
<%
//   ResourceBundle objectRb = ResourceBundle.getBundle(OBJECT_IMPORTSTEP_RESOURCE, localeBean.getLocale());
//	 NmCommandBean cb = new NmCommandBean();
//   cb.setCompContext(nmcontext.getContext().toString());
//      todo 需要初始化的隐藏参数，暂不知道从哪里获取，以及缺包
//   ArrayList<String> innerNameList =new ArrayList<String>();
//	 ArrayList<String> dispNameList = new ArrayList<String>();
//	 ArrayList<String> selectedList = new ArrayList<String>();
//	 boolean flag=false;
//	 try{
//			 flag=SessionServerHelper.manager.setAccessEnforced(flag);
//	     QuerySpec spec=new  QuerySpec(WTOrganization.class);   WTOrganization实体类保留
//	     QueryResult qr=PersistenceHelper.manager.find(spec);
//			 while(qr.hasMoreElements()){
//					WTOrganization organization=(WTOrganization)qr.nextElement();
//					String oid=OidHelper.getNmOid(organization).toString();
//					String name=organization.getName();
//					innerNameList.add(oid);
//					dispNameList.add(name);
//					selectedList.add(name);
//			 }
//    }catch(Exception e){
//    	e.printStackTrace();
//    }finally{
//			 SessionServerHelper.manager.setAccessEnforced(flag);
//		}
   
%>

<%--<c:set var="iValues" value="<%=innerNameList%>"/>--%>
<%--<c:set var="dValues" value="<%=dispNameList%>"/>--%>
<%--<c:set var="sValues" value="<%=selectedList%>"/>--%>


<fieldset class="x-fieldset x-form-label-left" id="Visualization_and_Attributes" style="width:95%;height:40%">
<legend><font class="wizardlabel"  size='2'>${ATTRIBUTE}</font></legend>		
<div id="SaveAsDiv">
<table border="0" cellspacing="3" cellpadding="3">
      
   <tr valign=top>
      <td align="left" valign="top" nowrap><FONT>*</FONT>
      	<FONT >container:</font>
      </td>
      <td align="left" valign="top" colspan=3>
          <w:comboBox name="organizationCombox" id="organizationCombox" required="true" size="3" internalValues="${iValuesiValues}"  displayValues="${dValues}" selectedValues="${sValues}"/>
      </td>
   </tr>
   
	<tr valign=top>
      <td align="left" valign="top" nowrap>
        <font >*</font>
        <font >
           file
        </font>
     </td>
     <td align="left" valign="top" nowrap>
        <input name="file" type="file" size="27">
        <br>
        <font >
            (XML,ZIP)
        </font>
      </td>
   </tr>
   
</table>
</div>
</fieldset>

<style type="text/css">
	td{padding-bottom:10px}
	select{width:220px}
</style>


<script language="JavaScript">

   <%--var FILE_ERROR_MSG = "<%=NmAction.escapeJsMessage(objectRb.getString(projectResource.IMPORT_FILE_ERROR))%>";--%>
 // setUserSubmitFunction(validateImportFile);
   function validateImportFile() {
     var fileName = getMainForm().file.value;
     if (fileName) {
        var fileNameLen = fileName.length;
        if (fileNameLen > 4) {
           var lastFour = fileName.substring(fileNameLen - 4);
           if (!(lastFour == ".xml"||lastFour==".zip")) {
              // wfalert(FILE_ERROR_MSG);
               alert("文件类型异常，必须是xml或zip文件");
              return false;
           }
        }
     }else { // A file was not selected.
        // alert(FILE_ERROR_MSG);
         alert("未选中文件，请重新选择");
        return false;
     }
     return true;
  }
</script>

 
<%--
<%@ include file="/netmarkets/jsp/util/end.jspf"%>--%>
