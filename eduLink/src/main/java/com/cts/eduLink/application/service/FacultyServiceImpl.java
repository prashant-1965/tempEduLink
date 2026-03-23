package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.FacultyException;
import com.cts.eduLink.application.dto.FacultyRegistrationDto;
import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.entity.Faculty;
import com.cts.eduLink.application.entity.Role;
import com.cts.eduLink.application.projection.FacultyDetailProjection;
import com.cts.eduLink.application.repository.FacultyRepository;
import com.cts.eduLink.application.repository.RoleRepository;
import com.cts.eduLink.application.util.DtoMapper;
import com.cts.eduLink.application.util.RatingCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.cts.eduLink.application.constants.ErrorConstant.FACULTY_ERROR;

@Service
@AllArgsConstructor
@Slf4j
public class FacultyServiceImpl implements IFacultyService {

    private final FacultyRepository facultyRepository;
    private final AppUserServiceImpl appUserService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    @Override
    public String registerFaculty(FacultyRegistrationDto facultyRegistrationDto) {

        log.debug("AppUser and Faculty separation initiated");
        AppUser appUser = DtoMapper.appUserDtoSeparator(facultyRegistrationDto,passwordEncoder);
        Faculty faculty = DtoMapper.facultyDtoSeparator(facultyRegistrationDto);
        Optional<Role> role = roleRepository.findRoleByName("FACULTY");

        appUser.setRole(role.get());
        log.debug("appUser instance has sent for registration");
        appUserService.registerAppUser(appUser);
        faculty.setAppUser(appUser);
        facultyRepository.save(faculty);
        log.info("faculty entity has saved into database for {}",appUser.getUserEmail());
        return "You have registered SuccessFully, your login id is: "+faculty.getFacultyId();
    }

    @Override
    public List<FacultyDetailProjection> filterFacultyByRating(int facultyRating) throws FacultyException {
        log.info("Faculty rating filtration request has sent to database");
        List<FacultyDetailProjection> facultyDetailProjections = facultyRepository.filterFacultyByRating(facultyRating);
        if (facultyDetailProjections.isEmpty()){
            log.error("No faculty available with {} ratting",facultyRating);
            throw new FacultyException(FACULTY_ERROR+facultyRating, HttpStatus.NOT_FOUND);
        }
        log.info("Faculty with rating {} fetch successfully and first faculty name is {}",facultyRating,facultyDetailProjections.getFirst().getFacultyName());
        return facultyDetailProjections;
    }

    @Override
    public String updateFacultyRating(Long facultyId, double newFacultyRating) throws FacultyException {
        log.info("Updating rating for Faculty ID: {} with new score: {}", facultyId, newFacultyRating);
        Optional<Faculty> faculty = facultyRepository.findFacultyById(facultyId);
        if(faculty.isEmpty()){
            log.error("Faculty with ID {} not found", facultyId);
            throw new FacultyException("Faculty is not registered",HttpStatus.NOT_FOUND);
        }
        Long totalFacultyRating = faculty.get().getTotalFacultyRatingCount();
        double newRating = RatingCalculator.calculateRating(faculty.get().getFacultyRating(),newFacultyRating,totalFacultyRating);
        faculty.get().setFacultyRating(newRating);
        faculty.get().setTotalFacultyRatingCount(totalFacultyRating+1);
        log.info("Update successful for Faculty ID: {}. Rating changed to {} (Total reviews: {})",facultyId, newRating, totalFacultyRating + 1);
        return "Thanks for you feedBack!";
    }
}
