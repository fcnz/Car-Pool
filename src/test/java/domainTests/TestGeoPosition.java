package domainTests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carPool.domain.GeoPosition;

public class TestGeoPosition {

	private static Logger logger = LoggerFactory.getLogger(TestGeoPosition.class);

	GeoPosition testGeoPosotion;

	@Before
	public void before() {
		// Create a new GeoPosition for each test
		testGeoPosotion = new GeoPosition(5.55, 5.55);
	}

	@Test
	public void testGeoPositionFromString() {
		String asString = testGeoPosotion.toString();
		logger.debug(asString);
		assertTrue(asString.equals("(5.55,5.55)"));
		GeoPosition fromString = new GeoPosition(asString);
		assertTrue(testGeoPosotion.equals(fromString));
	}

	@Test
	public void testLengthTo() {
		// Make another GeoPosition
		GeoPosition position2 = new GeoPosition(0.0, 0.0);
		// Check that the length is the same each way
		assertTrue(position2.lengthTo(testGeoPosotion) == testGeoPosotion.lengthTo(position2));
		logger.debug("length is: " + position2.lengthTo(testGeoPosotion));
		assertTrue(position2.lengthTo(testGeoPosotion) == 871226);
	}

}
