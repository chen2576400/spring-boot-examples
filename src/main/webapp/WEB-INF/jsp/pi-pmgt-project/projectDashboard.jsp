<%@ page pageEncoding="UTF-8" %>
<%
    String version="20191208";
%>
<%@ include file="../../../resources/pi-pmgt-common/globalization.jsp" %>
<!DOCTYPE HTML>
<html manifest="">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <script language="javascript" type="text/javascript">
        if(!window.localStorage){
            alert('浏览器版本过低，建议升级浏览器！')
        }else{
            lockedDataIndex=localStorage.getItem("lockedDataIndex");
        }
    </script>
    <%--<link href="../pi-pmgt-common/js/build/development/PIProjectManager/resources/PIProjectManager-all.css?version=<%=version%>" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="../pi-pmgt-common/js/resources/extgantt/css/sch-gantt-all-debug.css?version=<%=version%>" rel="stylesheet" type="text/css" />--%>
    <%--<link href="../pi-pmgt-common/js/resources/extgantt/css/gantt-scheduler.css?version=<%=version%>" rel="stylesheet" type="text/css" />--%>
    <%--<link href="../pi-pmgt-common/js/resources/extgantt/css/style.css?version=<%=version%>" rel="stylesheet" type="text/css" />--%>
    <script src="../pi-pmgt-common/js/resources/language/<%=locallang%>.js?version=<%=version%>"></script>
    <%--<script src="../pi-pmgt-common/js/resources/js/PmgtGolbal.js?version=<%=version%>" type="text/javascript"></script>--%>
    <script src="../pi-pmgt-common/js/ext/build/ext-all-rtl-debug-column.js?version=<%=version%>"></script>
    <script src="../pi-pmgt-common/js/resources/language/locale-<%=locallang%>.js?version=<%=version%>"></script>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/ResourceGrid.js?version=<%=version%>"></script>--%>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/PmgtMultiSelectorSearch.js?version=<%=version%>"></script>--%>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/PmgtTaskEditor.js?version=<%=version%>"></script>--%>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/CellDragDrop.js?version=<%=version%>"></script>--%>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/gantt4-column.js?version=<%=version%>" type="text/javascript"></script>--%>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/pi-customized-gantt.js?version=<%=version%>" type="text/javascript"></script>--%>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/Sch/locale/zh_CN.js?version=<%=version%>"></script>--%>
    <%--<script src="../pi-pmgt-common/js/resources/extgantt/js/Gnt/locale/zh_CN.js?version=<%=version%>"></script>--%>
    <script src="../pi-pmgt-common/js/resources/js/jquery.2.1.1.min.js?version=<%=version%>"></script>
    <%--<script src="../pi-pmgt-common/js/app.js?version=<%=version%>"></script>--%>

    <script src="/pi-pmgt-common/js/resources/js/highcharts-debug.js?t="+nowTime></script>
    <script src="/pi-pmgt-common/js/resources/js/taskStatusReport.js?t="+nowTime></script>
    <script src="/pi-pmgt-common/js/resources/js/workloadReport.js?t="+nowTime></script>
    <script src="/pi-pmgt-common/js/resources/js/projectIssues.js?t="+nowTime></script>
    <script src="/pi-pmgt-common/js/resources/js/projectChanges.js?t="+nowTime></script>
    <script src="/pi-pmgt-common/js/resources/js/projectRisks.js?t="+nowTime></script>
    <script src="/pi-pmgt-common/js/resources/js/projectDashboard.js?t="+nowTime></script>
</head>
<body>
<script language="javascript" type="text/javascript">
    // var targetNode=window.parent.document.getElementById("infoPage_myTab_pi-pmgt-project_planTree2");
    // var iframe=window.parent.document.getElementById("windchill_gantt");

    // iframe.style.height="800px";

</script>
</body>
</html>
