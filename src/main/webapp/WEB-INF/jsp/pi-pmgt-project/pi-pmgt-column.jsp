<%@ page import="com.pisx.tundra.foundation.org.model.PIUser" %>
<%@ page import="com.pisx.tundra.foundation.session.SessionHelper" %>
<%@ page pageEncoding="UTF-8" %>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme() + "://" 
+ request.getServerName() + ":" + request.getServerPort() 
+ path + "/"; 
String version="20191208";
String projectIsBaseline=request.getParameter("projectIsBaseline");
boolean bool = false;
PIUser user = (PIUser) SessionHelper.service.getPrincipal();
//if(PIPrincipalHelper.service.isSiteAdministrator(user)||PIPrincipalHelper.service.isOrganizationAdministrator(user)) {
	bool = true;
//}
%> 
<input type="hidden" name="isAD" id="isAD" value="<%=bool%>"/>
<%@ include file="../../../resources/pi-pmgt-common/globalization.jsp" %>
<!DOCTYPE HTML>
<html manifest="">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<script language="javascript" type="text/javascript">
	   var GanttView=false,lockedDataIndex='';
	   if(!window.localStorage){
        alert('浏览器版本过低，建议升级浏览器！')
      }else{
          lockedDataIndex=localStorage.getItem("lockedDataIndex");
      }
      var projectIsBaseline=("<%=projectIsBaseline%>"==='true'?true:false);
</script>
    <link href="../pi-pmgt-common/js/build/development/PIProjectManager/resources/PIProjectManager-all.css?version=<%=version%>" rel="stylesheet" type="text/css"/>
    <link href="../pi-pmgt-common/js/resources/extgantt/css/sch-gantt-all-debug.css?version=<%=version%>" rel="stylesheet" type="text/css" /> 
     <link href="../pi-pmgt-common/js/resources/extgantt/css/gantt-scheduler.css?version=<%=version%>" rel="stylesheet" type="text/css" />
    <link href="../pi-pmgt-common/js/resources/extgantt/css/style.css?version=<%=version%>" rel="stylesheet" type="text/css" /> 
    <script src="../pi-pmgt-common/js/resources/language/<%=locallang%>.js?version=<%=version%>"></script>
     <script src="../pi-pmgt-common/js/resources/js/PmgtGolbal.js?version=<%=version%>" type="text/javascript"></script>
    <script src="../pi-pmgt-common/js/ext/build/ext-all-rtl-debug-column.js?version=<%=version%>"></script>
     <script src="../pi-pmgt-common/js/resources/language/locale-<%=locallang%>.js?version=<%=version%>"></script>
     <script src="../pi-pmgt-common/js/resources/extgantt/js/ResourceGrid.js?version=<%=version%>"></script>
     <script src="../pi-pmgt-common/js/resources/extgantt/js/PmgtMultiSelectorSearch.js?version=<%=version%>"></script>
     <script src="../pi-pmgt-common/js/resources/extgantt/js/PmgtTaskEditor.js?version=<%=version%>"></script>
     <script src="../pi-pmgt-common/js/resources/extgantt/js/CellDragDrop.js?version=<%=version%>"></script>
    <script src="../pi-pmgt-common/js/resources/extgantt/js/gantt4-column.js?version=<%=version%>" type="text/javascript"></script>
	  <script src="../pi-pmgt-common/js/resources/extgantt/js/pi-customized-gantt.js?version=<%=version%>" type="text/javascript"></script>
    <script src="../pi-pmgt-common/js/resources/extgantt/js/Sch/locale/zh_CN.js?version=<%=version%>"></script>
    <script src="../pi-pmgt-common/js/resources/extgantt/js/Gnt/locale/zh_CN.js?version=<%=version%>"></script>  
     <script src="../pi-pmgt-common/js/resources/js/jquery.2.1.1.min.js?version=<%=version%>"></script>
    <script src="../pi-pmgt-common/js/app.js?version=<%=version%>"></script>
	<style type="text/css">
	.pi-tbar-backgroundcolor{
	  background-color: #fff;
	}
	.pi-rownumberer{
	line-height:15px;
	}
	/**������ʽ**/
	#pmgt-calendar .x-datepicker-arrow{
	 height:17px;
	}
	#pmgt-calendar .x-datepicker-month .x-btn{
	 padding: 0;
	}
	#pmgt-calendar .x-datepicker-column-header-inner{
	line-height: 28px;
	}
	#pmgt-calendar .x-datepicker-date{
	line-height: 28px;
	}
	#pmgt-calendar .x-datepicker-footer, .x-monthpicker-buttons{
	padding:0;
	}
	#pmgt-calendar .x-btn-default-small{
	    padding: 2px 0px 2px 0px;
	}
	#pmgt-calendar .x-monthpicker-item{
	    margin:0;
	}
	#pmgt-calendar .x-monthpicker-item-inner{
	    margin:0;
	}
	#pmgt-calendar .x-monthpicker-yearnav{
	    height:37px;
	}
	#pmgt-calendar-legend{
        top:237px !important;	
	}
	
	.pmgt-text-span-warper{
	 width:120px;
	 z-index:11;
	 position: absolute;
	 white-space:nowrap;
	 overflow:hidden;
	 line-height: 13px;
	 text-overflow:ellipsis;
	}
    .pmgt-text-span-warper:hover {
    text-overflow: clip;
    width:400px;
    font-weight: 700;
    text-shadow: 1px 0 0 yellow, 0 1px 0 yellow, 
		    0 -1px 0 yellow, -1px 0 0 yellow, 
		    1px 0 1px yellow, 0 1px 1px yellow, 
		    0 -1px 1px yellow, -1px 0 1px yellow;
    }
   
    
    #resourceChartType .x-form-text-default{
    	padding:5px 10px 1px;
    }
    #resourceChartType .x-form-item-label-default{
    	padding-top: 7px;
    }
    
    #single_roject_resources_table_chart .x-grid-cell-inner-treecolumn{
    	padding: 8px 10px 6px 6px;
    }
    #mainGanttPanel .x-tree-icon{
    	width: 0;
    }
    #mainGanttPanel .x-tree-icon-parent-expanded:before{
    	    content: "";
    }
    #mainGanttPanel .x-tree-icon-leaf:before{
    	content: "";
    }
    #mainGanttPanel .x-tree-icon-parent:before{
    	content: "";
    }
    #mainGanttPanel .x-tree-node-text{
    	padding-left: 0;
    	display: inline-block;
    }
    #mainGanttPanel .x-tree-elbow-img{
    	margin-right: 0;
    }
    #mainGanttPanel .x-grid-cell-inner-treecolumn {
     padding: 5px 10px 7px 0px;
    }
    /*.gnt-assignmentgrid .x-grid-cell-row-checker .x-grid-cell-inner{
    padding: 7px 4px 0px 7px;
    }*/
    .gnt-assignmentgrid .x-btn-default-toolbar-small{
        padding: 2px 7px 2px 7px;
    }
    .gnt-assignmentgrid .x-grid-cell-inner-action-col {
		    padding: 0px 4px 0px 4px;
		}
    .gnt-assignmentgrid .x-grid-row-checker, .x-column-header-checkbox .x-column-header-text{
    	   height: 20px;
         width: 23px;
    }
    /*.pmgt-custom-grid-row .x-grid-cell-row-checker .x-grid-cell-inner{
    	 padding: 7px 4px 0px 7px;
    }*/
    .single_project_sub_resources_tree_lineHeight{
    	  height:28px;
    }
    .pi-pmgt-topPriorityType{
    	box-shadow: 0px 0px 20px yellow inset;
    }
    .currentProjectPercent{
    	position:absolute;background:#8282de;bottom:0;
    }
    .resourcegrid-cls .x-form-cb-default,.x-form-cb-label-default{
    	margin-top: 2px;
    }
	</style>
    
