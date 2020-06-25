package Course;
import Actor.Student;
import Actor.Teacher;
import Domain.Level;
import Domain.Modality;
import Domain.Schedule;
import Exception.TeacherException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TentativeCourse {
    Teacher teacher;
    Level level;

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    Schedule schedule;
    List<Student> students = new ArrayList();
    Modality modality;
    //Validation condition list to be added on instantiation
    List<CourseValidation> conditions;

    public TentativeCourse(List<CourseValidation>conditions){
        this.conditions = conditions;
    }
    public void assignTeacher(Teacher teacher){
        if(teacher.isAvailableAt(schedule)){
            this.teacher = teacher;
        }else{
            throw new TeacherException("Teacher is not available");
        }
    }
    public Integer studentCount(){
        return students.size();
    }
    public Integer getMaximumSize(){
        return modality.maximumSize();
    }

    //when creating a new course we can set a list of validation in order
    public void assignStudent(Student student){
        for (final CourseValidation condition : conditions) {
            condition.validate(this, student);
        }
        students.add(student);
    }
    public void assignStudents(List<Student> students){
        for (Student student: students) {
            this.assignStudent(student);
        }
    }

    public Boolean hasStudent(Student student){
        return this.students.contains(student);
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public Level getLevel() { return level; }
    public Teacher getTeacher(){
        return teacher;
    }
    public List<Student> getStudents(){
        return students;
    }
    public Schedule getSchedule() { return schedule; }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public Modality getModality() {
        return modality;
    }
}

