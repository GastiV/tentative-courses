import Actor.Student;
import Actor.Teacher;
import Course.*;
import Domain.Level;
import Domain.Modality;
import Domain.Schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TentativeCoursesGenerator {
        List<Student> students;
        List<Teacher> teachers;

        public List<TentativeCourse> getTentativeCourses() {
                return tentativeCourses;
        }

        //Collections.emptyList() throws Exception
        List<TentativeCourse> tentativeCourses = new ArrayList<>();;

        public TentativeCoursesGenerator(List<Student> students, List<Teacher> teachers){
                this.students = students;
                this.teachers = teachers;
        }

        public void generateTentativeCourses(){
                List<Modality> modalities = Arrays.asList(Modality.GROUP, Modality.INDIVIDUAL);
                List<Level> levels = Arrays.asList(Level.BEGINNER, Level.PREINTERMEDIATE, Level.INTERMEDIATE, Level.UPPERINTERMEDIATE, Level.ADVANCED);
                List<CourseValidation> conditions= Arrays.asList(new CourseHasRoomAvailable(), new StudentHasRightModality(),
                                                                 new StudentIsAvailable(), new StudentHasRightLevel());
                List<Schedule>teacherSchedules = this.getAvailableTeachersSchedules();
                List<Student> students;
                Teacher teacher;

                for (Schedule schedule:teacherSchedules) {
                        teacher = this.findAvailableTeacher(schedule);
                        if (teacher == null) { continue; }
                        for (Modality modality:modalities) {
                                for (Level level:levels) {

                                        if (modality == Modality.GROUP){
                                                students = takeStudents(6, this.getAvailableStudentsByModalityAndLevel(schedule, modality, level));
                                        }else{
                                                students= takeStudents(1, this.getAvailableStudentsByModalityAndLevel(schedule, modality, level));
                                        }
                                        if (students == null || students.isEmpty()) { continue; }
                                        TentativeCourse newCourse = this.generateCourse(conditions, students, teacher, level, modality, schedule);
                                        tentativeCourses.add(newCourse);
                                }
                        }
                }
        }

        public List<Student> getUnassignedStudents(){
                return students.stream()
                        .filter(student -> !studentAssigned(student)).collect(Collectors.toList());
        }
        private Boolean studentAssigned(Student student){
                return tentativeCourses.stream().anyMatch(tentativeCourse -> tentativeCourse.hasStudent(student));
        }
        private TentativeCourse generateCourse(List<CourseValidation> conditions,List<Student> students,Teacher teacher,
                                               Level level, Modality modality, Schedule schedule){
                TentativeCourse tentativeCourse = new TentativeCourse(conditions);
                tentativeCourse.setSchedule(schedule);
                tentativeCourse.assignTeacher(teacher);
                tentativeCourse.setLevel(level);
                tentativeCourse.setModality(modality);
                tentativeCourse.assignStudents(students);
                return tentativeCourse;
        }
        private List<Student> takeStudents(Integer amount, List<Student> students){
                return students.stream()
                        .limit(amount)
                        .collect(Collectors.toList());
        }

        private Teacher findAvailableTeacher(Schedule schedule){
                return this.getAvailableTeachers(schedule).stream().filter(teacher -> teacher.isAvailableAt(schedule))
                        .collect(Collectors.toList()).get(0);
        }
        private List<Teacher> getAvailableTeachers(Schedule schedule){
                return teachers.stream()
                        .filter(teacher -> teacher.isAvailableAt(schedule))
                        .collect(Collectors.toList());
        }
        //get all teacher schedules available
        private List<Schedule> getAvailableTeachersSchedules(){
                return teachers
                        .stream()
                        .flatMap(listContainer -> listContainer.getSchedules().stream())
                        .collect(Collectors.toList());
        }
        private List<Student> getAvailableStudentsByModalityAndLevel(Schedule schedule, Modality modality, Level level){
                //TODO IMPROVEMENT
                //When filtering available students, we could possibly create a custom condition to match deffered students
                return students.stream()
                        .filter(student ->
                                student.getLevel() == level &&
                                student.getModality() == modality &&
                                student.isAvailableAt(schedule))
                        .collect(Collectors.toList());
        }
}
