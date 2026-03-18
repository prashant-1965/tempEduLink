package com.cts.eduLink.application.serviceTest;

import com.cts.eduLink.application.dto.FacultyRegistrationDto;
import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.repository.FacultyRepository;
import com.cts.eduLink.application.service.FacultyServiceImpl;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@AllArgsConstructor
public class FacilityServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    private final FacultyServiceImpl facultyService;

    private FacultyRegistrationDto facultyRegistrationDto;

    private AppUser appUser;

    @BeforeEach
    public void setUp(){
        facultyRegistrationDto = new FacultyRegistrationDto();
        facultyRegistrationDto.setUserEmail("ankit@gmail.com");
        facultyRegistrationDto.setUserName("Ankit");
        facultyRegistrationDto.setFacultyGender("Male");
    }


    @Test
    public void facultyRegistration_200(){

//        when(facultyRepository.findAllById(11L)).thenReturn(appUser);

//        facultyService.registerFaculty(facultyRegistrationDto);
    }

    @Test
    public void facultyRegistration_409(){

    }
}
