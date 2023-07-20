package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.UserActivityStatsDto;
import app.redoge.yhshback.entity.User;

import java.util.List;

public interface IStatsService {
    List<UserActivityStatsDto> getUserActivityStatsListByUser(User user);
}
