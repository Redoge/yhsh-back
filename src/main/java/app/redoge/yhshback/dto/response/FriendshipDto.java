package app.redoge.yhshback.dto.response;


import java.time.LocalDateTime;

public record FriendshipDto(long id, UserDto sender, UserDto receiver, LocalDateTime created) {
}