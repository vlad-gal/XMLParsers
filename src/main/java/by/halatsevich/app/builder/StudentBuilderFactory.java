package by.halatsevich.app.builder;

import by.halatsevich.app.builder.impl.StudentDOMBuilder;
import by.halatsevich.app.builder.impl.StudentsEventStaxBuilder;
import by.halatsevich.app.builder.impl.StudentsSAXBuilder;
import by.halatsevich.app.builder.impl.StudentsStreamStaxBuilder;
import by.halatsevich.app.exception.ProjectException;

public class StudentBuilderFactory {
    private enum TypeParser {
        SAX, DOM, EVENT_STAX, STREAM_STAX
    }

    private StudentBuilderFactory() {
    }

    public static AbstractStudentsBuilder createStudentBuilder(String typeParser) throws ProjectException {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new StudentDOMBuilder();
            case SAX:
                return new StudentsSAXBuilder();
            case EVENT_STAX:
                return new StudentsEventStaxBuilder();
            case STREAM_STAX:
                return new StudentsStreamStaxBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
