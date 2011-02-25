package edu.wpi.cs542.mmay.calendar;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
	Key id; //PRIMARY KEY ('id') 'id' varchar(255) NOT NULL
	
	@Persistent
	String something;
	
	
}
