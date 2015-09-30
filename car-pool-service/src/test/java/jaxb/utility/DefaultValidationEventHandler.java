package jaxb.utility;

import org.apache.log4j.Logger;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * Implementation of JAXB's ValidationHandler interface. A ValidationHandler can
 * be registered with a JAXBContext object. When registered, and when a XML
 * schema is in use, the unmarshaller detects any problems with the structure of
 * the XML document being unmarshalled (i.e. occurrences where the document
 * doesn't conform to the schema). The unmarshaller informs the registered
 * ValidationHandler by calling its handleEvent() method.
 *
 * This implementation simply writes to standard out any problems identified
 * during validation of the XML document.
 *
 * @author Ian Warren
 *
 */
public class DefaultValidationEventHandler implements ValidationEventHandler {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(DefaultValidationEventHandler.class);

	@Override
	public boolean handleEvent(ValidationEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("handleEvent(ValidationEvent) - \nEVENT"); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) - SEVERITY:  " + event.getSeverity()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) - MESSAGE:  " + event.getMessage()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) - LINKED EXCEPTION:  " + event.getLinkedException()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) - LOCATOR"); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) -     LINE NUMBER:  " + event.getLocator().getLineNumber()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) -     COLUMN NUMBER:  " + event.getLocator().getColumnNumber()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) -     OFFSET:  " + event.getLocator().getOffset()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) -     OBJECT:  " + event.getLocator().getObject()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) -     NODE:  " + event.getLocator().getNode()); //$NON-NLS-1$
			logger.debug("handleEvent(ValidationEvent) -     URL:  " + event.getLocator().getURL()); //$NON-NLS-1$
		}
		return true;
	}
}