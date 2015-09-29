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
public class CarPoolerMapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CarPoolerMapper.class);

	/**
	 * Provides a domain model traveler given a dto one
	 *
	 * @param dtoTraveler
	 * @return
	 */
	static Traveler toDomainTraveler(carPool.dto.Traveler dtoTraveler) {
		Traveler domainTraveler = new Traveler(dtoTraveler.getID(), dtoTraveler.getEmail(), dtoTraveler.getUsername(),
				dtoTraveler.getGender(), dtoTraveler.getHome());
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
		return dtoTraveler;
	}

	static Trip toDomainTrip(carPool.dto.Trip dtoTrip) {
		Trip domainTrip = new Trip(dtoTrip.getID(), dtoTrip.getTravelerID(), dtoTrip.getStartTime(), dtoTrip.getStart(),
				dtoTrip.getEnd());
		return domainTrip;
	}

	static carPool.dto.Trip toDTOTrip(Trip domainTrip) {
		carPool.dto.Trip dtoTrip = new carPool.dto.Trip(domainTrip.getID(), domainTrip.getTravelerID(), domainTrip.getDateTime(), domainTrip.getStart(),
				domainTrip.getEnd());
		return dtoTrip;
	}

}
