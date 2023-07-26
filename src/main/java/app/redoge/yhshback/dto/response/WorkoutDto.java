package app.redoge.yhshback.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutDto(long id, List<TrainingDto> trainings, long userId, LocalDateTime startTime,
                         String name, LocalDateTime endTime, boolean removed){
}
