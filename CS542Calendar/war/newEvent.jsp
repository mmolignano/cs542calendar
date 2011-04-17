<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="edu.wpi.cs542.mmay.calendar.DatabaseAccess"%>
<%@page import="java.util.Collection"%>
<%@page import="edu.wpi.cs542.mmay.calendar.kinds.Calendar"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>Add New Calendar Event</title>
	</head>
	<style type="text/css">
		@import "datepicker/jquery.datepick.css";
	</style>
	<script type="text/javascript" src="jquery/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="datepicker/jquery.datepick.js"></script>
	<script type="text/javascript">
	$(function() {
		$('#popupDatepicker').datepick();
	});

	function showDate(date) {
		alert('The date chosen is ' + date);
	}
	</script>
    <body>
    
<%
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();
    	if (user != null) {
%>
			<p><%= user.getNickname() %> (You can
			<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
			<p><a href="index.jsp">Home</a>
    
    
   			<h1>Add New Event</h1>
    
    		<form style="display: inline" name="input" action="/addevent" method="post">
    			<table>
					<tr><td><b>Name:</b></td><td> <input type="text" name="name" /></td></tr>
					<tr><td><b>Start Date:</b></td><td> <input type="text" name="startD" id="popupDatepicker"/></td></tr>
					<!--<b><tr><td>End Date:</b></td><td> <input type="text" name="endD" /></td></tr>-->
					<!--<b><tr><td>Start Time:</b></td><td> <input type="text" name="startT" /></td></tr>-->
					<!--<b><tr><td>End Time:</b></td><td> <input type="text" name="endT" /></td></tr>-->
					<tr><td><b>Location:</b></td><td> <input type="text" name="loc" /></td></tr>
					<tr><td><b>Description:</b></td><td> <input type="text" name="desc" /></td></tr>
					<tr><td><b>Calendar:</b></td><td> <select name="calendar">
<%
					Collection<Calendar> myCals = DatabaseAccess.getCalendarsByUser(user);
					for (Calendar c : myCals) {
%>
						<option value="<%= c.getId() %>"><%= c.getName() %></option>
<%
					}
%>
					</select></td></tr>
				</table>
				<input type="submit" value="Submit" />
			</form>
			<form style="display: inline" name="cancel" action="index.jsp" method="get">
				<input type="submit" value="Cancel" />
			</form>
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