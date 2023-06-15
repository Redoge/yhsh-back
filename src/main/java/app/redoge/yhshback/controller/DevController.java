package app.redoge.yhshback.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static app.redoge.yhshback.utill.paths.Constants.DEV_PATH;

@RestController
@RequestMapping(DEV_PATH)
public class DevController {
    @GetMapping
    public DevResponse dev() {
        return DevResponse
                .builder()
                .name("YHSH-back")
                .version("0.0.1-dev")
                .paths(List.of("/"))
                .build();
    }

    @Builder
    @Data
    public static class DevResponse{
        private String name;
        private String version;
        private List<String> paths;
    }
}
