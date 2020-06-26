package Course;

import Actor.Student;
import Exception.CourseException;

public class CourseHasRoomAvailable implements CourseValidation {
    public void validate(TentativeCourse course, Student student) {
        if(!(course.courseHasRoomAvailable())) throw new CourseException("Course is full");
    }
}
