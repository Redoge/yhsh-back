package app.redoge.yhshback.dto;

import java.time.LocalDateTime;

public record TrainingSaveRequestDto(long activityId, long userId, int count, LocalDateTime start) {
}
