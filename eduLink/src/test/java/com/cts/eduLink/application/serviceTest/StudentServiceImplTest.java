package com.cts.eduLink.application.serviceTest;

import com.cts.eduLink.application.dto.StudentRegistrationDto;
import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.entity.Role;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.repository.RoleRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.service.IAppUserService;
import com.cts.eduLink.application.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository ;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private IAppUserService iAppUserService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private StudentServiceImpl studentService;

    private StudentRegistrationDto studentRegistrationDto;

    private Optional<Role> role;

    @BeforeEach
    public void setUp() {
        studentService = new StudentServiceImpl(studentRepository,roleRepository, iAppUserService,passwordEncoder);
        studentRegistrationDto = new StudentRegistrationDto();
        studentRegistrationDto.setUserName("Nihil");
        studentRegistrationDto.setUserEmail("nikhil@eduLink.com");
        studentRegistrationDto.setPhoneNumber(1234567892L);
        studentRegistrationDto.setStudentDOB(LocalDate.now());
        studentRegistrationDto.setStudentGender("MALE");
        studentRegistrationDto.setStudentAddress("Pune, Maharashtra");
        role = Optional.of(new Role());
        role.get().setRoleName("STUDENT");
        Optional<AppUser> appUser = Optional.of(new AppUser());
        appUser.get().setId(1L);
        appUser.get().setUserName(studentRegistrationDto.getUserName());
        appUser.get().setUserEmail(studentRegistrationDto.getUserEmail());
        appUser.get().setPhoneNumber(studentRegistrationDto.getPhoneNumber());
        appUser.get().setRole(role.get());
    }

    @Test
    public void studentRegistration_200(){
        when(roleRepository.findRoleByName("STUDENT")).thenReturn(role);
        doNothing().when(iAppUserService).registerAppUser(any(AppUser.class));
        when(studentRepository.save(any(Student.class))).thenReturn(new Student());
        assertEquals("Thanks for Registration, Your User Id ",studentService.registerStudent(studentRegistrationDto));
        verify(roleRepository,times(1)).findRoleByName("STUDENT");
        verify(iAppUserService,times(1)).registerAppUser(any(AppUser.class));
        verify(studentRepository,times(1)).save(any(Student.class));
    }
}
