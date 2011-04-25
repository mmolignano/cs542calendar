package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class RecurringEvent extends Event {

	//@Persistent
	//String type; - Enum?  Weekly/Monthly/etc
	
	//@Persistent
	//Days - how to do this?
	
	@Persistent
	Date endDate;
	
	//@Persistent
	//End time?
	
	public RecurringEvent() {
		super();
		//type = "Default Type";
		endDate = java.util.Calendar.getInstance().getTime();
		
	}
	
	public RecurringEvent(String eventName, Date startDate, String location, String description, Date endDate) {
		super(eventName, startDate, location, description);
		this.endDate = endDate;
		
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
