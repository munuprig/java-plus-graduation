package ru.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.dto.RequestStatus;

import java.util.List;
import java.util.Map;

@FeignClient(name = "request-service")
public interface RequestFeign {
    @PostMapping("/requests/count")
    Map<Long, Long> countRequest(@RequestBody List<Long> eventsIds);

    @GetMapping("requests/events/{eventId}/{status}")
    List<ParticipationRequestDto> findAllByEventIdInAndStatus(@PathVariable(name = "eventId") List<Long> eventsId,
                                                              @PathVariable RequestStatus status);

    @GetMapping("/requests")
    List<EventFullDto> findByRequesterIdAndEventId(@RequestBody Long requesterId, @RequestBody Long eventId);

    @GetMapping("requests/event")
    Long findCountByEventIdInAndStatus(@PathVariable Long eventId, @PathVariable RequestStatus status);
}
