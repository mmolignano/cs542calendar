<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="edu.wpi.cs542.mmay.calendar.DatabaseAccess"%>
<%@page import="java.util.List"%>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.Calendar"%>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.Ownership"%>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.PendingCalendar"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>My Pending Calendars</title>
	</head>
    <body>
    
<%
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();
    	if (user != null) {
%>
			<p><%= user.getNickname() %> (You can
			<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
			<p><a href="index.jsp">Home</a>
    
    
   			<h1>My Pending Calendars</h1>
    
<%
			PendingCalendar pending = DatabaseAccess.getPendingCalendarsForUser(user);
			List<Calendar> pendingCals = pending.getPendingCalendars();
			for (Calendar c : pendingCals) {
%>				
				<b><%= c.getName() %></b> : <%= c.getDescription() %> 
				<form style="display: inline" action="pendingcalendar" method="post">
					<input type="hidden" name="key" value="<%= c.getId() %>" />
					<input type="hidden" name="name" value="<%= c.getName() %>" />
					<input type="hidden" name="add" value="true" />
					<input type="submit" value="Save to My Calendars" />
				</form>
				<form style="display: inline" action="pendingcalendar" method="post">
					<input type="hidden" name="key" value="<%= c.getId() %>" />
					<input type="hidden" name="name" value="<%= c.getName() %>" />
					<input type="hidden" name="add" value="false" />
					<input type="submit" value="Remove Calendar" />
				</form>
<%
			}
    	} else {
%>
			<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
			to add a new event.</p>
<%
		}
%>
  
  	</body>
  	
 </html>