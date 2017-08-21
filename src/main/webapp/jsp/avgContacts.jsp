<html>
<head>
<title>Avg number of user contacts</title>
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
Average number of user contacts is
<%@ page import="ConModel.services.Services" %>
<p>(<%=Services.avgUserContacts()%>)</p>
</h1>
<form>
  <input type="button" value="Go back!" onclick="history.back()">
  </input>
</form>
</center>
</body>
</html>