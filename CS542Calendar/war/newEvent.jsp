<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

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
    <h1>Add New Event</h1>
    
    <form name="input" action="addevent" method="post">
		<b>Name:</b> <input type="text" name="name" /><br />
		<b>Start Date:</b> <input type="text" name="startD" id="popupDatepicker"/><br />
		<!--<b>End Date:</b> <input type="text" name="endD" /><br />-->
		<!--<b>Start Time:</b> <input type="text" name="startT" /><br />-->
		<!--<b>End Time:</b> <input type="text" name="endT" /><br />-->
		<b>Location:</b> <input type="text" name="loc" /><br />
		<b>Description:</b> <input type="text" name="desc" /><br />
		
		<input type="submit" value="Submit" />
	</form> 
  
  	</body>
  	
 </html>