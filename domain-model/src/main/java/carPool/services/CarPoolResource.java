package carPool.services;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

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

	private Map<Long, Traveler> _carPoolDB;
	private AtomicLong _travelerIDCounter;
	private AtomicLong _tripIDCounter;

	public CarPoolResource() {
		// reloadDatabase();
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

	protected Traveler findTraveler(long id) {
		return _carPoolDB.get(id);
	}

}
