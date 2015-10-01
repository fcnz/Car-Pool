package persistenceTests;

import org.apache.log4j.Logger;
import org.junit.Test;

import carPool.domain.Gender;
import carPool.domain.GeoPosition;
import carPool.domain.Traveler;
import carPool.domain.Trip;

public class TestPersistence extends JpaTest {

	private static final Logger logger = Logger.getLogger(TestPersistence.class);

	@Test
	public void persistThings() {
		_entityManager.getTransaction().begin();

		// Instantiate domain objects
		GeoPosition home = new GeoPosition(0.00005, 0.00003);
		Traveler fraser = new Traveler("fraser@croad.co.nz", "croadie", Gender.MALE, home);
		GeoPosition location = new GeoPosition(0.000051, 0.0000305);
		fraser.setLocation(location);

		_entityManager.persist(fraser);

		_entityManager.getTransaction().commit();
	}

}
