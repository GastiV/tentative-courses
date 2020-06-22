package Course;

import Actor.Student;
import Exception.CourseException;

public class StudentHasRightModality implements CourseValidation{
    public void validate(TentativeCourse course, Student student) {
        if(!(course.getModality() == student.getModality())) throw new CourseException("Course is full");
    }
}
