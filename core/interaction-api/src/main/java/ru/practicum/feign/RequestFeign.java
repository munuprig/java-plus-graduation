package ru.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.dto.RequestStatus;

import java.util.List;

@FeignClient(name = "request-service")
public interface RequestFeign {
    @GetMapping("/requests/events")
    List<ParticipationRequestDto> findAllByEventIdInAndStatus(
            @RequestParam("eventIds") List<Long> eventsId,
            @RequestParam("status") RequestStatus status
    );

    @GetMapping("/requests")
    List<EventFullDto> findByRequesterIdAndEventId(
            @RequestParam("requesterId") Long requesterId,
            @RequestParam("eventId") Long eventId
    );

    @GetMapping("/requests/event/{eventId}/{status}")
    Long findCountByEventIdInAndStatus(
            @PathVariable("eventId") Long eventId,
            @PathVariable("status") RequestStatus status
    );}
