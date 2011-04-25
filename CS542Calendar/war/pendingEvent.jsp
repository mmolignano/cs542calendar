<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="edu.wpi.cs542.mmay.calendar.DatabaseAccess" %>
<%@page import="java.util.Collection" %>
<%@page import="java.util.Set" %>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.Calendar" %>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.Ownership" %>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.PendingCalendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>My Pending Events</title>
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
    
    
   			<h1>My Pending Events</h1>
    		<table>
<%
			Ownership owner = DatabaseAccess.getOwnershipByUser(user);
			Collection<Calendar> myCals = owner.getOwnedCalendars();
			Collection<Event> pendingEvents = owner.getPendingEvents();
			for (Event ev : pendingEvents) {
%>				
				<tr>
					<td><b><%= ev.getName() %></b> : <%= ev.getDescription() %></td> 
					<td>
						<form style="display: inline" action="pendingevent" method="post">
							<input type="hidden" name="evKey" value="<%= ev.getId() %>" />
							<input type="hidden" name="evName" value="<%= ev.getName() %>" />
							<input type="hidden" name="add" value="true" />
							<select name="calKey">
<%
								for (Calendar c : myCals) {
%>
									<option value="<%= c.getId() %>"><%= c.getName() %></option>
<%
								}
%>
							</select>
							<input type="submit" value="Save to this Calendar" />
						</form>
						<form style="display: inline" action="pendingevent" method="post">
							<input type="hidden" name="key" value="<%= ev.getId() %>" />
							<input type="hidden" name="name" value="<%= ev.getName() %>" />
							<input type="hidden" name="add" value="false" />
							<input type="submit" value="Remove Event" />
						</form><br>
					</td>
				</tr>
<%
			}
%>
			</table>
<%
    	} else {
%>
			<p><a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
			to add a new event.</p>
<%
		}
%>
  
  	</body>
  	
 </html>