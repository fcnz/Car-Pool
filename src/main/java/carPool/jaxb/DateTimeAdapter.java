package carPool.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;

/**
 * JAXB XML adapter to convert between Joda DateTime instances and Strings.
 * DateTime objects are marshalled as Strings, and unmarshalled back into 
 * DateTime instances.
 *
 */
public class DateTimeAdapter extends XmlAdapter<String, DateTime> {

	@Override
	public DateTime unmarshal(String dateTimeAsString) throws Exception {
		if(dateTimeAsString == null) {
			return null;
		}
		
		return new DateTime(dateTimeAsString);
	}

	@Override
	public String marshal(DateTime dateTime) throws Exception {
		if(dateTime == null) {
			return null;
		}
		
		return dateTime.toString();
	}
}