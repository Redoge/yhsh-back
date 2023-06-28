package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.pojo.UserUpdateRequestPojo;

import app.redoge.yhshback.utill.enums.UserFilterParam;
import app.redoge.yhshback.utill.validators.DtoValidators;
import app.redoge.yhshback.repository.UserRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
public class UserService implements  UserDetailsService {
    private final UserRepository userRepository;
    private final DtoValidators dtoValidators;

    public UserService(UserRepository userRepository, DtoValidators dtoValidators) {
        this.userRepository = userRepository;
        this.dtoValidators = dtoValidators;
    }
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsersByUserRole(UserRole userRole){
        if(isNotEmpty(userRole)){
            return userRepository.findByUserRole(userRole);
        }else{
            return new ArrayList<>();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserRoleByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserRole role = user.getUserRole();
            if(role.equals(UserRole.ADMIN)){
                user.setUserRole(UserRole.USER);
            }else if(role.equals(UserRole.USER)){
                user.setUserRole(UserRole.ADMIN);
            }
            userRepository.save(user);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void changeEnabledByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
        }
    }
    @PostAuthorize("returnObject.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public User getUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }
    @PostAuthorize("#username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

    @Transactional
    @PreAuthorize("#userUpdateRequest.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public User updateUserByUserUpdateRequest(UserUpdateRequestPojo userUpdateRequest) throws  BadRequestException {
        if(!dtoValidators.userUpdateRequestPojoIsValid(userUpdateRequest)){
            throw new BadRequestException(String.format("User with username %s not updated!!!", userUpdateRequest.username()));
        }
        var user = getUserByUsername(userUpdateRequest.username());
        user.setHeightSm(userUpdateRequest.heightSm());
        user.setWeightKg(userUpdateRequest.weightKg());
        user.setSex(userUpdateRequest.sex());
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsersByEnabled(boolean filter) {
        return userRepository.findAllByEnabled(filter);
    }

    public List<User> getUsersByFilter(String param, String value) throws NotFoundException {
        List<User> users = null;
        if(isNotEmpty(param) && isNotEmpty(value)){
            if(param.equalsIgnoreCase(UserFilterParam.ROLE.toString())){
                users = getAllUsersByUserRole(UserRole.findByName(value));
            } else if (param.equalsIgnoreCase(UserFilterParam.ENABLED.toString())) {
                if(value.equalsIgnoreCase("true")){
                    users = getAllUsersByEnabled(true);
                } else if(value.equalsIgnoreCase("false")){
                    users = getAllUsersByEnabled(false);
                }
            }
        }
        return !isNotEmpty(users) ? users : getAllUsers();
    }
}