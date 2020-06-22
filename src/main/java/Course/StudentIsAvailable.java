package Course;

import Actor.Student;
import Exception.StudentException;
public class StudentIsAvailable implements CourseValidation{
    public void validate(TentativeCourse course, Student student) {
        if(!student.isAvailableAt(course.getSchedule())) throw new StudentException("Student is not available");
    }
}
