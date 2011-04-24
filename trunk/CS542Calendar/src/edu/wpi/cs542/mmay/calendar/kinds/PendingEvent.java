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
public class PendingEvent {

	@PrimaryKey
	@Persistent
	private String nickname;
	//User account;
	
	@Persistent
	private List<Event> pendingEvents;
	
	public PendingEvent() {
		//this.account = new User("","");
		this.nickname = "";
		this.pendingEvents = new LinkedList<Event>();
	}
	
	public PendingEvent(User account) {
		//this.account = account;
		this.nickname = account.getNickname();
		this.pendingEvents = new LinkedList<Event>();
	}
	
	/*public User getAccount() {
		return this.account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}*/
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	
	public List<Event> getPendingEvents() {
		return pendingEvents;
	}
}
