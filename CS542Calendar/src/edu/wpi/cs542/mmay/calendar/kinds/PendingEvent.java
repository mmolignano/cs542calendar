package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

public class PendingEvent {

	@PrimaryKey
	@Persistent
	User account;
	
	@Persistent
	List<Event> pendingEvents;
	
	public PendingEvent() {
		this.account = new User("","");
		this.pendingEvents = new LinkedList<Event>();
	}
	
	public PendingEvent(User account) {
		this.account = account;
		this.pendingEvents = new LinkedList<Event>();
	}
	
	public User getAccount() {
		return this.account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}
	
	public void addPendingEvent(Event event) {
		this.pendingEvents.add(event);
	}
	
	public void removePendingEvent(Key eventKey) {
		for (Event calendar : pendingEvents) {
			if (calendar.getId().equals(eventKey)) {
				pendingEvents.remove(calendar);
				break;
			}
		}
	}
}
