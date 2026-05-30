package ru.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.UserShortDto;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "user-service")
public interface UserFeign {
    @GetMapping("/users/{id}")
    UserShortDto findUserShortDtoById(
            @PathVariable("id") Long id
    );

    @PostMapping("/users/map")
    Map<Long, UserShortDto> findUserShortDtoById(@RequestBody Set<Long> usersId);
}
