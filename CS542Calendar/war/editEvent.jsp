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
	Key key = KeyFactory.createKey(id.substring(0, id.indexOf('(')), new Long(id.substring(id.indexOf('(') + 1, id.indexOf(')'))));
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
		<title>Edit Event</title>
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
    
    
   			<h1>Edit Event</h1>
    
    		<form style="display: inline" name="input" action="/saveevent" method="post">
    			<input type="hidden" name="key" value="<%= ev.getId() %>" />
    			<table>
					<tr><td><b>Name:</b></td><td> <input type="text" name="name" value="<%= ev.getEventName() %>" /></td></tr>
					<tr><td><b>Start Date:</b></td><td> <input type="text" name="startD" value="<%= eventDate %>" id="popupDatepicker"/></td></tr>
					<tr><td><b>Location:</b></td><td> <input type="text" name="loc" value="<%= ev.getLocation() %>" /></td></tr>
					<tr><td><b>Description:</b></td><td> <input type="text" name="desc" value="<%= ev.getDescription() %>" /></td></tr>
				</table>
				<input type="submit" value="Submit" />
			</form>
			<form style="display: inline" name="cancel" action="showcalendar.jsp" method="get">
				<input type="submit" value="Cancel" />
			</form>
			
			<h1>Share Event</h1>
			
			<p> 
			<form style="display: inline" action="shareevent" method="post" />
				<input type="hidden" name="key" value="<%= ev.getId() %>" />
				<input type="hidden" name="name" value="<%= ev.getEventName() %>" />
				Share with: <input type="text" name="user" />
				<input type="submit" value="Share">
			</form>
			</p>
			
			<h1>Remove Event</h1>
			
			<p> 
			<form style="display: inline" action="removeevent" method="post" />
				<input type="hidden" name="key" value="<%= ev.getId() %>" />
				<input type="submit" value="Remove">
			</form>
			</p>
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