<%@ page pageEncoding="UTF-8" %>
<%
String path = request.getContextPath(); 
String basePath = request.getScheme() + "://" 
+ request.getServerName() + ":" + request.getServerPort() 
+ path + "/"; 
String version="1547691253";
%>
<%@ include file="../../../resources/pi-pmgt-common/globalization.jsp" %>
<!DOCTYPE HTML>
<html manifest="">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <link href="../pi-pmgt-common/js/build/development/PIProjectManager/resources/PIProjectManager-all.css" rel="stylesheet" type="text/css"/>
    <!-- Gantt styles -->
    <link href="../pi-pmgt-common/js/resources/extgantt/css/sch-gantt-all-debug.css" rel="stylesheet" type="text/css" /> 
	<!--Implementation specific styles-->
     <link href="../pi-pmgt-common/js/resources/extgantt/css/gantt-scheduler.css" rel="stylesheet" type="text/css" />
    <link href="../pi-pmgt-common/js/resources/extgantt/css/style.css" rel="stylesheet" type="text/css" />
    <script src="../pi-pmgt-common/js/resources/language/<%=locallang%>.js?<%=version%>"></script> 
    <script src="../pi-pmgt-common/js/resources/js/PmgtGolbal.js?<%=version%>" type="text/javascript"></script>
    <script src="../pi-pmgt-common/js/ext/build/ext-all-rtl-debug.js?<%=version%>"></script>
    <script src="../pi-pmgt-common/js/resources/language/locale-<%=locallang%>.js"></script>
    <!-- Gantt JS files -->
    <script src="../pi-pmgt-common/js/resources/extgantt/js/gantt4.js?<%=version%>" type="text/javascript"></script>
	<script src="../pi-pmgt-common/js/resources/extgantt/js/pi-customized-gantt.js?<%=version%>" type="text/javascript"></script>
    <script src="../pi-pmgt-common/js/resources/extgantt/js/Sch/locale/zh_CN.js"></script>
    <script src="../pi-pmgt-common/js/resources/extgantt/js/Gnt/locale/zh_CN.js"></script>  
    <!-- JQuery JS files -->
    <script src="../pi-pmgt-common/js/resources/jquery.2.1.1.min.js"></script>
    <script src="../pi-pmgt-common/js/resources/echarts.min.js"></script>
    <script src="../pi-pmgt-common/js/app.js"></script> 
	<style type="text/css">  
        .pi-chart-legend{
        list-style:none;
        padding-left:20px;
        position:absolute;
        top:10%;
        }
        .pi-chart-legend-dot{
        width:10px;
        height:10px;
        background-color:red;
        display:inline-block;
        margin:0 0 10px 0;
        }
        .pi-chart-legend-line{
        width:70px;
        height:0;
        background-color:blue;
        display:inline-block;
        border:0;
        border-top:2px solid red;
        margin:0 0 15px 20px;
        }
        .pi-chart-legend-bar{
        display:inline-block;
        width:70px;
        height:20px;
        margin:0 0 5px 20px;
        background-color:blue;
        }
        .pi-chart-legend-text{
        margin:-10px 0 0 28px;
        }
        .pi-tree-node-display{
        display:none;
        }
    </style> 
    
</head>
<body>
<input type="hidden" id="pi_enterprise_roles_basePath" value="<%=basePath%>">
<script language="javascript" type="text/javascript">
      function translateSpecialChar(str) {
    	    // 转义之后的结果
    	    var escapseResult = str;
    	    // javascript正则表达式中的特殊字符
    	    var jsSpecialChars = ["."];
    	    // jquery中的特殊字符,不是正则表达式中的特殊字符
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

	  function initWindow(){
	    //var targetNode=window.parent.document.getElementById("infoPage_myTab_pi-pmgt-enterprise_rolesLoadTracking");
	    var iframe=window.parent.document.getElementById("windchill_enterprise_roles");
	    //var targetParent=targetNode.parentNode;
        //iframe.style.height=targetParent.style.height;
	    //iframe.style.width="435px";
          iframe.style.height="435px";
      }
	  initWindow();
      var pi_project_basePath=$("#pi_enterprise_roles_basePath").val();
	  var pi_pageId="rolesLoadTracking";
    </script>
</body>
</html>












