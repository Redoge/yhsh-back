//package app.redoge.yhshback.service;
//
//import app.redoge.yhshback.entity.User;
//import app.redoge.yhshback.entity.enums.UserRole;
//import app.redoge.yhshback.exception.UserNotFoundException;
//import app.redoge.yhshback.repository.UserRepository;
//import app.redoge.yhshback.testUtill.GenerateEntityUtil;
//import app.redoge.yhshback.utill.validators.DtoValidators;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private DtoValidators dtoValidators;
//    @InjectMocks
//    private UserService userService;
//    private List<User> expectedUsers;
//
//    @BeforeEach
//    void setUp() {
//        expectedUsers = GenerateEntityUtil.getInstance().generateUser();
//    }
//
//    @Test
//    void findUserByUsername() throws UserNotFoundException {
//        var expectedUser = expectedUsers.get(0);
//        when(userRepository.findByUsername(expectedUser.getUsername())).thenReturn(Optional.of(expectedUser));
//        var actualUser = userService.findUserByUsername(expectedUser.getUsername());
//
//        assertEquals(expectedUser, actualUser);
//    }
//    @Test
//    void findUserByUsernameIncorrect()  {
//        var username = "Username";
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> userService.findUserByUsername(username));
//    }
//
//    @Test
//    void getAllUsers() {
//        when(userRepository.findAll()).thenReturn(expectedUsers);
//
//        var actualUsers = userService.getAllUsers();
//        assertEquals(expectedUsers, actualUsers);
//    }
//
//    @Test
//    void getAllUsersByUserRole() {
//        when(userRepository.findByUserRole(UserRole.USER)).thenReturn(expectedUsers);
//        var actualUsers = userService.getAllUsersByUserRole(UserRole.USER);
//
//        assertEquals(expectedUsers, actualUsers);
//    }
//    @Test
//    void getAllUsersByUserRoleIncorrect() {
//        var actualUsersIncorrect = userService.getAllUsersByUserRole(null);
//
//        assertEquals(new ArrayList<>(), actualUsersIncorrect);
//    }
//    @Test
//    void changeUserRoleByUserId() {
//        var user = expectedUsers.get(0);
//        when(userRepository.save(any())).thenReturn(null);
//        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
//
//        assertDoesNotThrow(() -> userService.changeUserRoleByUserId(1L));
//    }
//
//    @Test
//    void changeEnabledByUserId() {
//        var user = expectedUsers.get(0);
//        when(userRepository.save(any())).thenReturn(null);
//        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
//
//        assertDoesNotThrow(() -> userService.changeEnabledByUserId(1L));
//    }
//
//    @Test
//    void getUserById() throws UserNotFoundException {
//        var expectedUser = expectedUsers.get(0);
//        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedUser));
//
//        var actualUser = userService.getUserById(1L);
//        assertEquals(expectedUser, actualUser);
//    }
//    @Test
//    void getUserByIdIncorrect() {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
//    }
//    @Test
//    void loadUserByUsername() {
//        var expectedUser = expectedUsers.get(0);
//        var username = "Username1";
//        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(expectedUser));
//
//        var actualUser = userService.loadUserByUsername(username);
//        assertEquals(expectedUser, actualUser);
//    }
//    @Test
//    void loadUserByUsernameIncorrect() {
//        var username = "Username1";
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
//    }
//    @Test
//    void getUserByUsername() {
//        var expectedUser = expectedUsers.get(0);
//        var username = "Username1";
//        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(expectedUser));
//
//        var actualUser = userService.getUserByUsername(username);
//        assertEquals(expectedUser, actualUser);
//    }
//    @Test
//    void getUserByUsernameIncorrect() {
//        var username = "Username1";
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUsername(username));
//    }
//    @Test
//    void updateUserByUserUpdateRequest() {
//    }
//
//    @Test
//    void getAllUsersByEnabled() {
//        when(userRepository.findAllByEnabled(true)).thenReturn(expectedUsers);
//        var actualUsers = userService.getAllUsersByEnabled(true);
//
//        assertEquals(expectedUsers, actualUsers);
//    }
//
//    @Test
//    void getUsersByFilter() {
//    }
//}