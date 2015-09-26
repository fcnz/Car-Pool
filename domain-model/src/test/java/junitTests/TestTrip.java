package junitTests;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carPooler.domain.GeoPosition;
import carPooler.domain.Trip;

public class TestTrip {

	private static Logger logger = LoggerFactory.getLogger(TestTrip.class);

	Trip testTrip;
	GeoPosition start;
	GeoPosition end;

	@Before
	public void before() {
		start = new GeoPosition(0.0, 0.0);
		end = new GeoPosition(5.55, 5.55);
		testTrip = new Trip(0, null, null, start, end);
	}

	@Test
	public void testTripLength() {
		// With only two points, trip length should be just the distance between
		// start and end
		logger.debug("Initial length = " + testTrip.tripLength());
		if (start.lengthTo(end) != testTrip.tripLength()) {
			fail("Trip length does not match length between points");
		}
		// Add a detour to the test trip
		GeoPosition stop1 = new GeoPosition(11.1, 11.1);
		GeoPosition stop2 = new GeoPosition(0.0, 0.0);
		testTrip.addDetour(new Trip(0, null, null, stop1, stop2));
		// The new trip should be 5 times the original
		long total = 5 * start.lengthTo(end);
		logger.debug("Total length = " + testTrip.tripLength());
		if (testTrip.tripLength() != total) {
			fail("Trip length should be 5 times original");
		}
	}

}
