package by.halatsevich.app.builder.handler;

import by.halatsevich.app.entity.Student;
import by.halatsevich.app.entity.StudentXmlTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class StudentHandler extends DefaultHandler {
    private static final String DEFAULT_FACULTY = "mmf";
    private Set<Student> students;
    private Student current;
    private StudentXmlTag currentXmlTag;
    private EnumSet<StudentXmlTag> withText;

    public StudentHandler() {
        students = new HashSet<>();
        withText = EnumSet.range(StudentXmlTag.NAME, StudentXmlTag.STREET);
    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (StudentXmlTag.STUDENT.getValue().equals(qName)) {
            current = new Student();
            for (int i = 0; i < attributes.getLength(); i++) {
                if (attributes.getLength() == 1) {
                    if (attributes.getLocalName(i).equals(StudentXmlTag.LOGIN.getValue())) {
                        current.setLogin(attributes.getValue(i));
                    }
                    current.setFaculty(DEFAULT_FACULTY);
                } else {
                    if (attributes.getLocalName(i).equals(StudentXmlTag.LOGIN.getValue())) {
                        current.setLogin(attributes.getValue(i));
                    }
                    if (attributes.getLocalName(i).equals(StudentXmlTag.FACULTY.getValue())) {
                        current.setFaculty(attributes.getValue(i));
                    }
                }
            }
        } else {
            StudentXmlTag temp = StudentXmlTag.valueOf(qName.toUpperCase());
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (StudentXmlTag.STUDENT.getValue().equals(qName)) {
            students.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length).trim();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case NAME:
                    current.setName(data);
                    break;
                case TELEPHONE:
                    current.setTelephone(Long.parseLong(data));
                    break;
                case STREET:
                    current.getAddress().setStreet(data);
                    break;
                case CITY:
                    current.getAddress().setCity(data);
                    break;
                case COUNTRY:
                    current.getAddress().setCountry(data);
                    break;
                default:
                    throw new EnumConstantNotPresentException(currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}
