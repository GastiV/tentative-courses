package Actor;

import Domain.Schedule;

import java.util.List;

public class Teacher implements CourseMember{
    List<Schedule> schedules;

    public Teacher(	List<Schedule> schedules){
        this.schedules = schedules;
    }
    public Boolean isAvailableAt(Schedule schedule){
        return schedules.contains(schedule);
    }

    //courses are tentative not final, should I fix a teacher in two courses at same time? this way I am.

}
