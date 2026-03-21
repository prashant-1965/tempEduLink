package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.CourseException;
import com.cts.eduLink.application.classexception.ExamException;
import com.cts.eduLink.application.dto.ExamRegistrationDto;
import com.cts.eduLink.application.entity.Course;
import com.cts.eduLink.application.entity.Exam;
import com.cts.eduLink.application.repository.CourseRepository;
import com.cts.eduLink.application.repository.ExamRepository;
import com.cts.eduLink.application.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ExamServiceImpl implements IExamService{

    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;


    @Override
    public String registerExam(ExamRegistrationDto examRegistrationDto) throws ExamException, CourseException {
        log.info("Received request to register exam: {} for course ID: {}",examRegistrationDto.getExamName(), examRegistrationDto.getCourseId());
        Optional<Course> course = courseRepository.findCourseById(examRegistrationDto.getCourseId());
        if(course.isEmpty()){
            log.error("Registration failed: Course ID {} not found", examRegistrationDto.getCourseId());
            throw new CourseException("No course is not register with id "+examRegistrationDto.getCourseId(), HttpStatus.BAD_REQUEST);
        }
        Exam exam = DtoMapper.examDtoSeparator(examRegistrationDto);
        exam.setCourse(course.get());
        examRepository.save(exam);
        log.info("Successfully registered exam with ID: {} for course: {}", exam.getId(), examRegistrationDto.getExamName());
        return "Exam registered successfully with "+examRegistrationDto.getExamName();
    }
}
