<%@ page import="com.pisx.tundra.pmgt.plan.processors.ExportPlanProcessor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>导出BOM</title>
</head>

<body>
<%
    try {
        out.clear();
        String oid =request.getParameter("oid");
        response.setContentType("application/vnd.ms-project");
        response.setHeader("Content-disposition", "attachment; filename=\"" + "msproject.mpp" + "\";");
        ExportPlanProcessor exportZipUtil = new ExportPlanProcessor();
        exportZipUtil.exportPlan(response.getOutputStream(),oid);
        out.clear();
        out=pageContext.pushBody();
    }
    catch (Throwable t)
    {
        t.printStackTrace();
    }
%>
<%
    out.clear();
    String oid = request.getParameter("oid");
    WTPart part =null;
    ReferenceFactory referencefactory = new ReferenceFactory();
    part = (WTPart)referencefactory.getReference(oid).getObject();
    String number = part.getNumber();
    String filename = "ExportSuperItem_"+number+".xls";

    response.setContentType("application/x-msdownload");
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    OutputStream outputStream = response.getOutputStream();
    HSSFWorkbook wb=ExportSuperItem.exportToExcel(part);
    wb.write(outputStream);
    outputStream.flush();
    out.clear();
    out=pageContext.pushBody();
%>
</body>
</html>

