<%@ page import="com.pisx.tundra.foundation.fc.model.PIObject" %>
<%@ page import="com.pisx.tundra.pmgt.common.util.CommonUtils" %>
<%@ page import="com.pisx.tundra.pmgt.plan.model.PIPlan" %>
<%@ page import="com.pisx.tundra.pmgt.project.PIProjectHelper" %>
<%@ page import="com.pisx.tundra.pmgt.project.model.PIProject" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

    boolean projectIsBaseline = false;
    String oid = (String) request.getParameter("oid");
    PIObject object = CommonUtils.getPIObjectByOid(oid);
    if (object instanceof PIPlan) {
        PIPlan plan = (PIPlan) object;
        PIProject project = plan.getProject();
        projectIsBaseline = PIProjectHelper.service.isBaselineProject(project);
    }
    System.out.println(" planTree2 projectIsBaseline=" + projectIsBaseline);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<body>
<%--<script language="javascript" type="text/javascript">--%>
<%--alert("plantree2222" );--%>

<%--</script>--%>

<iframe id="windchill_gantt"
        width="100%"
        height="100%"
        style="background-color: transparent;" marginwidth="0" marginheight="0"
        frameborder="0" allowfullscreen="true">
    <%--<p>333333333</p>  allowfullscreen="true"--%>
</iframe>
<script language="javascript" type="text/javascript">
    //alert("plantree2");
    if (!window.localStorage) {
        alert('浏览器版本过低，建议升级浏览器！')
    } else {
        var iframeUrl = '', lockedDataIndex = localStorage.getItem("lockedDataIndex"),
            lockedConfig = localStorage.getItem("lockedConfig"), showGantt = sessionStorage.getItem("showGantt");
        console.log(" lockedDataIndex=" + lockedDataIndex);
        console.log(" lockedConfig=" + lockedConfig);
        if (lockedDataIndex && lockedDataIndex != '' && lockedConfig) {
            //todo jsp待改进
            <%--iframeUrl = "<%=basePath%>netmarkets/jsp/pi-pmgt-project/pi-pmgt-column.jsp?projectIsBaseline=<%=projectIsBaseline%>&version=20190114";--%>
            iframeUrl = "/PlanActivityController/view?file=jsp/pi-pmgt-project/pi-pmgt-column&projectIsBaseline=<%=projectIsBaseline%>&version=20190114";
        } else if (showGantt) {
            <%--iframeUrl = "<%=basePath%>netmarkets/jsp/pi-pmgt-project/pi-pmgt-gantt.jsp?projectIsBaseline=<%=projectIsBaseline%>&version=20190114";--%>
            iframeUrl = "/PlanActivityController/view?file=jsp/pi-pmgt-project/pi-pmgt-gantt&projectIsBaseline=<%=projectIsBaseline%>&version=20190114";
        } else {
            <%--iframeUrl = "<%=basePath%>netmarkets/jsp/pi-pmgt-project/pi-pmgt-column.jsp?projectIsBaseline=<%=projectIsBaseline%>&version=20190114";--%>
            iframeUrl = "/PlanActivityController/view?file=jsp/pi-pmgt-project/pi-pmgt-column&projectIsBaseline=<%=projectIsBaseline%>&version=20190114";
        }
        <%--iframeUrl = "/PlanActivityController/view?file=/pi-pmgt-project/pi-pmgt-gantt&projectIsBaseline=<%=projectIsBaseline%>&version=20190114";--%>
        document.getElementById("windchill_gantt").src = iframeUrl;

    }

    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.href.substr(1).match(reg);

        if (r != null) return unescape(r[2]);
        return null;
    }

    function getOid() {
        return GetQueryString('oid').replace('OR:' + PIPlan.class.getName() + ':', '');
    }

</script>


</body>
</html>
