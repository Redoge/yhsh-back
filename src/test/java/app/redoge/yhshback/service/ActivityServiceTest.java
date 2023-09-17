//package app.redoge.yhshback.service;
//
//import app.redoge.yhshback.dto.ActivitySaveRequestDto;
//import app.redoge.yhshback.entity.Activity;
//import app.redoge.yhshback.entity.User;
//import app.redoge.yhshback.exception.BadRequestException;
//import app.redoge.yhshback.exception.NotFoundException;
//import app.redoge.yhshback.exception.UserNotFoundException;
//import app.redoge.yhshback.repository.ActivityRepository;
//import app.redoge.yhshback.testUtill.GenerateEntityUtil;
//import app.redoge.yhshback.utill.validators.DtoValidators;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//@ExtendWith(MockitoExtension.class)
//class ActivityServiceTest {
//    @Mock
//    private ActivityRepository activityRepository;
//    @Mock
//    private UserService userService;
//    @Mock
//    private DtoValidators dtoValidators;
//    @InjectMocks
//    private ActivityService activityService;
//    private List<Activity> expectedActivities;
//
//    @BeforeEach
//    void setUp() {
//        expectedActivities = GenerateEntityUtil.getInstance().generateActivity();
//    }
//
//    @Test
//    void getAll() {
//        when(activityRepository.findAllByRemoved(false)).thenReturn(expectedActivities);
//        var actualActivities = activityService.getAll();
//
//        assertEquals(expectedActivities, actualActivities);
//    }
//    @Test
//    void save() {
//        var emptyActivity = new Activity();
//        var correctActivity = expectedActivities.get(0);
//
//        assertThrows(BadRequestException.class, () -> activityService.save(emptyActivity));
//        assertDoesNotThrow(() -> activityService.save(correctActivity));
//    }
//    @Test
//    void saveByDtoCorrect() throws UserNotFoundException, BadRequestException {
//        var activitySaveRequestDto = new ActivitySaveRequestDto("Activity1", "Notation1", "Username1");
//        var user = new User();
//        var activity = Activity.builder()
//                .creator(user)
//                .removed(false)
//                .name(activitySaveRequestDto.name())
//                .notation(activitySaveRequestDto.notation())
//                .build();
//
//        when(dtoValidators.activitySaveRequestDtoIsValid(activitySaveRequestDto)).thenReturn(true);
//        when(userService.findUserByUsername(activitySaveRequestDto.username())).thenReturn(user);
//        when(activityRepository.save(activity)).thenReturn(activity);
//        var actualActivity = activityService.saveByDto(activitySaveRequestDto);
//
//        assertEquals(actualActivity.getName(), activitySaveRequestDto.name());
//    }
//    @Test
//    void saveByDtoInvalidDto(){
//        var activitySaveRequestDto = new ActivitySaveRequestDto("", "", "");
//        when(dtoValidators.activitySaveRequestDtoIsValid(activitySaveRequestDto)).thenReturn(false);
//
//        assertThrows(BadRequestException.class, ()->activityService.saveByDto(activitySaveRequestDto));
//    }
//    @Test
//    void saveByDtoInvalidUser() throws UserNotFoundException {
//        var activitySaveRequestDto = new ActivitySaveRequestDto("", "", "");
//        when(dtoValidators.activitySaveRequestDtoIsValid(activitySaveRequestDto)).thenReturn(true);
//        when(userService.findUserByUsername(activitySaveRequestDto.username())).thenThrow(UserNotFoundException.class);
//
//        assertThrows(UserNotFoundException.class, ()->activityService.saveByDto(activitySaveRequestDto));
//    }
////    @Test
////    void removeById() throws NotFoundException, BadRequestException {
////        when(activityRepository.findByIdAndRemoved(1, false))
////                .thenReturn(Optional.ofNullable(expectedActivities.get(0)));
////
////        assertTrue(activityService.removeById(1));
////    }
//    @Test
//    void removeById_Incorrect(){
//        when(activityRepository.findByIdAndRemoved(1, false))
//                .thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> activityService.getById(1));
//    }
//
//    @Test
//    void getAllByCreatorUsername() {
//        var activityList = new ArrayList<>(List.of(expectedActivities.get(0)));
//        when(activityRepository.findByCreatorUsernameAndRemoved("Username1", false)).thenReturn(activityList);
//        var actualActivity = activityService.getAllByCreatorUsername("Username1");
//
//        assertEquals(activityList, actualActivity);
//    }
//
//    @Test
//    void getAllByCreatorId() {
//        var activityList = new ArrayList<>(List.of(expectedActivities.get(0)));
//        when(activityRepository.findByCreatorIdAndRemoved(1L, false))
//                .thenReturn(activityList);
//        var actualActivity = activityService.getAllByCreatorId(1L);
//
//        assertEquals(activityList, actualActivity);
//    }
//
////    @Test
////    void getById() throws NotFoundException {
////        when(activityRepository.findByIdAndRemoved(1, false))
////                .thenReturn(Optional.ofNullable(expectedActivities.get(0)));
////        var actualActivity = activityService.getById(1);
////
////        assertEquals(expectedActivities.get(0), actualActivity);
////    }
//    @Test
//    void getById_UserNotFound() {
//        when(activityRepository.findByIdAndRemoved(1, false)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> activityService.getById(1));
//    }
//}