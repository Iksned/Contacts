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

<%@ page import="java.util.List,utils.SpringUtils,services.*" %>
<%List<ResultTable> usersContacts = ((AnalyticalService)SpringUtils.getBean("analyticalService")).countUserGroups();%>
<table>
<tableborder="1" width="30%" cellpadding="3">
 <thead>
 <tr>
 <th colspan="2">Number of groups</th>
 </tr>
 </thead>
 <tbody>
<%for (ResultTable usersContact : usersContacts) {
%><tr><td><%=usersContact.getUsername()%></td>
      <td><%=usersContact.getCount()%></td></tr><%
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