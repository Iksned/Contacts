<html>
<head>
<title>Avg number of contacts in groups</title>
</head>
<body bgcolor=white>
<table border="0">
<tr>
<td align=center>
</td>
<td>
<h1> </h1>
</td>
</tr>
</table>
<center>
<h1>
Average number of contacts in groups is
<%@ page import="ConModel.services.Services" %>
<p>(<%=Services.avgContactsInGroups()%>)</p>
</h1>
<form>
  <input type="button" value="Go back!" onclick="history.back()">
  </input>
</form>
</center>
</body>
</html>