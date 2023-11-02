package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.UserUpdateRequestDto;

import app.redoge.yhshback.service.interfaces.IUserService;
import app.redoge.yhshback.utill.enums.UserFilterParam;
import app.redoge.yhshback.utill.mappers.EntityMapper;
import app.redoge.yhshback.utill.validators.DtoValidators;
import app.redoge.yhshback.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements  IUserService {
    private final UserRepository userRepository;
    private final DtoValidators dtoValidators;
    private final UserWeightService userWeightService;
    private final EntityMapper entityMapper;
    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    }
    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Email", email));
    }
    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<User> getAllUsersByUserRole(UserRole userRole){
        if(isNotEmpty(userRole)){
            return userRepository.findByUserRole(userRole);
        }else{
            return new ArrayList<>();
        }
    }
    @PreAuthorize("hasAuthority('ADMIN') and !(@userService.getUserById(#userId).username.equalsIgnoreCase(authentication.name))")
    @Override
    public void changeUserRoleByUserId(Long userId) throws BadRequestException, UserNotFoundException {
        if (isNull(userId))
            throw new BadRequestException("User id is null!!!");
        var user = getUserById(userId);
        var role = user.getUserRole();
        if(role.equals(UserRole.ADMIN)){
            user.setUserRole(UserRole.USER);
        }else if(role.equals(UserRole.USER)){
            user.setUserRole(UserRole.ADMIN);
        }
        userRepository.save(user);
    }
    @PreAuthorize("hasAuthority('ADMIN') and !(@userService.getUserById(#userId).username.equalsIgnoreCase(authentication.name))")
    @Override
    public void changeEnabledByUserId(Long userId) throws BadRequestException, UserNotFoundException {
        if (isNull(userId))
            throw new BadRequestException("User id is null!!!");
        var user = getUserById(userId);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }
    @PostAuthorize("returnObject.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public User getUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return getUserByUsername(username);
    }
    @PostAuthorize("#username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

    @Transactional
    @PreAuthorize("#userUpdateRequest.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public User updateUserByUserUpdateRequest(UserUpdateRequestDto userUpdateRequest) throws  BadRequestException {
        if(!dtoValidators.userUpdateRequestDtoIsValid(userUpdateRequest))
            throw new BadRequestException(String.format("User with username %s not updated!!!", userUpdateRequest.username()));
        var user = getUserByUsername(userUpdateRequest.username());
        var weight = entityMapper.mapFloatToUserWeight(userUpdateRequest.weightKg());
        if(!userWeightService.isWeightExist(user.getWeightList(), weight)){
            userWeightService.saveUserWeight(weight);
            user.addWeight(weight);
        }
        user.setHeight(userUpdateRequest.heightSm());
        user.setSex(userUpdateRequest.sex());

        return userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<User> getAllUsersByEnabled(boolean filter) {
        return userRepository.findAllByEnabled(filter);
    }

    /**
    * @param param  may be (ROLE or ENABLE)
    * @param value  if ROLE - ADMIN or USER, if ENABLE - true/false
    */
    @Override
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
        return isNotEmpty(users) ? users : getAllUsers();
    }
    @Override
    public boolean confirmEmail(User user) {
        user.setEmailConfirmed(true);
        save(user);
        return true;
    }

    private User save(User user){
        return userRepository.save(user);
    }

}