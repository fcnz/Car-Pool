package carPooler.domain;

/**
 * Picking someone up will result in a detour, the passengers trip is put inside
 * this trip so that the total length can be calculated. The Traveler of the
 * trip at the top of the hierarchy is considered to be the driver and the
 * Travelers in subsequent levels are considered to be passengers.
 */

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trip {

	private static Logger logger = LoggerFactory.getLogger(Trip.class);

	// Could be a driver or a passenger
	private long _id;
	private Traveler _traveler;
	private DateTime _startTime;
	private GeoPosition _start;
	private GeoPosition _end;
	private List<Trip> _detours;

	public Trip(long id, Traveler traveler, DateTime dateTime, GeoPosition start, GeoPosition end) {
		_detours = new ArrayList<Trip>();
		this._id = id;
		this._traveler = traveler;
		this._startTime = dateTime;
		this._start = start;
		this._end = end;
	}

	/**
	 * Finds the difference adding a new detour would make to the current trip
	 *
	 * @param detour
	 * @return - The difference in length if the new Trip is added
	 */
	public long findExtraLength(Trip detour) {
		// Find the current length
		long currentLength = tripLength();
		// Add the new Trip
		addDetour(detour);
		// Find the new length
		long newLength = tripLength();
		// Remove the Trip that was just added
		_detours.remove(_detours.size());
		// Return the difference
		return newLength - currentLength;
	}

	/**
	 * Any detours within the trip assigned as a detour will be ignored.
	 *
	 * @param detour
	 */
	public void addDetour(Trip detour) {
		_detours.add(detour);
	}

	/**
	 * Calculates the distance, beginning at the start, visiting the pickup
	 * locations, the dropoff locations, then the end location
	 *
	 * @return The optimal distance this trip can be given all its detours as an
	 *         int. What the int represents could be time or distance
	 */
	public long tripLength() {
		long length = 0;
		List<GeoPosition> stops = getDirections();
		logger.debug("0th stop is: " + stops.get(0));
		for (int i = 0; i < stops.size() - 1; i++) {
			logger.debug((i + 1) + "th stop is: " + stops.get(i + 1).toString());
			length += stops.get(i).lengthTo(stops.get(i + 1));
		}
		return length;
	}

	/**
	 * Give the directions for a trip given all it's detours
	 *
	 * @return A list of positions that should be visited in order.
	 */
	public List<GeoPosition> getDirections() {
		List<GeoPosition> stops = new ArrayList<GeoPosition>();
		stops.add(_start);
		for (Trip t : _detours) {
			stops.add(t.getStart());
		}
		for (Trip t : _detours) {
			stops.add(t.getEnd());
		}
		stops.add(_end);
		logger.debug("number of stops = " + stops.size());
		return stops;
	}

	public long getID() {
		return _id;
	}

	public void setID(long _id) {
		this._id = _id;
	}

	// Make getEnd() and getStart() safe
	GeoPosition getEnd() {
		return _end;
	}

	GeoPosition getStart() {
		return _start;
	}

	public Traveler getTraveler() {
		return _traveler;
	}

	public void setTraveler(Traveler traveler) {
		this._traveler = traveler;
	}

	public void setStart(GeoPosition start) {
		this._start = start;
	}

	public void setEnd(GeoPosition end) {
		this._end = end;
	}

	public DateTime getDateTime() {
		return _startTime;
	}

	public void setDateTime(DateTime dateTime) {
		this._startTime = dateTime;
	}

}
