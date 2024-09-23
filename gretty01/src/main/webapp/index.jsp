<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>Hello World!</title>
</head>
<body>
<h1>Hello World!</h1>

<%
out.println(new java.util.Date());
out.println("<br />");
out.println(out.getClass().toString());
%>
<br />
<%= web.JspTools.escapeHtml("<br />\r\n&amp;\n").replaceAll("\\n", "<br />") %>
<%= out.getClass().toString() %>

<p><a href="./MyDate">MyDate</a></p>
<p><a href="./Aisatsu">Aisatsu</a></p>

</body>
</html>