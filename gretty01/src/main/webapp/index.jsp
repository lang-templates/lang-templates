<%@ page contentType="text/html;charset=UTF-8" %>
<%!
public String escape(String s) {
  return org.apache.commons.lang3.StringEscapeUtils.escapeHtml4("<br />\n&amp;").replaceAll("\\n", "<br />");
}
%>
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
<%= escape("<br />\n&amp;").replaceAll("\\n", "<br />") %>
<%= out.getClass().toString() %>

<p><a href="./MyDate">MyDate</a></p>
<p><a href="./Aisatsu">Aisatsu</a></p>

</body>
</html>