package carPool.services;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import carPool.domain.Traveler;

/**
 * Web service resource implementation. An instance of this class will handle
 * all HTTP requests.
 *
 * @author Fraser
 *
 */
public class CarPoolerResource {

	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(CarPoolerResource.class);

	private Map<Long, Traveler> _travelerDB;
	private AtomicLong _idCounter;

	public CarPoolerResource() {
		// reloadDatabase();
	}

	public Response createTraveler(carPool.dto.Traveler dtoTraveler) {
		return null;

	}

}
