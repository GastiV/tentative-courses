package Actor;

import Domain.Schedule;

public interface CourseMember {
    Boolean isAvailableAt(Schedule schedule);
}
