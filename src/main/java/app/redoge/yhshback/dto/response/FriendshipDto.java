package app.redoge.yhshback.dto.response;


import java.time.LocalDateTime;

public record FriendshipDto(long id, long senderId, long receiverId, LocalDateTime created) {
}