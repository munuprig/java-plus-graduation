package ru.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.CategoryDto;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "category-service")
public interface CategoryFeign {
    @GetMapping("/categories/{id}")
    CategoryDto getCategoryById(@RequestParam Long id);

    @PostMapping("/categories/map")
    Map<Long, CategoryDto> getCategoryById(@RequestBody Set<Long> categoriesId);
}
