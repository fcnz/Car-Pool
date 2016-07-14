package carPool.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.joda.time.DateTime;

import carPool.jaxb.DateTimeAdapter;

@XmlRootElement(name = "Detour")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "DETOURS")
public class Detour {

	@Id
	@GeneratedValue(generator = "ID_GENERATOR")
	@XmlAttribute(name = "id")
	protected long _id;

	// ID of passenger
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "passenger", updatable = false)
	@XmlAttribute(name = "passenger-id")
	private long _passengerID;

	@XmlElement(name = "start-time")
	@XmlJavaTypeAdapter(value=DateTimeAdapter.class)
	protected DateTime _startTime;

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "start", nullable = false)
	@XmlElement(name = "start")
	protected GeoPosition _start;

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "end", nullable = false)
	@XmlElement(name = "end")
	protected GeoPosition _end;

	public Detour() {

	}

	public Detour(long id, long passenger, DateTime dateTime, GeoPosition start, GeoPosition end) {
		this._id = id;
		this._passengerID = passenger;
		this._startTime = dateTime;
		this._start = start;
		this._end = end;
	}

	public Detour(long id, DateTime dateTime, GeoPosition start, GeoPosition end) {
		this._id = id;
		this._startTime = dateTime;
		this._start = start;
		this._end = end;
	}

	public long getID() {
		return _id;
	}

	public void setID(long _id) {
		this._id = _id;
	}

	// Make getEnd() and getStart() safe
	public GeoPosition getEnd() {
		return _end;
	}

	public GeoPosition getStart() {
		return _start;
	}

	public long getPassengerID() {
		return _passengerID;
	}

	public void setPassengerID(long passenger) {
		this._passengerID = passenger;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		try {
			Detour d = (Detour) obj;
			return new EqualsBuilder().append(_id, d._id).append(_startTime, d._startTime).append(_start, _start)
					.append(_end, d._end).append(_passengerID, d._passengerID).isEquals();
		} catch (Exception e) {
			return false;
		}

	}

}
