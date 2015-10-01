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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

@Entity
@Table(name = "DETOURS")
public class Detour {

	private static final Logger logger = Logger.getLogger(Detour.class);

	@Id
	@GeneratedValue(generator = "ID_GENERATOR")
	protected long _id;

	// ID of passenger
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "traveler", updatable = false)
	protected Traveler _traveler;


	protected DateTime _startTime;

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "start", nullable = false)
	protected GeoPosition _start;

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "end", nullable = false)
	protected GeoPosition _end;

	public Detour() {

	}

	public Detour(long id, Traveler traveler, DateTime dateTime, GeoPosition start, GeoPosition end) {
		this._id = id;
		this._traveler = traveler;
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
