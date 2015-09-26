package junitTests;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carPooler.domain.GeoPosition;
import carPooler.domain.Traveler;

public class TestGeoPosition {

	private static Logger logger = LoggerFactory.getLogger(TestGeoPosition.class);

	GeoPosition testGeoPosotion;

	@Before
	public void before() {
		// Create a new GeoPosition for each test
		testGeoPosotion = new GeoPosition(5.55,5.55);
	}

	@Test
	public void testGeoPositionFromString() {
		String asString = testGeoPosotion.toString();
		logger.info(asString);
		assertTrue(asString.equals("(5.55,5.55)"));
		GeoPosition fromString = new GeoPosition(asString);
		assertTrue(testGeoPosotion.equals(fromString));
	}

}
