package ru.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "category-service")
public interface CategoryFeign {
    @GetMapping("/categories/{id}")
    CategoryDto getCategoryById(
            @PathVariable("id") Long id
    );

    @PostMapping("/categories/map")
    Map<Long, CategoryDto> getCategoryById(@RequestBody Set<Long> categoriesId);
}
