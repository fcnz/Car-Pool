package carPool.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;

/** 
 * JAXB XML adapter to convert between Joda LocalDate instances and Strings.
 * LocalDate objects are marshalled as Strings, and unmarshalled back into 
 * LocalDate instances.
 * 
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	@Override
	public LocalDate unmarshal(String dateAsString) throws Exception {
		if(dateAsString == null) {
			return null;
		}
		
		return new LocalDate(dateAsString);
	}

	@Override
	public String marshal(LocalDate date) throws Exception {
		if(date == null) {
			return null;
		}
		
		return date.toString();
	}
}