package edu.wpi.cs542.mmay.calendar;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

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

}
