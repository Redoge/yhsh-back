package app.redoge.yhshback.utill.mappers;

import app.redoge.yhshback.dto.response.*;
import app.redoge.yhshback.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseDtoMapper {

    public UserDto mapUserToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEmailConfirmed(),
                user.getUserRole(),
                user.getSex(),
                user.getWeightKg(),
                user.getHeightSm(),
                user.isEnabled()
        );
    }
    public ActivityDto mapActivityToActivityDto(Activity activity){
        return new ActivityDto(
                activity.getId(),
                activity.getName(),
                activity.getNotation(),
                activity.getCreator().getId(),
                activity.isRemoved()
        );
    }
    public TrainingDto mapTrainingToTrainingDto(Training training){
        return new TrainingDto(
                training.getId(),
                mapActivityToActivityDto(training.getActivity()),
                training.getCount(),
                training.getStartTime(),
                training.isRemoved(),
                training.getMode()
        );
    }
    public WorkoutDto mapWorkoutToWorkoutDto(Workout workout){
        var trainings = workout.getTrainings().stream().map(this::mapTrainingToTrainingDto).toList();
        return new WorkoutDto(
                workout.getId(),
                trainings,
                workout.getUser().getId(),
                workout.getStartTime(),
                workout.getName(),
                workout.getEndTime(),
                workout.isRemoved()
        );
    }

    public FriendsDto mapFriendsToFriendsDto(Friends friends) {
        return new FriendsDto(
                friends.getId(),
                mapUserToUserDto(friends.getUser1()),
                mapUserToUserDto(friends.getUser2())
                );
    }

    public FriendshipDto mapFriendshipToFriendshipDto(Friendship friendship) {
        return new FriendshipDto(
                friendship.getId(),
                mapUserToUserDto(friendship.getSender()),
                mapUserToUserDto(friendship.getRecipient()),
                friendship.getCreated()
        );
    }
}
