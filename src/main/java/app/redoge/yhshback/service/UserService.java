package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.pojo.UserUpdateRequestPojo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.redoge.yhshback.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public User findUserByUserName(String userName) {
        if(isNotEmpty(userName)) {
            LOGGER.debug("Found user by username " + userName);
            return userRepository.findByUsername(userName);
        }else{
            LOGGER.error("User not found by username. Null or len = 0");
            return null;
        }
    }

    public void saveUser(User user) {
        if(isNotEmpty(user)) {
            save(user, UserRole.USER);
            LOGGER.info("User saved " + user.getUsername());
        }else{
            LOGGER.error("User not saved");
        }
    }
    public void saveAdmin(User user) {
        if(isNotEmpty(user)) {
            save(user, UserRole.ADMIN);
            LOGGER.info("Admin saved " + user.getUsername());
        }else{
            LOGGER.error("Admin not saved");
        }
    }
    public void save(User user, UserRole userRole) {
        if(isNotEmpty(user.getUsername()) && isNotEmpty(user.getEmail()) && isNotEmpty(user.getPassword()) &&
                !userExistsByEmail(user.getEmail()) && !userExistsByUsername(user.getUsername())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setUserRole(userRole);
            userRepository.save(user);
            LOGGER.info("User  saved successfully: " + user.getUsername());
        }else{
            LOGGER.error("User not saved");
        }
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

    public boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
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
}