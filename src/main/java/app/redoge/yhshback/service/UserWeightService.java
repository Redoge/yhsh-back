package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.UserWeight;
import app.redoge.yhshback.repository.UserWeightRepository;
import app.redoge.yhshback.service.interfaces.IUserWeightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserWeightService implements IUserWeightService {
    private final UserWeightRepository repository;
    @Override
    public UserWeight saveUserWeight(UserWeight userWeight) {
        return repository.save(userWeight);
    }

    @Override
    public void removeUserWeight(UserWeight userWeight) {
        repository.delete(userWeight);
    }

    @Override
    public boolean isWeightExist(List<UserWeight> userWeightList, UserWeight userWeight) {
        if (userWeightList.isEmpty()) {
            return false;
        }
        return userWeightList
                .stream()
                .anyMatch(
                        w -> w.getWeight().equals(userWeight.getWeight()) &&
                                w.getDate().toString().equals(userWeight.getDate().toString())
                );
    }
}
