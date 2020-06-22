package Actor;

import Domain.Level;
import Domain.Modality;
import Domain.Schedule;

import java.util.List;

public class Student implements CourseMember{
    //Students would probably want to change their config
    Modality modality;

    public Level getLevel() {
        return level;
    }

    Level level;
    List<Schedule> schedules;

    public Student(List<Schedule> schedules){
        this.schedules = schedules;
    }

    public Boolean isAvailableAt(Schedule schedule){
        return schedules.contains(schedule);
    }
    public void setModality(Modality modality){
        this.modality = modality;
    }
    public void setLevel(Level level){
        this.level = level;
    }

    public Modality getModality() {
        return modality;
    }
}
