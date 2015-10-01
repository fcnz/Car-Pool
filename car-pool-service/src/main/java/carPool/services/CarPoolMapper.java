package carPool.services;

import org.apache.log4j.Logger;

import carPool.domain.Traveler;
import carPool.domain.Trip;;

/**
 * Helper class for converting between DTO and domain model objects
 *
 * @author Fraser
 *
 */
public class CarPoolMapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CarPoolMapper.class);

	/**
	 * Provides a domain model traveler given a dto one
	 *
	 * @param dtoTraveler
	 * @return
	 */
	static Traveler toDomainTraveler(carPool.dto.Traveler dtoTraveler) {
		Traveler domainTraveler = new Traveler(dtoTraveler.getID(), dtoTraveler.getEmail(), dtoTraveler.getUsername(),
				dtoTraveler.getGender(), dtoTraveler.getHome());
		domainTraveler.setTrips(dtoTraveler.getTrips());
		try {
			domainTraveler.setLocation(dtoTraveler.getLocation());
			logger.debug(
					"Location for Traveler: " + dtoTraveler.getID() + " was: " + dtoTraveler.getLocation().toString());
		} catch (NullPointerException e) {
			logger.debug("Location was null for Traveler: " + dtoTraveler.getID());
		}
		return domainTraveler;
	}

	static carPool.dto.Traveler toDTOTraveler(Traveler domainTraveler) {
		carPool.dto.Traveler dtoTraveler = new carPool.dto.Traveler(domainTraveler.getId(),
				domainTraveler.getEmail(), domainTraveler.getName(), domainTraveler.getGender(),
				domainTraveler.getHome(), domainTraveler.getLocation());
		dtoTraveler.setTrips(domainTraveler.getTrips());
		return dtoTraveler;
	}

}
