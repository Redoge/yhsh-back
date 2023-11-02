package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.UserWeight;
import app.redoge.yhshback.repository.UserWeightRepository;
import app.redoge.yhshback.service.interfaces.IUserWeightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
