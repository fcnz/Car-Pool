package carPooler.domain;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Traveler {

	// Initiate logger
	private static Logger logger = LoggerFactory.getLogger(Traveler.class);

	// Fields initialized at creation time
	private long _id;
	private String _email;
	private String _username;
	private Gender _gender;
	private GeoPosition _home;

	// Fields set after creation
	private GeoPosition _location;
	private Set<Traveler> _friends;

	public Traveler(long id, String email, String username, Gender gender, GeoPosition home) {
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
}
