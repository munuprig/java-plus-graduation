package ru.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.UserShortDto;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "user-service")
public interface UserFeign {
    @GetMapping("/users/{id}")
    UserShortDto findUserShortDtoById(@RequestParam Long id);

    @PostMapping("/users/map")
    Map<Long, UserShortDto> findUserShortDtoById(@RequestBody Set<Long> usersId);
}
