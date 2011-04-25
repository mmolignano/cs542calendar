<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="edu.wpi.cs542.mmay.calendar.DatabaseAccess"%>
<%@page import="java.util.Collection"%>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.Calendar"%>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.Event"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="java.lang.Exception" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
	String id = request.getParameter("key");
	//Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
	Key key = KeyFactory.createKey(id.substring(0, id.indexOf(' ')), new Long(id.substring(id.indexOf(' ') + 1)));
	Event ev = DatabaseAccess.getEvent(key);
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	String eventDate = "";
	try {
		eventDate = formatter.format(ev.getStartDate());
	} catch (Exception e) {
		e.getMessage();
	} 
%>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>Your Event</title>
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
    
    
   			<h1>Your Event</h1>
    
   			<table>
				<tr><td><b>Name:</b></td><td><%= ev.getEventName() %></td></tr>
				<tr><td><b>Start Date:</b></td><td><%= eventDate %></td></tr>
				<tr><td><b>Location:</b></td><td><%= ev.getLocation() %></td></tr>
				<tr><td><b>Description:</b></td><td><%= ev.getDescription() %></td></tr>
				<tr><td>
					<form style="display: inline" action="editEvent.jsp" method="post">
						<input type="hidden" name="key" value="<%= ev.getId() %>" />
						<input type="submit" value="Edit or Share" />
					</form>
				</td></tr>
			</table>			
			
			<p><a href="showcalendar">Back</a>   <a href="index.jsp">Home</a></p>
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