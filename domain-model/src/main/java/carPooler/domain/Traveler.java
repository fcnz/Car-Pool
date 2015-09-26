package carPooler.domain;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Traveler {

	private static Logger logger = LoggerFactory.getLogger( Traveler.class );

	// Fields initialized at creation time
	private long _id;
	private String _email;
	private String _username;
	private Gender _gender;
	private GeoPosition _home;
	// Fields set after creation
	private GeoPosition _location;
	private Set<Traveler> _friends;

	public Traveler(long id,
			String email,
			String username,
			Gender gender,
			GeoPosition home) {
		this._id = id;
		this._email = email;
		this._username = username;
		this._gender = gender;
		this._home = home;
	}

	/**
	 * Adds the friend to your list so long as they are not already there.
	 * @param friend - User friend to add to the list.
	 */
	public void addFriend(Traveler friend) {
			if (!_friends.add(friend)) {
				logger.error("Friend, " + friend.getEmail() + " not added to " + this._email);
			}


	}

	public GeoPosition getHome() {
		return _home;
	}

	public void setUsername(String name) {
		this._username = name;
	}

	public String getName() {
		return _username;
	}

	public String getEmail() {
		return _email;
	}

	public void setHome(GeoPosition home) {
		this._home = home;
	}

	public GeoPosition getLocation() {
		return _location;
	}

	public void setLocation(GeoPosition location) {
		this._location = location;
	}

	@Override
	public boolean equals(Object o) {
		if (((Traveler) o).getEmail().equals(this._email)) {
			return true;
		} else {
			return false;
		}
	}

	public Gender getGender() {
		return _gender;
	}

	public long getId() {
		return _id;
	}

}
