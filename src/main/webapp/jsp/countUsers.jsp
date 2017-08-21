<html>
<head>
<title>Number of all Users</title>
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
There is
<%@ page import="ConModel.services.Services" %>
<p>(<%=Services.countUsers()%>)</p>
  Users
</h1>
<form>
  <input type="button" value="Go back!" onclick="history.back()">
  </input>
</form>
</center>

</body>
</html>