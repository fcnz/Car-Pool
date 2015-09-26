package carPooler.domain;

import java.util.IllegalFormatException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class to represent a geographic location in terms of latitude and longitude.
 * GeoPosition object are immutable.
 *
 */
public class GeoPosition {

	private double _latitude;

	private double _longitude;

	protected GeoPosition() {
	}

	public GeoPosition(double lat, double lng) {
		_latitude = lat;
		_longitude = lng;
	}

	public int lengthTo(GeoPosition otherLocation) {
		// Distance between the two points in cartesian space.
		double distance = Math.sqrt(Math.pow(this._latitude - otherLocation.getLatitude(), 2)
				+ Math.pow(this._longitude - otherLocation.getLongitude(), 2));
		// Convert to meters and drop remainder
		int distanceInKm = (int) (distance * 111000);
		return distanceInKm;
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
		if (geoPositionAsString.indexOf(0) == '(' && geoPositionAsString.indexOf(geoPositionAsString.length()) == ')') {
			// Split the string by ','
			String[] splitString = geoPositionAsString.split(",");
			// If there are now 2 arguments,
			if (splitString.length == 2) {
				this._latitude = Double.parseDouble(splitString[0]);
				this._longitude = Double.parseDouble(splitString[1]);
				return;
			}
		}
		// If you get to this point without returning, the format must have been wrong. Throw an invalid format exception
		throw new IllegalArgumentException("Invalid format of string for GeoPosition: " + geoPositionAsString);
	}
}
