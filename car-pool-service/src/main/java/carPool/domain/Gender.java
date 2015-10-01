package carPool.domain;

/**
 * Enumerated type to represent gender.
 *
 */
public enum Gender {
	MALE, FEMALE;
	
	/**
	 * Return a Gender value that corresponds to a String value.
	 */
	public static Gender fromString(String text) {
	    if (text != null) {
	      for (Gender g : Gender.values()) {
	        if (text.equalsIgnoreCase(g.toString())) {
	          return g;
	        }
	      }
	    }
	    return null;
	  }
}
