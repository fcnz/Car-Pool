package carPooler.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

public class Trip {

	/**
	 * Picking someone up will result in a detour, the passengers trip is put
	 * inside this trip so that the total length can be calculated
	 */
	private Set<Trip> _detours;
	private GeoPosition _start;
	private GeoPosition _end;
	private Traveler _traveler;
	private DateTime _dateTime;

	public Trip() {
		_detours = new HashSet<Trip>();
	}

	public Trip(Traveler traveler, GeoPosition start, GeoPosition end) {
		_detours = new HashSet<Trip>();
		this._traveler = traveler;
		this._start = start;
		this._end = end;
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
	 * Calculates the least distance, beginning at the start, visiting the
	 * pickup locations, the dropoff locations, then the end location
	 *
	 * @return The optimal distance this trip can be given all its detours as an
	 *         int. What the int represents could be time or distance
	 */
	public int tripLength() {
		int length = 0;
		List<GeoPosition> stops = getDirections();
		for (int i = 0; i < stops.size() - 1; i++) {
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
		return stops;
	}

	/**
	 * Make safe
	 *
	 * @return
	 */
	GeoPosition getEnd() {
		return _end;
	}

	/**
	 * Make safe
	 *
	 * @return
	 */
	GeoPosition getStart() {
		return _start;
	}

	public Traveler get_traveler() {
		return _traveler;
	}

	public void set_traveler(Traveler traveler) {
		this._traveler = traveler;
	}

	public void set_start(GeoPosition start) {
		this._start = start;
	}

	public void set_end(GeoPosition end) {
		this._end = end;
	}

	public DateTime getDateTime() {
		return _dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this._dateTime = dateTime;
	}

}
