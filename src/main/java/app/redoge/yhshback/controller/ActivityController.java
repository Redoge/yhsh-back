package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.dto.ActivityUpdateDto;
import app.redoge.yhshback.dto.Page;
import app.redoge.yhshback.dto.response.ActivityDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.service.interfaces.IActivityService;
import app.redoge.yhshback.utill.mappers.PageMapper;
import app.redoge.yhshback.utill.mappers.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.ACTIVITIES_PATH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
@RestController
@RequestMapping(ACTIVITIES_PATH)
@AllArgsConstructor
public class ActivityController {
    private final IActivityService activityService;
    private final ResponseDtoMapper responseDtoMapper;
    private final PageMapper<ActivityDto> pageMapper;

    @GetMapping
    public Page<ActivityDto> getAll(@RequestParam(value = "creatorId", required = false) Long userId,
                                    @RequestParam(value = "username", required = false) String username,
                                    Pageable pageable) {
        List<Activity> activity;
        if (isNotEmpty(userId)) {
            activity = activityService.getAllByCreatorId(userId);
        } else if (isNotEmpty(username)) {
            activity = activityService.getAllByCreatorUsername(username);
        } else {
            activity = activityService.getAll();
        }
        var dto = activity.stream().map(responseDtoMapper::mapActivityToActivityDto).toList();
        return pageMapper.mapToPage(dto, pageable);
    }

    @GetMapping("/{id}")
    public ActivityDto getById(@PathVariable long id) throws NotFoundException {
        var activity = activityService.getById(id);
        return responseDtoMapper.mapActivityToActivityDto(activity);
    }
    @PostMapping
    public ActivityDto create(@RequestBody ActivitySaveRequestDto activitySaveRequestDto) throws BadRequestException, UserNotFoundException, NotFoundException {
        var activity = activityService.saveByDto(activitySaveRequestDto);
        return responseDtoMapper.mapActivityToActivityDto(activity);

    }
    @PostMapping("/{id}")
    public ActivityDto update(@RequestBody ActivityUpdateDto activityRequestDto, @PathVariable long id) throws NotFoundException, BadRequestException {
        var activity = activityService.updateByDto(id, activityRequestDto);
        return responseDtoMapper.mapActivityToActivityDto(activity);

    }
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable long id) throws NotFoundException, BadRequestException {
        return activityService.removeById(id);
    }
}