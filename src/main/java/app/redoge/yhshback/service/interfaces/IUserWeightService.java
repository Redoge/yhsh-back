package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.UserWeight;

public interface IUserWeightService {
    UserWeight saveUserWeight(UserWeight userWeight);
    void removeUserWeight(UserWeight userWeight);
}
