//package app.redoge.yhshback.service;
//
//import app.redoge.yhshback.dto.TrainingSaveRequestDto;
//import app.redoge.yhshback.entity.Activity;
//import app.redoge.yhshback.entity.Training;
//import app.redoge.yhshback.exception.BadRequestException;
//import app.redoge.yhshback.exception.NotFoundException;
//import app.redoge.yhshback.repository.TrainingRepository;
//import app.redoge.yhshback.testUtill.GenerateEntityUtil;
//import app.redoge.yhshback.utill.validators.DtoValidators;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class TrainingServiceTest {
//    @Mock
//    private TrainingRepository trainingRepository;
//    @Mock
//    private ActivityService activityService;
//    @Mock
//    private DtoValidators dtoValidators;
//    @InjectMocks
//    private TrainingService trainingService;
//    private List<Training> expectedTrainings;
//    private List<Activity> expectedActivity;
//    @BeforeEach
//    void setUp() {
//        expectedTrainings = GenerateEntityUtil.getInstance().generateTraining();
//        expectedActivity = GenerateEntityUtil.getInstance().generateActivity();
//    }
//
//    @Test
//    void save() throws BadRequestException {
//        var training = expectedTrainings.get(0);
//        when(trainingRepository.save(training)).thenReturn(training);
//        var actualTraining = trainingService.save(training);
//
//        assertEquals(training, actualTraining);
//    }
//    @Test
//    void saveIncorrect() {
//        var training = new Training();
//
//        assertThrows(BadRequestException.class, () -> trainingService.save(training));
//    }
//
//    @Test
//    void removeById() throws NotFoundException {
//        var training = expectedTrainings.get(0);
//        when(trainingRepository.findByIdAndRemovedAndActivityRemoved(1, false, false)).
//                thenReturn(Optional.of(training));
//        when(trainingRepository.save(training)).thenReturn(null);
//        assertTrue(trainingService.removeById(1));
//    }
//
//    @Test
//    void saveAndAddToActivity() throws BadRequestException {
//        var training = expectedTrainings.get(0);
//        when(trainingRepository.save(training)).thenReturn(training);
//        var actualTraining = trainingService.saveAndAddToActivity(training);
//
//        assertEquals(training, actualTraining);
//    }
//
//    @Test
//    void getTrainingById() throws NotFoundException {
//        var training = expectedTrainings.get(0);
//        when(trainingRepository.findByIdAndRemovedAndActivityRemoved(1, false, false)).
//                thenReturn(Optional.of(training));
//        var actualTraining = trainingService.getTrainingById(1);
//
//        assertEquals(training, actualTraining);
//    }
//    @Test
//    void getTrainingByIdIncorrect(){
//        when(trainingRepository.findByIdAndRemovedAndActivityRemoved(1, false, false)).
//                thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> trainingService.getTrainingById(1));
//    }
//    @Test
//    void saveByDto() throws NotFoundException, BadRequestException {
//        var trainingDto = new TrainingSaveRequestDto(1, "Username1", 1, LocalDateTime.now());
//        var activity = expectedActivity.get(0);
//        when(dtoValidators.trainingSaveRequestDtoIsValid(trainingDto)).thenReturn(true);
//        when(activityService.getById(1)).thenReturn(activity);
//        var expectedTraining = Training.builder()
//                .activity(activity)
//                .startTime(trainingDto.start())
//                .count(trainingDto.count())
//                .build();
//        when(trainingRepository.save(any())).thenReturn(expectedTraining);
//        var actualTraining =  trainingService.saveByDto(trainingDto);
//        assertEquals(expectedTraining.getCount(), actualTraining.getCount());
//    }
//    @Test
//    void saveByDtoIncorrect(){
//        var trainingDto = new TrainingSaveRequestDto(1, "Username1", 1, LocalDateTime.now());
//        when(dtoValidators.trainingSaveRequestDtoIsValid(trainingDto)).thenReturn(false);
//
//        assertThrows(BadRequestException.class, () -> trainingService.saveByDto(trainingDto));
//    }
//    @Test
//    void getAllTrainingByUserUsername() {
//        when(trainingRepository.getTrainingByActivityCreatorUsernameAndRemovedAndActivityRemoved("Username1", false, false)).
//                thenReturn(expectedTrainings);
//        var actualTrainings = trainingService.getAllTrainingByUserUsername("Username1");
//
//        assertEquals(expectedTrainings.size(), actualTrainings.size());
//    }
//
//    @Test
//    void getAllTrainingByUserId() {
//        when(trainingRepository.getTrainingByActivityCreatorIdAndRemovedAndActivityRemoved(1L, false, false)).
//                thenReturn(expectedTrainings);
//        var actualTrainings = trainingService.getAllTrainingByUserId(1L);
//
//        assertEquals(expectedTrainings.size(), actualTrainings.size());
//    }
//
//    @Test
//    void getAllTraining() {
//        when(trainingRepository.findAllByRemovedAndActivityRemoved(false, false)).
//                thenReturn(expectedTrainings);
//        var actualTrainings = trainingService.getAllTraining();
//
//        assertEquals(expectedTrainings.size(), actualTrainings.size());
//    }
//}