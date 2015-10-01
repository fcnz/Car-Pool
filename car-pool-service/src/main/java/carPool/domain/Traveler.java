package carPool.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "Travelers")
public class Traveler {

	// Initiate logger
	private static Logger logger = LoggerFactory.getLogger(Traveler.class);

	// Fields initialized at creation time
	@Id
	@GeneratedValue(generator = "ID_GENERATOR")
	private long _id;

	@Column(name = "email", nullable = false, length = 50)
	private String _email;

	@Column(name = "username", nullable = false, length = 50)
	private String _username;

	@Column(name = "gender", nullable = false, length = 30)
	private Gender _gender;

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "home", nullable = false)
	private GeoPosition _home;

	// Fields set after creation

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "location", nullable = true)
	private GeoPosition _location;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "FRIENDS", joinColumns = @JoinColumn(name = "traveler-id") , inverseJoinColumns = @JoinColumn(name = "friend-id") )
	private Set<Traveler> _friends;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TRAVELER_TRIPS", joinColumns = @JoinColumn(name = "traveler-id") , inverseJoinColumns = @JoinColumn(name = "trip-id") )
	private Set<Trip> _trips;

	public Traveler(long id, String email, String username, Gender gender, GeoPosition home) {
		this._friends = new HashSet<Traveler>();
		this._trips = new HashSet<Trip>();
		this._id = id;
		this._email = email;
		this._username = username;
		this._gender = gender;
		this._home = home;
	}

	/**
	 * Adds the friend to your list so long as they are not already there.
	 * Relies on the equals(Traveler t) method
	 *
	 * @param friend
	 *            - Traveler to add to the friends list.
	 */
	public void addFriend(Traveler friend) {
		if (!_friends.add(friend)) {
			logger.error("Friend, " + friend.getEmail() + " not added to " + this._email);
		}
	}

	public void addTrip(Trip trip) {
		if (!_trips.add(trip)) {
			logger.error("Trip, " + trip.getDateTime() + " not added to " + this._email);
		}
	}

	public long getId() {
		return _id;
	}

	public String getEmail() {
		return _email;
	}

	public String getName() {
		return _username;
	}

	public Gender getGender() {
		return _gender;
	}

	public GeoPosition getHome() {
		return _home;
	}

	public GeoPosition getLocation() {
		return _location;
	}

	public Set<Traveler> getFriends() {
		return Collections.unmodifiableSet(_friends);
	}

	// Setters

	public void setID(long id) {
		this._id = id;
	}

	public void setHome(GeoPosition home) {
		this._home = home;
	}

	public void setUsername(String name) {
		this._username = name;
	}

	public void setLocation(GeoPosition location) {
		this._location = location;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Traveler)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		Traveler other = (Traveler) obj;
		return this._id == other._id;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(_id).toHashCode();
	}

	// Create toString() method
}
