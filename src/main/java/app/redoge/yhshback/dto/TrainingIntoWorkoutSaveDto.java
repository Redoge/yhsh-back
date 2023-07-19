package app.redoge.yhshback.dto;

import java.time.LocalDateTime;

public record TrainingIntoWorkoutSaveDto(long activityId,  int count, LocalDateTime start) {
}
