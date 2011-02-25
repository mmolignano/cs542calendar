package edu.wpi.cs542.mmay.calendar;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

/**
 * Entity representing a calendar event
 * 
 * @author Andrew Yee
 * @author Mike Molignano
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Event {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key id;
	
	@Persistent
	String eventName;	
	
	@Persistent
	Date startDate;
	
	/*@Persistent
	Date endDate;
	
	@Persistent
	int duration; // in minutes*/
	
	@Persistent
	String location;
	
	@Persistent
	String description;
	

	public Event() {
		eventName="Default Event";
		startDate = java.util.Calendar.getInstance().getTime();
		//endDate = java.util.Calendar.getInstance().getTime();
		location="Default Location";
		description="Default Description";
	}
	
	//public Event(String eventName, Date startDate, Date endDate, String location, String description) {
	public Event(String eventName, Date startDate, String location, String description) {
		this.eventName = eventName;
		this.startDate = startDate;
		//this.endDate = endDate;
		this.location = location;
		this.description = description;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean equals(Object o) {
		if(this.getClass().equals(o.getClass())) {
			if (this.id.equals(((Event)o).getId())) {
				return true;
			}
		}
		return false;
	}
}