package app.redoge.yhshback.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutSaveDto(List<TrainingIntoWorkoutSaveDto> trainings, String username, LocalDateTime startTime, LocalDateTime endTime) {
}
