<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
	<head>
		<title>Hello App Engine</title>
	</head>
	<body>

<%
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();
    	if (user != null) {
%>
		<p><%= user.getNickname() %> (You can
		<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
		
		<h1>CS542Calendar!</h1>
	
    	<table>
      		<tr>
        		<td colspan="2" style="font-weight:bold;">Available Servlets:</td>        
      		</tr>
      		<tr>
        		<td><a href="showcalendar">Show My Calendar</a></td>
      		</tr>
      		<tr>
        		<td><a href="newEvent.jsp">Add Event</a></td>
      		</tr>
      		<!--<tr>
        		<td><a href="listEvent">List Events</a></td>
      		</tr>-->
      		<tr>
        		<td><a href="addCalendar.jsp">Add Calendar</a></td>
      		</tr>
      		<tr>
        		<td><a href="listCalendar">List Calendars</a></td>
      		</tr>
      		<tr>
      			<td><a href="pendingCalendar.jsp">Check Pending Calendars</a></td>
      		</tr>
      		<tr>
      			<td><a href="pendingEvent.jsp">Check Pending Events</a></td>
      		</tr>
    	</table>
<%
    	} else {
%>
		<p>Hello!
		<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
		to include your name with greetings you post.</p>
<%
    	}
%>

	</body>
</html>