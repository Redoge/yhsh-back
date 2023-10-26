package app.redoge.yhshback.dto.response;

public record ActivityDto(long id, String name, ActivityTypeDto type, long creatorId, boolean removed, boolean withWeight) {
}
