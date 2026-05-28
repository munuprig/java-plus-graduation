package ru.practicum.ewm.event.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Location {

    private Double lat;

    private Double lon;
}
