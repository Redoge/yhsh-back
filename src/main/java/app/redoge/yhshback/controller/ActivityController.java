package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.paths.Constants.ACTIVITIES_PATH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
@RestController
@RequestMapping(ACTIVITIES_PATH)
@AllArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public List<Activity> getAll(@RequestParam(value = "creatorId", required = false) Long userId,
                                 @RequestParam(value = "username", required = false) String username){
        if(isNotEmpty(userId))
            return activityService.getAllByCreatorId(userId);
        if(isNotEmpty(username))
            return activityService.getAllByCreatorUsername(username);
        return activityService.getAll();
    }
    @GetMapping("/{id}")
    public Activity getById(@PathVariable long id) throws NotFoundException {
        return activityService.getById(id);
    }
    @PostMapping
    public Activity create(@RequestBody ActivitySaveRequestDto activitySaveRequestDto) throws BadRequestException {
        return activityService.saveByDto(activitySaveRequestDto);
    }
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable long id) throws NotFoundException {
        return activityService.removeById(id);
    }
}