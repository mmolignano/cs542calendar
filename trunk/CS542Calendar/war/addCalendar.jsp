<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
	<head>
		<title>Add New Calendar</title>
	</head>

    <body>

<%
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();
    	if (user != null) {
%>
			<p><%= user.getNickname() %>! (You can
			<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
			<p><a href="index.jsp">Home</a>
			
    		<h1>Add New Calendar</h1>
    
    		<form name="input" action="/addcalendar" method="post">
				<b>Name:</b> <input type="text" name="name" /><br />
				<b>Description:</b> <textarea name="desc" rows="3" cols="60"></textarea><br />
				<input type="submit" value="Submit" />
			</form>
<%
    	} else {
%>
			<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
			to add a new calendar.</p>
<%
		}
%>
  
  	</body>
  	
 </html>