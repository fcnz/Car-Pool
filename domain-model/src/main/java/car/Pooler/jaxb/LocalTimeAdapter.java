package car.Pooler.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalTime;

/** 
 * JAXB XML adapter to convert between Joda LocalTime instances and Strings.
 * LocalTime objects are marshalled as Strings, and unmarshalled back into 
 * LocalTime instances.
 * 
 */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

	@Override
	public LocalTime unmarshal(String timeAsString) throws Exception {
		if(timeAsString == null) {
			return null;
		}
		
		return new LocalTime(timeAsString);
	}

	@Override
	public String marshal(LocalTime time) throws Exception {
		if(time == null) {
			return null;
		}
		
		return time.toString();
	}
}
