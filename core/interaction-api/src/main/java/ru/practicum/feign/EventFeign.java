package ru.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.EventFullDto;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "event-service")
public interface EventFeign {
    @GetMapping("/admin/events/check/category")
    List<EventFullDto> adminGetAllEventsByCategory(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size);

    @GetMapping("/users/{userId}/events/optional/{eventId}")
    Optional<EventFullDto> findOptEventByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                     @PathVariable(name = "eventId") Long eventId);

    @GetMapping("/admin/events/{eventId}")
    EventFullDto findEventById(@PathVariable(name = "eventId") Long eventId);
}
