<html>
<head>
<title>Inactive users</title>
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
<%@ page import="java.util.List,utils.SpringUtils,services.*" %>
<%List<String> usersContacts = ((AnalyticalService)SpringUtils.getBean("analyticalService")).inactiveUsers();%>
<table>
<tableborder="1" width="30%" cellpadding="3">
 <thead>
 <tr>
 <th colspan="2">List of inactive users (contacts < 10)</th>
 </tr>
 </thead>
 <tbody>
<%for (String usersContact : usersContacts) {
%><tr><td><%=usersContact%></td>
 </tr><%
}%>
</tbody>
</table>
</h1>
<form>
  <input type="button" value="Go back!" onclick="history.back()">
  </input>
</form>
</center>

</body>
</html>