</head>
<body>
<input type="hidden" id="pi_project_basePath" value="<%=basePath%>">
<script language="javascript" type="text/javascript">
		function translateSpecialChar(str) {
		    var escapseResult = str;
		    var jsSpecialChars = ["."];
		    var jquerySpecialChars = [":"];
		    for (var i = 0; i < jsSpecialChars.length; i++) {
		        escapseResult = escapseResult.replace(new RegExp("\\" + jsSpecialChars[i], "g"), "\\"+ jsSpecialChars[i]);
		    }
		    for (var i = 0; i < jquerySpecialChars.length; i++) {
		        escapseResult = escapseResult.replace(new RegExp(jquerySpecialChars[i],
		                "g"), "\\" + jquerySpecialChars[i]);
		    }
		    return escapseResult;
		}
		
    // var targetNode=window.parent.document.getElementById("infoPage_myTab_pi-pmgt-project_planTree2");
		var iframe=window.parent.document.getElementById("windchill_gantt");
    // var targetParent=targetNode.parentNode,iframeWidth=targetNode.style.width,lockGridWidth=parseInt(iframeWidth.replace('px',''));
  	// iframe.style.height=targetParent.style.height;
        //iframe.style.height=window.parent.document.getElementById("p_i_plan_web_view_builder").style.height;
        iframe.style.height="800px";
        // window.parent.document.getElementById("pi_content").style.height = "600px";
        // window.parent.document.getElementById("pi_pmgt_plan_info_page_tab_set").style.height = "550px";
        // document.getElementById("p_i_plan_web_view_builder").style.height = "510px";

    // iframe.style.width=iframeWidth;
    //添加固定列
  	var lockedConfig=localStorage.getItem("lockedConfig");
		lockedConfig=lockedConfig?lockedConfig.split(','):[];
		if(lockedConfig.length!=0){
			lockGridWidth=null;
			lockedDataIndex=lockedDataIndex?lockedConfig[lockedConfig.length-1]:'';
		}
		
    var pi_project_basePath=document.getElementById("pi_project_basePath").value;
	  var pi_pageId="planTree2";
    </script>
</body>
</html>
