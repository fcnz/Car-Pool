package carPooler.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to represent a geographic location in terms of latitude and longitude.
 * GeoPosition object are immutable.
 *
 */
@XmlRootElement(name = "GeoPosition")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeoPosition {

	private static Logger logger = LoggerFactory.getLogger(GeoPosition.class);

	@XmlElement(name="latitude")
	private double _latitude;

	@XmlElement(name="longitude")
	private double _longitude;

	protected GeoPosition() {
	}

	public GeoPosition(double lat, double lng) {
		_latitude = lat;
		_longitude = lng;
	}

	/**
	 * Returns an integer representing the length from this position to another.
	 * At the moment, the length is the distance in meters but could be time in
	 * seconds or any other more meaningful measure
	 *
	 * @param otherLocation
	 * @return
	 */
	public long lengthTo(GeoPosition otherLocation) {
		// Distance between the two points in cartesian space.
		double distance = Math.sqrt(Math.pow(this._latitude - otherLocation.getLatitude(), 2)
				+ Math.pow(this._longitude - otherLocation.getLongitude(), 2));
		// Convert to meters and drop remainder
		long distanceInM = (long) (distance * 111000);
		return distanceInM;
	}

	public double getLatitude() {
		return _latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GeoPosition))
			return false;
		if (obj == this)
			return true;

		GeoPosition rhs = (GeoPosition) obj;
		return new EqualsBuilder().append(_latitude, rhs._latitude).append(_longitude, rhs._longitude).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(_latitude).append(_longitude).toHashCode();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("(");
		buffer.append(_latitude);
		buffer.append(",");
		buffer.append(_longitude);
		buffer.append(")");

		return buffer.toString();
	}

	/**
	 * Constructor method that deconstructs a string as long as it is in the
	 * same format as the toString() method
	 */
	public GeoPosition(String geoPositionAsString) {
		// Check if the string starts and ends with a ()
		if (geoPositionAsString.startsWith("(") && geoPositionAsString.endsWith(")")) {
			// Remove the brackets
			geoPositionAsString = geoPositionAsString.substring(1, geoPositionAsString.length() - 1);
			// Split the string by ','
			String[] splitString = geoPositionAsString.split(",");
			logger.debug("lat = " + splitString[0]);
			logger.debug("long = " + splitString[1]);
			// If there are now 2 arguments, assign them to lat and long
			if (splitString.length == 2) {
				this._latitude = Double.parseDouble(splitString[0]);
				this._longitude = Double.parseDouble(splitString[1]);
				return;
			}
		}
		// If you get to this point without returning, the format must have been
		// wrong. Throw an invalid format exception
		throw new IllegalArgumentException("Invalid format of string for GeoPosition: " + geoPositionAsString);
	}
}
