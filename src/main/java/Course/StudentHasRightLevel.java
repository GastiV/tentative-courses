package Course;

import Actor.Student;
import Exception.StudentException;

public class StudentHasRightLevel implements CourseValidation {
    @Override
    public void validate(TentativeCourse course, Student student) {
        if(!(course.getLevel() == student.getLevel())) throw new StudentException("Student has wrong level");
    }
}
