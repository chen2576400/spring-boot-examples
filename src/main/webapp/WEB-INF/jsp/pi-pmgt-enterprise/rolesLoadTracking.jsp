﻿ <% 
String path = request.getContextPath(); 
String basePath = request.getScheme() + "://" 
+ request.getServerName() + ":" + request.getServerPort() 
+ path + "/"; 
%> 

<iframe id="windchill_enterprise_roles"  width="100%"  style="background-color: transparent;" marginwidth="0" marginheight="0" frameborder="0"> </iframe>

 <script language="javascript" type="text/javascript">
     document.getElementById("windchill_enterprise_roles").src = "/PlanActivityController/view?file=jsp/pi-pmgt-enterprise/rolesLoadTrackingIframe" ;
 </script>