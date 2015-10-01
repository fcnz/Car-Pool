package carPool.domain;

/**
 * Picking someone up will result in a detour, the passengers trip is put inside
 * this trip so that the total length can be calculated. The Traveler of the
 * trip at the top of the hierarchy is considered to be the driver and the
 * Travelers in subsequent levels are considered to be passengers.
 */

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "Trip")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "TRIPS")
public class Trip extends Detour {

	private static Logger logger = LoggerFactory.getLogger(Trip.class);

	@OneToMany
	@JoinTable(name = "TRIP_DETOURS", joinColumns = @JoinColumn(name = "trip-id") , inverseJoinColumns = @JoinColumn(name = "detour-id") )
	@XmlElement(name = "detours")
	private List<Detour> _detours;

	public Trip() {
		_detours = new ArrayList<Detour>();
	}

	/**
	 * Constructor to use, omits the traveler ID field, should only added when
	 * it is added to the database
	 *
	 * @param id
	 * @param dateTime
	 * @param start
	 * @param end
	 */
	public Trip(long id, DateTime dateTime, GeoPosition start, GeoPosition end) {
		super(id, dateTime, start, end);
		_detours = new ArrayList<Detour>();
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
	public void addDetour(Detour detour) {
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
		stops.add(this.getStart());
		for (Detour d : _detours) {
			stops.add(d.getStart());
		}
		for (Detour d : _detours) {
			stops.add(d.getEnd());
		}
		stops.add(this.getEnd());
		logger.debug("number of stops = " + stops.size());
		return stops;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		try {
			Trip t = (Trip) obj;
			return new EqualsBuilder().append(_id, t._id).append(_startTime, t._startTime).append(_start, _start)
					.append(_end, t._end).isEquals();
		} catch (Exception e) {
			return false;
		}
	}

}
