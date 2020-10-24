package by.halatsevich.app.builder.handler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class StudentErrorHandler extends DefaultHandler {
    private static final Logger logger = LogManager.getLogger(StudentErrorHandler.class);
    private static final String REGEX_DELIMITER = " : ";
    private static final String REGEX_HYPHEN = " - ";

    @Override
    public void warning(SAXParseException e) throws SAXException {
        logger.log(Level.WARN, getLineAddress(e) + REGEX_HYPHEN + e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        logger.log(Level.ERROR, getLineAddress(e) + REGEX_HYPHEN + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        logger.log(Level.FATAL, getLineAddress(e) + REGEX_HYPHEN + e.getMessage());
    }

    private String getLineAddress(SAXParseException e) {
        return e.getLineNumber() + REGEX_DELIMITER + e.getColumnNumber();
    }
}
