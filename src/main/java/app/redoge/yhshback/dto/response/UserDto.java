package app.redoge.yhshback.dto.response;

import app.redoge.yhshback.entity.enums.UserRole;

public record UserDto(long id, String username, String email, boolean emailConfirmed, UserRole userRole,
                      String sex, int weightKg, int heightSm, boolean enabled) {
}
