package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class PendingEvent {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	//private String nickname;
	//User account;
	
	@Persistent
	//private List<Event> pendingEvents;
	private Set<Key> pendingEvents;
	
	public PendingEvent() {
		//this.account = new User("","");
		//this.nickname = "";
		//this.pendingEvents = new LinkedList<Event>();
		this.pendingEvents = new HashSet<Key>();
	}
	
	public PendingEvent(User account) {
		//this.account = account;
		//this.nickname = account.getNickname();
		//this.pendingEvents = new LinkedList<Event>();
		this();
	}
	
	/*public User getAccount() {
		return this.account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}*/
	
	/*public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}*/
	
	public Key getId() {
		return id;
	}
	
	public void setId(Key key) {
		this.id = key;
	}
	
	/*public void addPendingEvent(Event event) {
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
	}*/
	
	public void addPendingEvent(Key eventKey) {
		this.pendingEvents.add(eventKey);
	}
	
	public void removePendingEvent(Key eventKey) {
		this.pendingEvents.remove(eventKey);
	}
	
	public Set<Key> getPendingEvents() {
		return this.pendingEvents;
	}
}
