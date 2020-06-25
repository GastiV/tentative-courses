import Actor.Student;
import Actor.Teacher;
import Course.*;
import Domain.BusinessDay;
import Domain.Level;
import Domain.Modality;
import Domain.Schedule;
import Exception.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class CourseTest {
    Schedule monSeventeen;
    Schedule wedNine;
    Schedule thuFifteen;

    Teacher teacherMonSeventeen;
    Teacher teacherWedNine;
    Teacher teacherThuFifteen;

    Student studentMonSeventeen;
    Student studentMonSeventeenB;
    Student studentMonSeventeenC;
    Student studentMonSeventeenD;
    Student studentMonSeventeenE;
    Student studentMonSeventeenF;
    Student studentMonSeventeenG;

    Student studentWedNine;
    Student studentWedNineB;

    List<Student> students;
    List<Teacher> teachers;
    TentativeCourse tentativeCourse;

    @Before
    public void init(){

        //Fixture

        tentativeCourse = new TentativeCourse(Arrays.asList(new CourseHasRoomAvailable(), new StudentHasRightModality(),
                          new StudentIsAvailable(), new StudentHasRightLevel()));
        tentativeCourse.setLevel(Level.BEGINNER);

        monSeventeen = new Schedule(17, BusinessDay.MONDAY);
        wedNine = new Schedule(9,BusinessDay.WEDNESDAY);
        thuFifteen = new Schedule(15,BusinessDay.THURSDAY);

        List<Schedule> monSeventeenOnly = Arrays.asList(monSeventeen);
        List<Schedule> wedNineOnly = Arrays.asList(wedNine);
        List<Schedule> thuFifteenOnly = Arrays.asList(thuFifteen);

        //Teachers

        teacherMonSeventeen = new Teacher(monSeventeenOnly);
        teacherWedNine = new Teacher(wedNineOnly);
        teacherThuFifteen = new Teacher(thuFifteenOnly);


        //Students for group Course
        studentMonSeventeen = new Student(monSeventeenOnly);
        studentMonSeventeenB = new Student(monSeventeenOnly);
        studentMonSeventeenC = new Student(monSeventeenOnly);
        studentMonSeventeenD = new Student(monSeventeenOnly);
        studentMonSeventeenE = new Student(monSeventeenOnly);
        studentMonSeventeenF = new Student(monSeventeenOnly);
        studentMonSeventeenG = new Student(monSeventeenOnly);

        studentMonSeventeen.setModality(Modality.GROUP);
        studentMonSeventeenB.setModality(Modality.GROUP);
        studentMonSeventeenC.setModality(Modality.GROUP);
        studentMonSeventeenD.setModality(Modality.GROUP);
        studentMonSeventeenE.setModality(Modality.GROUP);
        studentMonSeventeenF.setModality(Modality.GROUP);
        studentMonSeventeenG.setModality(Modality.GROUP);
        studentMonSeventeen.setLevel(Level.BEGINNER);
        studentMonSeventeenB.setLevel(Level.BEGINNER);
        studentMonSeventeenC.setLevel(Level.BEGINNER);
        studentMonSeventeenD.setLevel(Level.BEGINNER);
        studentMonSeventeenE.setLevel(Level.BEGINNER);
        studentMonSeventeenF.setLevel(Level.BEGINNER);
        studentMonSeventeenG.setLevel(Level.BEGINNER);


        //Students for individual
        studentWedNine = new Student(wedNineOnly);
        studentWedNineB = new Student(wedNineOnly);

        studentWedNine.setModality(Modality.INDIVIDUAL);
        studentWedNineB.setModality(Modality.INDIVIDUAL);
        studentWedNine.setLevel(Level.PREINTERMEDIATE);
        studentWedNineB.setLevel(Level.ADVANCED);


        students = Arrays.asList(studentMonSeventeen,
                studentMonSeventeenB,
                studentMonSeventeenC,
                studentMonSeventeenD,
                studentMonSeventeenE,
                studentMonSeventeenF,
                studentMonSeventeenG,
                studentWedNine,
                studentWedNineB
                );

        teachers = Arrays.asList(teacherMonSeventeen, teacherThuFifteen, teacherWedNine);



    }

    //Los cursos tienen que respetar el horario que el docente tiene disponible.
    @Test
    public void courseRespectsTeachersSchedule(){
        tentativeCourse .setSchedule(wedNine);
        tentativeCourse.assignTeacher(teacherWedNine);
        Assert.assertTrue(tentativeCourse.getTeacher() == teacherWedNine);
    }
    @Test (expected = TeacherException.class)
    public void courseRespectsTeachersScheduleOrThrowException(){
        tentativeCourse .setSchedule(monSeventeen);
        tentativeCourse.assignTeacher(teacherWedNine);
    }

    //Los cursos tienen que respetar el horario disponible de los estudiantes
    @Test
    public void courseAssignsAvailableStudent(){
        tentativeCourse.setSchedule(monSeventeen);
        tentativeCourse.setModality(Modality.GROUP);
        tentativeCourse.assignStudent(studentMonSeventeen);
        Assert.assertTrue(tentativeCourse.getStudents().contains(studentMonSeventeen));
    }
    @Test (expected = StudentException.class)
    public void courseAssignsAvailableStudentOrThrowsException(){
        tentativeCourse.setSchedule(wedNine);
        tentativeCourse.setModality(Modality.GROUP);
        tentativeCourse.assignStudent(studentMonSeventeen);
    }

    //Todos los inscriptos en el curso tienen que tener el mismo nivel
    //expect exception
    @Test
    public void studenstGotSameLevel(){
        tentativeCourse .setSchedule(monSeventeen);
        tentativeCourse.setModality(Modality.GROUP);
        tentativeCourse.assignStudent(studentMonSeventeen);
        tentativeCourse.assignStudent(studentMonSeventeenB);
        tentativeCourse.assignStudent(studentMonSeventeenC);
        Assert.assertTrue(tentativeCourse.getStudents().stream().allMatch(s-> s.getLevel() == tentativeCourse.getLevel()));
    }

    @Test (expected = StudentException.class)
    public void studenstGotSameLevelOrThrowException(){
        Level advanced = Level.ADVANCED;
        tentativeCourse .setSchedule(monSeventeen);
        tentativeCourse.setModality(Modality.GROUP);
        studentMonSeventeenC.setLevel(advanced);
        tentativeCourse.assignStudent(studentMonSeventeen);
        tentativeCourse.assignStudent(studentMonSeventeenB);
        tentativeCourse.assignStudent(studentMonSeventeenC);
    }

    //Los cursos grupales pueden contener hasta 6 inscriptos.
    @Test (expected = CourseException.class)
    public void groupCoursesUpToSixStudents(){
        tentativeCourse .setSchedule(monSeventeen);
        tentativeCourse.setModality(Modality.GROUP);

        tentativeCourse.assignStudent(studentMonSeventeen);
        tentativeCourse.assignStudent(studentMonSeventeenB);
        tentativeCourse.assignStudent(studentMonSeventeenC);
        tentativeCourse.assignStudent(studentMonSeventeenD);
        tentativeCourse.assignStudent(studentMonSeventeenE);
        tentativeCourse.assignStudent(studentMonSeventeenF);
        tentativeCourse.assignStudent(studentMonSeventeenG);
    }
    //Los cursos individuales sólo pueden contener 1 inscripto.
    @Test (expected = CourseException.class)
    public void individualCoursesUpToOneStudent(){
        tentativeCourse .setSchedule(monSeventeen);
        tentativeCourse.setModality(Modality.INDIVIDUAL);
        studentMonSeventeen.setModality(Modality.INDIVIDUAL);
        studentMonSeventeenB.setModality(Modality.INDIVIDUAL);
        tentativeCourse.assignStudent(studentMonSeventeen);
        tentativeCourse.assignStudent(studentMonSeventeenB);
    }
    //Todos los inscriptos tienen que la misma modalidad. Ej. Si un estudiante eligió modalidad individual no se los puede inscribir en curso grupal
    @Test (expected = CourseException.class)
    public void studentsGotSameModality(){
        tentativeCourse .setSchedule(monSeventeen);
        tentativeCourse.setModality(Modality.GROUP);


        tentativeCourse.assignStudent(studentMonSeventeenC);
        tentativeCourse.assignStudent(studentMonSeventeenD);
        tentativeCourse.assignStudent(studentMonSeventeenE);
        //set wrong modality
        studentMonSeventeenF.setModality(Modality.INDIVIDUAL);
        tentativeCourse.assignStudent(studentMonSeventeenF);
    }


    @Test
    public void courseGetsGenerated(){
        TentativeCoursesGenerator tentativeCoursesGenerator = new TentativeCoursesGenerator(students, teachers);
        tentativeCoursesGenerator.generateTentativeCourses();
        Assert.assertTrue(!tentativeCoursesGenerator.getTentativeCourses().isEmpty() );
    }
    @Test
    public void studentsKeepUnassigned(){
        TentativeCoursesGenerator tentativeCoursesGenerator = new TentativeCoursesGenerator(students, teachers);
        tentativeCoursesGenerator.generateTentativeCourses();
        Assert.assertTrue(!tentativeCoursesGenerator.getUnassignedStudents().isEmpty() );
    }
}
