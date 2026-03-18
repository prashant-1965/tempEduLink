package com.cts.eduLink.application.repositoryTest;

import com.cts.eduLink.application.entity.Course;
import com.cts.eduLink.application.projection.CourseDetailProjection;
import com.cts.eduLink.application.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
//@ActiveProfiles("test")
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp(){
        Course course = new Course();
        course.setCourseId(12532434543L);
        course.setCourseTitle("Java Full Stack");
        course.setCourseSubject("Java");
        course.setCourseGradeLevel("Intermediate");
        course.setCourseCredit(4);
        course.setCourseStatus("Active");
        course.setCourseRating(4.5);
        courseRepository.save(course);
    }


    @Test
    public void getCourseList_200(){
        List<CourseDetailProjection> courseDetailProjections = courseRepository.findAllAvailableCourse();
        assertEquals("Java", courseDetailProjections.getFirst().getCourseTitle());
    }
}
