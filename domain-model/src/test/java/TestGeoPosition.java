import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import carPooler.domain.GeoPosition;

public class TestGeoPosition {

	GeoPosition testGeoPosotion;

	@Before
	public void before() {
		// Create a GeoPosition at position (5,5)
		testGeoPosotion = new GeoPosition(5,5);
	}

	@Test
	public void testGeoPositionFromString() {
		String asString = testGeoPosotion.toString();
		assertTrue(asString.equals("(5,5)"));
		GeoPosition fromString = new GeoPosition(asString);
		assertTrue(testGeoPosotion.equals(fromString));
	}

}
