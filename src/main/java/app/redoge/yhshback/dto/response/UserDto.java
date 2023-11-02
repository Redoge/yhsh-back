package app.redoge.yhshback.dto.response;

import app.redoge.yhshback.entity.UserWeight;
import app.redoge.yhshback.entity.enums.UserRole;

import java.util.List;

public record UserDto(long id, String username, String email, boolean emailConfirmed, UserRole userRole,
                      String sex, List<UserWeight> weightKg, int heightSm, boolean enabled) {
}
