package app.redoge.yhshback.testUtill;

import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class GenerateEntityUtil {
    private List<User> users;
    private List<Training> trainings;
    private List<Activity> activities;
    private static GenerateEntityUtil instance;
    private GenerateEntityUtil() {}
    public static GenerateEntityUtil getInstance(){
        if(isNull(instance)){
            instance = new GenerateEntityUtil();
            instance.generateTraining();
        }
        return instance;
    }
    public List<User> generateUser(){
        if(this.users == null) {
            var users = new ArrayList<User>();
            for (var i = 1; i <= 10; i++) {
                var user = User.builder()
                        .id(i)
                        .username("Username" + i)
                        .userRole(UserRole.USER)
                        .enabled(true)
                        .email(String.format("email%s@gmail.com", i))
                        .password("password")
                        .activities(new ArrayList<>())//TODO
                        .build();
                users.add(user);
            }
            this.users = users;
        }
        return this.users;
    }

    public List<Activity> generateActivity(){
        var users = generateUser();
        if(this.activities == null) {
            var activities = new ArrayList<Activity>();
            for(var i = 1;  i<=10; i++){
                var activity = Activity.builder()
                        .creator(users.get(i-1))
                        .id(i)
                        .name("activity"+i)
                        .notation("notation"+i)
                        .trainings(new ArrayList<>())
                        .removed(false)
                        .build();
                users.get(i-1).setActivities(new ArrayList<>(List.of(activity)));
                activities.add(activity);
            }
            this.activities = activities;
        }
        return this.activities;
    }
    public List<Training> generateTraining(){
        var activities = generateActivity();
        if(this.trainings == null) {
            var trainings = new ArrayList<Training>();
            for(var i = 1; i <= 10; i++){
                var training = Training.builder()
                        .id(i)
                        .count(i)
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.now())
                        .removed(false)
                        .activity(activities.get(i-1))
                        .build();
                activities.get(i-1).setTrainings(new ArrayList<>(List.of(training)));
                trainings.add(training);
            }
            this.trainings = trainings;
        }
        return this.trainings;
    }
}
