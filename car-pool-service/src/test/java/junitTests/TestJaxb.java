package junitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import carPool.domain.Gender;
import carPool.domain.GeoPosition;
import carPool.domain.Trip;
import carPool.dto.Traveler;

public class TestJaxb {

	private static JAXBContext _jaxbCxt = null;
	private static Marshaller _marshaller = null;
	private static Unmarshaller _unmarshaller = null;

	private Traveler _traveler;
	private Trip _trip;
	private GeoPosition _geoPosition;

	/**
	 * One time set up method. Instantiates JAXB classes for marshalling and
	 * unmarshaling.
	 *
	 * @throws IOException
	 * @throws JAXBException
	 * @throws SAXException
	 */
	@BeforeClass
	public static void setUpJAXB() throws IOException, JAXBException, SAXException {
		_jaxbCxt = JAXBContext.newInstance(Traveler.class, Trip.class, GeoPosition.class);
		_marshaller = _jaxbCxt.createMarshaller();
		_unmarshaller = _jaxbCxt.createUnmarshaller();

		// Set JAXB to create human readable xml
		_marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// // Make output directory if necessary
		// new File(OUTPUT_PATH).mkdirs();
		//
		// // Generate xml schema, store the .xsd file in the output dir
		// SchemaOutputResolver sor = new
		// DefaultSchemaOutputResolver(OUTPUT_PATH, XML_FILE_SCHEMA);
		// _jaxbCxt.generateSchema(sor);
		//
		// // Set the schema for the unmarshaler
		// SchemaFactory factory =
		// SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// Schema schema = factory.newSchema(new File(OUTPUT_PATH +
		// XML_FILE_SCHEMA));
		// _unmarshaller.setSchema(schema);
		//
		// // Set an error handler on the unmarshaller
		// _unmarshaller.setEventHandler(new DefaultValidationEventHandler());
	}

	/**
	 * Reset values for objects before each test
	 */
	@Before
	public void initializeFixtureObjects() {
		// ----- Initialize traveler 1
		GeoPosition home = new GeoPosition(0, 0);
		_traveler = new Traveler(1, "t1@carpool.co.nz", "Traveler One", Gender.MALE, home, home);

		// ----- Initialize a trip for traveler 1
		GeoPosition start = new GeoPosition(0.0, 0.0);
		GeoPosition end = new GeoPosition(0.002, 0.0008);
		DateTime startTime = DateTime.now().plusDays(2);
		_trip = new Trip(1, startTime, start, end);
		_traveler.addTrip(_trip);

		// ----- Initialize a samplle GeoPosition
		_geoPosition = new GeoPosition(0, 0);
	}

	/**
	 * Marshals a Traveler instance, storing the generated XML in a ByteArray in
	 * memory. The XML representation is then unmarshalled to produce a copy of
	 * the original Traveler instance.
	 *
	 */
	@Test
	public void marshalAndUnmarshalTraveler() throws JAXBException {
		_marshaller.marshal(_traveler, System.out);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		_marshaller.marshal(_traveler, baos);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Traveler copy = (Traveler) _unmarshaller.unmarshal(bais);

		// The two Traveler instances should have the same value (i.e. state).
		assertEquals(_traveler, copy);

		// The two Traveler instance are separate objects.
		assertNotSame(_traveler, copy);
	}

	/**
	 * Marshals a Trip instance, storing the generated XML in a ByteArray in
	 * memory. The XML representation is then unmarshalled to produce a copy of
	 * the original Trip instance.
	 *
	 */
	@Test
	public void marshalAndUnmarshalTrip() throws JAXBException {

		_marshaller.marshal(_trip, System.out);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		_marshaller.marshal(_trip, baos);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Trip copy = (Trip) _unmarshaller.unmarshal(bais);

		// The two Trip instances should have the same value (i.e. state).
		assertEquals(_trip, copy);

		// The two Trip instance are separate objects.
		assertNotSame(_trip, copy);
	}

	/**
	 * Marshals a GeoPosition instance, storing the generated XML in a ByteArray
	 * in memory. The XML representation is then unmarshalled to produce a copy
	 * of the original GeoPosition instance.
	 *
	 */
	@Test
	public void marshalAndUnmarshalGeoPosition() throws JAXBException {

		_marshaller.marshal(_geoPosition, System.out);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		_marshaller.marshal(_geoPosition, baos);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		GeoPosition copy = (GeoPosition) _unmarshaller.unmarshal(bais);

		// The two GeoPosition instances should have the same value (i.e.
		// state).
		assertEquals(_geoPosition, copy);

		// The two GeoPosition instance are separate objects.
		assertNotSame(_geoPosition, copy);
	}

}
