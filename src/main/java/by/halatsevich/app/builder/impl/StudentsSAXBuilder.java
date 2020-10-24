package by.halatsevich.app.builder.impl;

import by.halatsevich.app.builder.AbstractStudentsBuilder;
import by.halatsevich.app.builder.handler.StudentErrorHandler;
import by.halatsevich.app.builder.handler.StudentHandler;
import by.halatsevich.app.entity.Student;
import by.halatsevich.app.exception.ProjectException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class StudentsSAXBuilder extends AbstractStudentsBuilder {
    private StudentHandler studentHandler;
    private StudentErrorHandler studentErrorHandler;
    private XMLReader reader;

    public StudentsSAXBuilder() throws ProjectException {
        initialization();
    }

    public StudentsSAXBuilder(Set<Student> students) throws ProjectException {
        super(students);
        initialization();
    }

    private void initialization() throws ProjectException {
        studentHandler = new StudentHandler();
        studentErrorHandler = new StudentErrorHandler();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
            reader.setContentHandler(studentHandler);
            reader.setErrorHandler(studentErrorHandler);
        } catch (SAXException | ParserConfigurationException e) {
            throw new ProjectException("Error while creating SAX parser", e);
        }
    }

    @Override
    public void buildSetStudents(String fileName) throws ProjectException {
        try {
            reader.parse(fileName);
            students = studentHandler.getStudents();
        } catch (IOException | SAXException e) {
            throw new ProjectException("Error while parsing XML", e);
        }
    }
}
