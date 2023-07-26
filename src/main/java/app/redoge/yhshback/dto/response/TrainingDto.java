package app.redoge.yhshback.dto.response;

import app.redoge.yhshback.entity.enums.TrainingMode;

import java.time.LocalDateTime;

public record TrainingDto(long id, ActivityDto activity, int count,
                          LocalDateTime startTime, boolean removed, TrainingMode mode) {
}
