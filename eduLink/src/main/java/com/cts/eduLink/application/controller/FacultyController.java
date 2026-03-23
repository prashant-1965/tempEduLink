package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.FacultyRegistrationDto;
import com.cts.eduLink.application.projection.FacultyDetailProjection;
import com.cts.eduLink.application.service.IFacultyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@AllArgsConstructor
@Slf4j
public class FacultyController {

    private final IFacultyService facultyService;

    @PostMapping("/register")
    public ResponseEntity<String> registerFaculty(@Valid @RequestBody FacultyRegistrationDto facultyRegistrationDto){
        log.info("{} has initiated the registration as a Faculty",facultyRegistrationDto.getUserEmail());
        return ResponseEntity.status(200).body(facultyService.registerFaculty(facultyRegistrationDto));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/filterByRatting/{facultyRating}")
    public ResponseEntity<List<FacultyDetailProjection>> filterFacultyByRating(@PathVariable int facultyRating){
        log.info("Faculty filter request by ratting intercepted");
        return ResponseEntity.status(200).body(facultyService.filterFacultyByRating(facultyRating));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/updateRating/{facultyId}/{newFacultyRating}")
    public ResponseEntity<String> updateFacultyRating(@PathVariable Long facultyId, @PathVariable double newFacultyRating){
        return ResponseEntity.status(200).body(facultyService.updateFacultyRating(facultyId,newFacultyRating));
    }

}
