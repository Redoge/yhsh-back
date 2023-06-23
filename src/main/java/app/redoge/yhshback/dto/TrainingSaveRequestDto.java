package app.redoge.yhshback.dto;

import java.time.LocalDateTime;

public record TrainingSaveRequestDto(long activityId, String username, int count, LocalDateTime start) {
}
