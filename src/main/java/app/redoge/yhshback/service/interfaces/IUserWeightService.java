package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.UserWeight;

import java.util.List;

public interface IUserWeightService {
    UserWeight saveUserWeight(UserWeight userWeight);
    void removeUserWeight(UserWeight userWeight);

    boolean isWeightExist(List<UserWeight> userWeightList, UserWeight userWeight);
}
