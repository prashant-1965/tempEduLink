package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.FacultyRegistrationDto;
import com.cts.eduLink.application.projection.FacultyDetailProjection;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IFacultyService {
    String registerFaculty(FacultyRegistrationDto facultyRegistrationDto);
    List<FacultyDetailProjection> filterFacultyByRating(@Param("facultyRating") int facultyRating);
    String updateFacultyRating(Long facultyId, double newFacultyRating);
}
