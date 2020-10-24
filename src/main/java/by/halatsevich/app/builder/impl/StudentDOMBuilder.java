package by.halatsevich.app.builder.impl;

import by.halatsevich.app.builder.AbstractStudentsBuilder;
import by.halatsevich.app.entity.Student;
import by.halatsevich.app.entity.StudentXmlTag;
import by.halatsevich.app.exception.ProjectException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public class StudentDOMBuilder extends AbstractStudentsBuilder {
    private static final String DEFAULT_FACULTY = "mmf";
    private DocumentBuilder documentBuilder;

    public StudentDOMBuilder() throws ProjectException {
        initialization();
    }

    public StudentDOMBuilder(Set<Student> students) throws ProjectException {
        super(students);
        initialization();
    }

    private void initialization() throws ProjectException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ProjectException("Error while creating document builder", e);
        }
    }

    @Override
    public void buildSetStudents(String fileName) throws ProjectException {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList studentsList = root.getElementsByTagName(StudentXmlTag.STUDENT.getValue());
            for (int i = 0; i < studentsList.getLength(); i++) {
                Element studentElement = (Element) studentsList.item(i);
                Student student = buildStudent(studentElement);
                students.add(student);
            }
        } catch (SAXException | IOException e) {
            throw new ProjectException("Error while parsing XML", e);
        }
    }

    private Student buildStudent(Element studentElement) {
        Student student = new Student();
        student.setLogin(studentElement.getAttribute(StudentXmlTag.LOGIN.getValue()));
        if (studentElement.getAttribute(StudentXmlTag.FACULTY.getValue()) == null || studentElement.getAttribute(StudentXmlTag.FACULTY.getValue()).isEmpty()) {
            student.setFaculty(DEFAULT_FACULTY);
        } else {
            student.setFaculty(studentElement.getAttribute(StudentXmlTag.FACULTY.getValue()));
        }
        student.setName(getElementByTagName(studentElement, StudentXmlTag.NAME.getValue()));
        Long telephone = Long.valueOf(getElementByTagName(studentElement, StudentXmlTag.TELEPHONE.getValue()));
        student.setTelephone(telephone);
        Student.Address address = student.getAddress();
        Element addressElement = (Element) studentElement.getElementsByTagName(StudentXmlTag.ADDRESS.getValue()).item(0);
        address.setCountry(getElementByTagName(addressElement, StudentXmlTag.COUNTRY.getValue()));
        address.setCity(getElementByTagName(addressElement, StudentXmlTag.CITY.getValue()));
        address.setStreet(getElementByTagName(addressElement, StudentXmlTag.STREET.getValue()));
        return student;
    }

    private String getElementByTagName(Element element, String tag) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Element targetElement = (Element) nodeList.item(0);
        return targetElement.getTextContent();
    }
}
