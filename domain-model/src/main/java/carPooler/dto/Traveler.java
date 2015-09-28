package carPooler.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/* Class to represent a Traveler. 
 * 
 * An instance of this class represents a DTO Traveler. A DTO Traveler includes
 * a subset of Traveler data, and DTO Travelers objects are exchanged between 
 * clients and the Traveler Web service.
 * 
 * A DTO Traveler is described by:
 * - Personal details: email, username, gender, home address, current location;
 * - Last know location: latitude/longitude position.
 * 
 * A Traveler is uniquely identified by an id value of type long.
 * 
 */
@XmlRootElement(name="Traveler")
@XmlAccessorType(XmlAccessType.FIELD)
public class Traveler {

}
