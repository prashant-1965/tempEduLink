package com.cts.eduLink.application.serviceTest;

import com.cts.eduLink.application.classexception.CourseException;
import com.cts.eduLink.application.dto.CourseRegistrationDto;
import com.cts.eduLink.application.entity.Course;
import com.cts.eduLink.application.entity.Faculty;
import com.cts.eduLink.application.projection.CourseDetailByIdProjection;
import com.cts.eduLink.application.projection.CourseDetailProjection;
import com.cts.eduLink.application.repository.CourseRepository;
import com.cts.eduLink.application.repository.FacultyRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.service.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private StudentRepository studentRepository;

    private  CourseServiceImpl courseService;
    private  List<CourseDetailProjection> courseDetailProjectionList;
    private Optional<CourseDetailByIdProjection> courseDetailByIdProjection;
    private CourseRegistrationDto courseRegistrationDto;


    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseRepository,facultyRepository,studentRepository);
        courseDetailProjectionList = new ArrayList<>();
        courseDetailProjectionList.add(new CourseDetailProjection("java springBoot" , "K",  4.8));
        courseDetailByIdProjection = Optional.of(new CourseDetailByIdProjection(1234L,"java springBoot","Full stack","12",4,"Active",4.6,"Monit",3.5));
        courseRegistrationDto = new CourseRegistrationDto();
        courseRegistrationDto.setCourseTitle("JAVA"); // only to ensure, faculty is not empty
    }

    @Test
    public void registerCourse_200(){
        Optional<Faculty> faculty = Optional.of(new Faculty());
        faculty.get().setFacultyRating(1.3);
        when(facultyRepository.findFacultyById(1234L)).thenReturn(faculty);
//        doNothing().when(courseRepository).save(any(Course.class));

        String message = courseService.registerCourse(courseRegistrationDto);
        assertEquals("Course has registered successFully",message);

        verify(facultyRepository,times(1)).findFacultyById(1234L);
        verify(courseRepository,times(1)).save(any(Course.class));
    }

    @Test
    public void courseAvailableTest_200(){
        when(courseRepository.findAllAvailableCourse()).thenReturn(courseDetailProjectionList);
        List<CourseDetailProjection> courseDetailProjections = courseService.findAllAvailableCourse();
        assertEquals(!courseDetailProjections.isEmpty(), courseDetailProjections.size()==1,"Tested Successfully");
    }

    @Test
    public void courseAvailableTest_404(){
        courseDetailProjectionList = new ArrayList<>();
        CourseException exception = assertThrows(CourseException.class,()->{
            courseService.findAllAvailableCourse();
        });
        verify(courseRepository).findAllAvailableCourse();
        assertEquals("No course Available!",exception.getMessage());
    }

    @Test
    public void findCourseDetailsById_200(){
        when(courseRepository.findCourseDetailsById(12345L)).thenReturn(courseDetailByIdProjection);
        CourseDetailByIdProjection courseDetailByIdProjectionList = courseService.findCourseDetailsById(12345L);
        assertEquals(4, courseDetailByIdProjectionList.getCourseCredit());
        verify(courseRepository,times(1)).findCourseDetailsById(12345L);
    }

    @Test
    public void findCourseDetailsById_404(){
        when(courseRepository.findCourseDetailsById(12345L)).thenReturn(Optional.empty());
        CourseException exception = assertThrows(CourseException.class,()->{
            courseService.findCourseDetailsById(12345L);
        });
        assertEquals("No Course available for course id: 12345",exception.getMessage());
        verify(courseRepository,times(1)).findCourseDetailsById(12345L);
    }
}
