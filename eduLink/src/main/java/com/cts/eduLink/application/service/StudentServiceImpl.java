package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.AppUserException;
import com.cts.eduLink.application.classexception.StudentException;
import com.cts.eduLink.application.dto.StudentRegistrationDto;
import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.entity.Role;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.projection.StudentDetailByIdProjection;
import com.cts.eduLink.application.repository.RoleRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository ;
    private final RoleRepository roleRepository;
    private final IAppUserService iAppUserService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerStudent(StudentRegistrationDto studentRegistrationDto) throws StudentException, AppUserException {
        log.info("Initiating student registration for user: {}", studentRegistrationDto.getUserEmail());
        log.debug("Extracting student and user entities from DTO");
        Student student = DtoMapper.studentDtoSeparator(studentRegistrationDto);
        AppUser appUser = DtoMapper.appUserDtoSeparator(studentRegistrationDto,passwordEncoder);
        log.info("Extraction completed for student and user entities from DTO");
        Optional<Role> role = roleRepository.findRoleByName("STUDENT");
        appUser.setRole(role.get());
        student.setAppUser(appUser);
        log.error("Attempting to register AppUser and save Student entity");
        iAppUserService.registerAppUser(appUser);
        studentRepository.save(student);
        log.info("Successfully registered student. Assigned Student ID: {}", student.getStudentId());
//        return "Thanks for Registration, Your User id "; // return for testing
        return "Thanks for Registration, Your User Id is: "+student.getStudentId(); // return for development
    }

    @Override
    public int studentCourseEnrollCount(Long studentId) throws StudentException{
        log.info("Request received to fetch course enrollment count for Student ID: {}", studentId);
        Optional<Student> student = studentRepository.findStudentById(studentId);
        if(student.isEmpty()){
            log.error("Enrollment count failed: Student ID {} is not registered", studentId);
            throw new StudentException(studentId+" is not registered!", HttpStatus.NOT_FOUND);
        }
        int enrollCount = studentRepository.studentCourseEnrollCount(studentId);
        log.info("Successfully retrieved enrollment count for Student ID: {}. Count: {}", studentId, enrollCount);
        return studentRepository.studentCourseEnrollCount(studentId);
    }

    @Override
    public StudentDetailByIdProjection findStudentDetailsById(Long studentId) throws StudentException{
        log.info("Initiating request to find details for student ID: {}", studentId);
        Optional<Student> student = studentRepository.findStudentById(studentId);
        if(student.isEmpty()){
            log.error("Lookup failed: Student ID {} does not exist.", studentId);
            throw new StudentException("Student is not register into the system with id: "+studentId,HttpStatus.UNAUTHORIZED);
        }
        Optional<StudentDetailByIdProjection> studentDetailByIdProjections = studentRepository.findStudentDetailsById(studentId);
        if(studentDetailByIdProjections.isEmpty()){
            log.warn("Student ID {} found, but no profile details are available.", studentId);
            throw new StudentException("No details available for student id: "+studentId,HttpStatus.NOT_FOUND);
        }
        log.info("Successfully retrieved details for student ID: {}", studentId);
        return studentDetailByIdProjections.get();
    }
}
