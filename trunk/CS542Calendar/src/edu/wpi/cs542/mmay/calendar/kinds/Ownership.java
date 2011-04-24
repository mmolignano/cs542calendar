package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable
public class Ownership {

	@PrimaryKey
	@Persistent
	private String nickname;
	
	@Persistent
	private User account;
	
	@Persistent
	private List<Calendar> ownedCalendars;
	
	@Persistent
	private PendingCalendar pendingCalendars;
	//private List<Calendar> pendingCalendars;
		
	@Persistent
	private PendingEvent pendingEvents;
	//private List<Event> pendingEvents;
	
	public Ownership() {
		this.nickname = "";
		this.account = new User("","");
		this.ownedCalendars = new LinkedList<Calendar>();
		//this.pendingCalendars = new LinkedList<Calendar>();
		this.pendingCalendars = new PendingCalendar();
		//this.pendingEvents = new LinkedList<Event>();
		this.pendingEvents = new PendingEvent();
	}
	
	public Ownership(User account) {
		this.nickname = account.getNickname();
		this.account = account;
		this.ownedCalendars = new LinkedList<Calendar>();
		//this.pendingCalendars = new LinkedList<Calendar>();
		this.pendingCalendars = new PendingCalendar(account);
		//this.pendingEvents = new LinkedList<Event>();
		this.pendingEvents = new PendingEvent(account);
	}
	
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public User getAccount() {
		return account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}
	
	public void addOwnedCalendar(Calendar calendar) {
		ownedCalendars.add(calendar);
	}
	
	public void removeOwnedCalendar(Key calendarKey) {
		for (Calendar calendar : ownedCalendars) {
			if (calendar.getId().equals(calendarKey)) {
				ownedCalendars.remove(calendar);
				break;
			}
		}
	}
	
	public List<Calendar> getOwnedCalendars() {
		return ownedCalendars;
	}
	
	public void addPendingCalendar(Calendar calendar) {
		pendingCalendars.addPendingCalendar(calendar);
	}
	
	public void removePendingCalendar(Key calendarKey) {
		/*for (Calendar calendar : pendingCalendars) {
			if (calendar.getId().equals(calendarKey)) {
				pendingCalendars.remove(calendar);
				break;
			}
		}*/
		pendingCalendars.removePendingCalendar(calendarKey);
	}
	
	/*public List<Calendar> getPendingCalendars() {
		return pendingCalendars;
	}*/
	public PendingCalendar getPendingCalendarKind() {
		return pendingCalendars;
	}
	
	public List<Calendar> getPendingCalendarList() {
		return pendingCalendars.getPendingCalendars();
	}
	
	public void addPendingEvent(Event event) {
		pendingEvents.addPendingEvent(event);
	}
	
	public void removePendingEvent(Key eventKey) {
		/*for (Event event : pendingEvents) {
			if (event.getId().equals(eventKey)) {
				pendingEvents.remove(event);
				break;
			}
		}*/
		pendingEvents.removePendingEvent(eventKey);
	}
	
	/*public List<Event> getPendingEvents() {
		return pendingEvents;
	}*/
	
	public PendingEvent getPendingEventsKind() {
		return pendingEvents;
	}
	
	public List<Event> getPendingEventsList() {
		return pendingEvents.getPendingEvents();
	}
}
