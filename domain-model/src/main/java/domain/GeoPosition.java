package domain;

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
}
