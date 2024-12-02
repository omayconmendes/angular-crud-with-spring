package com.maycon.service;

import com.maycon.dto.CourseDTO;
import com.maycon.dto.CoursePageDTO;
import com.maycon.dto.mapper.CourseMapper;
import com.maycon.exception.RecordNotFoundException;
import com.maycon.model.Course;
import com.maycon.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list(@PositiveOrZero int pageNumber, @Positive @Max(100) int pageSize) {
        Page<Course> pageCourse =  courseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).collect(Collectors.toList());
        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id).map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                   Course course = courseMapper.toEntity(courseDTO);
                   recordFound.setName(courseDTO.name());
                   recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
//                   recordFound.setLessons(course.getLessons());
                   recordFound.getLessons().clear();
                   course.getLessons().forEach(lesson -> recordFound.getLessons().add(lesson));
                   return courseMapper.toDTO(courseRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(
                courseRepository.findById(id)
                        .orElseThrow(() -> new RecordNotFoundException(id)
                        )
        );
    }
}
