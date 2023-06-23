package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.pojo.UserUpdateRequestPojo;

import app.redoge.yhshback.utill.validators.DtoValidators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.redoge.yhshback.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
public class UserService implements  UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final DtoValidators dtoValidators;

    public UserService(UserRepository userRepository, DtoValidators dtoValidators) {
        this.userRepository = userRepository;
        this.dtoValidators = dtoValidators;
    }

    public User findUserByEmail(String email) {
        if(isNotEmpty(email)) {
            LOGGER.debug("Found user by email " + email);
            return userRepository.findByEmail(email);
        }else{
            LOGGER.error("User not found by email  Null or len = 0");
            return null;
        }
    }

    public User findUserByUsername(String userName) throws UserNotFoundException {
        return userRepository.findByUsername(userName).orElseThrow(()->new UserNotFoundException(userName));
    }



    public void update(User user){
        if(isNotEmpty(user)) {
            userRepository.save(user);
            LOGGER.info("User updated: " + user.getUsername());
        }else{
            LOGGER.error("User not updated");
        }
    }
    public void update(User user, UserUpdateRequestPojo up){
        if(isNotEmpty(user)) {
            if(up.heightSm()>50&&up.heightSm()<300) user.setHeightSm(up.heightSm());
            if(up.weightKg()>10&&up.weightKg()<300) user.setWeightKg(up.weightKg());
            if(isNotEmpty(up.sex())&&(up.sex().equals("Male") || up.sex().equals("Female"))) user.setSex(up.sex());
            userRepository.save(user);
            LOGGER.info("User updated: " + user.getUsername());
        }else{
            LOGGER.error("User not updated");
        }
    }

    public List<User> getAllUsers(){
        LOGGER.debug("Get all users");
        return userRepository.findAll();
    }

    public List<User> getAllUsersByUserRole(UserRole userRole){
        if(isNotEmpty(userRole)){
            LOGGER.debug("Get all users by user role " + userRole);
            return userRepository.findByUserRole(userRole);
        }else{
            LOGGER.error("Error to get all user by user role");
            return new ArrayList<>();
        }
    }



    public void changeUserRoleByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserRole role = user.getUserRole();
            if(role.equals(UserRole.ADMIN)){
                user.setUserRole(UserRole.USER);
                LOGGER.info("Changed user role: admin -> user for " + user.getUsername());
            }else if(role.equals(UserRole.USER)){
                user.setUserRole(UserRole.ADMIN);
                LOGGER.info("Changed user role: user -> admin for " + user.getUsername());
            }
            userRepository.save(user);
        }else{
            LOGGER.error("Error to change user role");
        }
    }

    public void changeEnabledByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
            LOGGER.info("User enabled changed to "+ user.isEnabled() +" for " + user.getUsername());
        }else{
            LOGGER.error("User enabled not changed");
        }
    }

    public User getUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

    @Transactional
    public User updateUserByUserUpdateRequest(UserUpdateRequestPojo userUpdateRequest) throws UserNotFoundException, BadRequestException {
        if(!dtoValidators.userUpdateRequestPojoIsValid(userUpdateRequest)){
            throw new BadRequestException(String.format("User with username %s not updated!!!", userUpdateRequest.username()));
        }
        var user = getUserByUsername(userUpdateRequest.username());
        user.setHeightSm(userUpdateRequest.heightSm());
        user.setWeightKg(userUpdateRequest.weightKg());
        user.setSex(userUpdateRequest.sex());
        return userRepository.save(user);
    }
}