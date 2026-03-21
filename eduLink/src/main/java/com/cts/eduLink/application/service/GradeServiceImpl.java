package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.CourseException;
import com.cts.eduLink.application.classexception.ExamException;
import com.cts.eduLink.application.classexception.GradeException;
import com.cts.eduLink.application.classexception.StudentException;
import com.cts.eduLink.application.dto.GradeRegistrationDto;
import com.cts.eduLink.application.entity.Exam;
import com.cts.eduLink.application.entity.Grade;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.repository.ExamRepository;
import com.cts.eduLink.application.repository.GradeRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.util.DtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class GradeServiceImpl implements IGradeService{
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    @Override
    public String registerGrade(GradeRegistrationDto gradeRegistrationDto) throws StudentException, ExamException {
        log.info("Attempting to register grade for Student ID: {} and Exam ID: {}",gradeRegistrationDto.getStudentId(), gradeRegistrationDto.getExamId());
        Optional<Student> student = studentRepository.findStudentById(gradeRegistrationDto.getStudentId());
        if(student.isEmpty()){
            log.error("Grade registration failed: Student ID {} not found", gradeRegistrationDto.getStudentId());
            throw new StudentException(gradeRegistrationDto.getStudentId()+" is not available in database",HttpStatus.NOT_FOUND);
        }
        Optional<Exam> exam = examRepository.findExamById(gradeRegistrationDto.getExamId());
        if(exam.isEmpty()){
            log.error("Grade registration failed: Exam ID {} not found", gradeRegistrationDto.getExamId());
            throw new ExamException("No exam available with id: "+gradeRegistrationDto.getExamId(),HttpStatus.NOT_FOUND);
        }
        Grade grade = DtoMapper.gradeDtoSeparator(gradeRegistrationDto);
        grade.setExam(exam.get());
        grade.setStudent(student.get());
        gradeRepository.save(grade);
        log.info("Successfully registered grade for Student: {} in Exam: {}",student.get().getStudentId(), exam.get().getExamName());
        return "Thanks for completing the exam";
    }

    @Override
    public String findGradeStatus(Long gradeId) throws GradeException {
        log.info("Fetching grade status for ID: {}", gradeId);
        Optional<Grade> grade = gradeRepository.findGradeById(gradeId);
        if(grade.isEmpty()){
            log.error("Grade lookup failed: No assignment found with ID {}", gradeId);
            throw new GradeException("NO assignment available with id: "+gradeId,HttpStatus.NOT_FOUND);
        }
        String status = gradeRepository.findGradeStatus(gradeId);
        log.debug("Successfully retrieved status '{}' for grade ID: {}", status, gradeId);
        return status;
    }

    @Override
    public double findTotalGradeByStudentId(Long studentId) {
        log.info("Calculating total grade for student ID: {}", studentId);
        Optional<Grade> grade = gradeRepository.checkStudentAvailableInGrade(studentId);
        if(grade.isEmpty()){
            log.warn("Grade calculation aborted: Student ID {} has no recorded tests.", studentId);
            throw new CourseException(studentId+" is not given any test yet!",HttpStatus.NOT_FOUND);
        }
        double totalGrade = gradeRepository.findGradeByStudentId(studentId);
        log.info("Total grade for student ID {}: {}", studentId, totalGrade);
        return totalGrade;
    }
}
