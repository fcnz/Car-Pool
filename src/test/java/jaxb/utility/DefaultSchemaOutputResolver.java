package jaxb.utility;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 * Class DefaultSchemaOutputResolver extends the JAXB framework to implement a
 * handler for storing a generated XML schema document.
 *
 * An instance of this class is registered with a JAXBContext object, so that
 * when it processes a Java class, it can generate an XML schema document for
 * the class.
 *
 * @author Ian Warren
 *
 */
public class DefaultSchemaOutputResolver extends SchemaOutputResolver {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DefaultSchemaOutputResolver.class);

	private final String _outputPath;
	private final String _schemaFileName;

	public DefaultSchemaOutputResolver(String outputPath, String schemaFileName) {
		_outputPath = outputPath;
		_schemaFileName = schemaFileName;
	}

	@Override
	public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
		File schemaFile = new File(_outputPath + _schemaFileName);

		logger.debug("Schema file at: " + _outputPath + _schemaFileName);

		StreamResult result = new StreamResult(schemaFile);
		result.setSystemId(schemaFile.toURI().toURL().toString());
		return result;
	}
}