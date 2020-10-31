<%@page import="java.util.*" %>

<%! private static final String OBJECT_IMPORTSTEP_RESOURCE = "com.pisx.pmgt.project.projectResource";%>
<%
   //ResourceBundle objectRb = ResourceBundle.getBundle(OBJECT_IMPORTSTEP_RESOURCE, localeBean.getLocale());
	// NmCommandBean cb = new NmCommandBean();
   //cb.setCompContext(nmcontext.getContext().toString());
   
%>


<fieldset class="x-fieldset x-form-label-left" id="Visualization_and_Attributes" style="width:95%;height:30%">
<legend><font class="wizardlabel"  size='2'>${ATTRIBUTE}</font></legend>		
<div id="SaveAsDiv">

<table border="0" cellspacing="3" cellpadding="3">
   
	<tr valign=top>
      <td align="left" valign="top" nowrap>
        <font class=requiredfield>*</font>
        <font class=wizardlabel>
           ${SELECT_FILE_IMPORT}
        </font>
     </td>
     <td align="left" valign="top" nowrap>
        <input name="file" type="file" size="27" id="file1" onchange="file_onselect()"/>
        <br>
        <font class="wizardlabel">
          ${IMPORT_PLAN_FILE_TYPES}
        </font>
      </td>
     
   </tr>
   
</table>

<input type="hidden" id="checkResult" name="checkResult" value="OK"/>
<input type="hidden" id="checkErrorInfo" name="checkErrorInfo" value=""/>
<!--<input type="hidden" id="checkNext" name="checkNext" value=""/>-->

</div>
</fieldset>

<style type="text/css">
	td{padding-bottom:10px}
	select{width:220px}
</style>


<script language="JavaScript">
	        
   <%--var FILE_ERROR_MSG = "<%=NmAction.escapeJsMessage(objectRb.getString(projectResource.IMPORT_PLAN_FILE_ERROR))%>";--%>
   <%--var CLICK_NEXT_BUTTON = "<%=NmAction.escapeJsMessage(objectRb.getString(projectResource.CLICK_NEXT_BUTTON_FIRST))%>";--%>

   setUserSubmitFunction(validateImportFile);
   
   function validateImportFile() {
   	   var fileName = jQuery('#file1').val();
       if (fileName) {
       var fileNameLen = fileName.length;
       if(fileNameLen > 4) {
           var lastFour = fileName.substring(fileNameLen - 4);
           if (!(lastFour === ".mpp")) {
              wfalert(FILE_ERROR_MSG);
              return false;
           }else{		         		  
         		  //alert( document.getElementById('checkResult').value);
           }
        }
       }else {
       	  wfalert(FILE_ERROR_MSG);
        	return false;
      }
     	return true; 
   }
   
   function checkMPPResult(value){
   		if(value=="OK"){
   			 document.getElementById('checkResult').value="";
   			 onSubmit(); 
   		}else{
   			if(confirm(value)){
   				 document.getElementById('checkResult').value="";
					 onSubmit(); 
			  }else{
			  	window.close();
			  }
   		}  		
			return true;
   }
   
   function openErrorInfoPage(value){
   	  document.getElementById('checkErrorInfo').value=value;
   	  window.open("${pageContext.request.contextPath}/app/netmarkets/jsp/pi-pmgt-plan/importPlanError.jsp", "newwindow", "height=220, width=500,top=100,left=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no");		
		  return false;
   }


	function file_onselect(){
		 document.getElementById('checkResult').value="OK";
	}
   

  
</script>
