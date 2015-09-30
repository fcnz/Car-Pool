package carPool.services;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import carPool.domain.Gender;
import carPool.domain.GeoPosition;
import carPool.domain.Traveler;
import carPool.domain.Trip;

/**
 * Web service resource implementation. An instance of this class will handle
 * all HTTP requests.
 *
 * @author Fraser
 *
 */
@Path("/car-pool")
public class CarPoolResource {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CarPoolResource.class);

	private Map<Long, Traveler> _travelersDBTable;
	private Map<Long, Trip> _tripsDBTable;
	private AtomicLong _travelerIDCounter;
	private AtomicLong _tripIDCounter;

	public CarPoolResource() {
		reloadDatabase();

		initializeSampleData();
	}

	private void initializeSampleData() {
		// ----- Initialize traveler 1
		long id = _travelerIDCounter.incrementAndGet();
		GeoPosition home = new GeoPosition(0, 0);
		Traveler traveler1 = new Traveler(id, "t1@carpool.co.nz", "Traveler One", Gender.MALE, home);

		// ----- Initialize traveler 2
		id = _travelerIDCounter.incrementAndGet();
		home = new GeoPosition(0, 0);
		Traveler traveler2 = new Traveler(id, "t2@carpool.co.nz", "Traveler Two", Gender.FEMALE, home);

		// ----- Initialize a trip for traveler 1
		id = _tripIDCounter.incrementAndGet();
		GeoPosition start = new GeoPosition(0.0, 0.0);
		GeoPosition end = new GeoPosition(0.002, 0.0008);
		DateTime startTime = DateTime.now().plusDays(2);
		Trip trip = new Trip(id, startTime, start, end);
		trip.setTraveler(traveler1.getId());

		// ----- Initialize a trip for traveler 2
		id = _tripIDCounter.incrementAndGet();
		start = new GeoPosition(0.0000001, 0.0000002);
		end = new GeoPosition(0.0020000003, 0.0008001);
		startTime = DateTime.now().plusDays(2);
		trip = new Trip(id, startTime, start, end);
		trip.setTraveler(traveler2.getId());

		// Place initialized objects in the database tables
	}

	private void reloadDatabase() {
		// Initialize pseudo database tables
		_travelersDBTable = new ConcurrentHashMap<Long, Traveler>();
		_tripsDBTable = new ConcurrentHashMap<Long, Trip>();

		// Initialize counters, skip zero
		_travelerIDCounter = new AtomicLong();
		_travelerIDCounter.incrementAndGet();

		_tripIDCounter = new AtomicLong();
		_tripIDCounter.incrementAndGet();

	}

	/**
	 * Adds a new traveler to the system.
	 *
	 * @param dtoTraveler
	 * @return
	 */
	@POST
	@Consumes("application/xml")
	public Response createTraveler(carPool.dto.Traveler dtoTraveler) {
		logger.debug("Read Traveler: " + dtoTraveler);

		// Convert to domain Traveler
		Traveler traveler = CarPoolMapper.toDomainTraveler(dtoTraveler);
		// Set the ID
		traveler.setID(_travelerIDCounter.incrementAndGet());
		// Place it in the database
		_travelersDBTable.put(traveler.getId(), traveler);
		// Return the response
		return Response.created(URI.create("/car-pool/" + traveler.getId())).build();
	}

	@POST
	@Path("{id}/Trips")
	public Response createTrip(@PathParam("id") long travelerID, carPool.dto.Trip dtoTrip) {
		logger.debug("Read Trip: " + dtoTrip);

		// Convert to domain Trip
		Trip trip = CarPoolMapper.toDomainTrip(dtoTrip);
		// Set the ID
		trip.setID(_tripIDCounter.incrementAndGet());
		// Return the response
		return Response.created(URI.create("/Trips/" + trip.getID())).build();
	}

	/**
	 * Records the most recent location for a particular traveler
	 *
	 * @param id
	 * @param location
	 */
	@PUT
	@Path("{id}/locations")
	@Consumes("application/xml")
	public void setTravelerLocation(@PathParam("id") long id, GeoPosition location) {
		// Get the Traveler object from the database
		Traveler traveler = findTraveler(id);
		// Set its location
		traveler.setLocation(location);
	}

	/**
	 * Updates an existing Traveler. Only the parts that can be updated are used
	 * (i.e. cant update email, gender, location)
	 *
	 * @param dtoTraveler
	 */
	@PUT
	@Path("{id}")
	@Consumes
	public void updateTraveler(carPool.dto.Traveler dtoTraveler) {
		// Get the domain model Traveler object from the database
		Traveler traveler = findTraveler(dtoTraveler.getID());
		// Update the Traveler object
		traveler.setUsername(dtoTraveler.getUsername());
		traveler.setHome(dtoTraveler.getHome());
	}

	/**
	 * Gets a specified Traveler object from the database
	 *
	 * @param id
	 * @return
	 */
	protected Traveler findTraveler(long id) {
		return _travelersDBTable.get(id);
	}

	/**
	 * Gets the specified Trip object from the database
	 *
	 * @param id
	 * @return
	 */
	protected Trip findTrip(long id) {
		return _tripsDBTable.get(id);
	}

}
