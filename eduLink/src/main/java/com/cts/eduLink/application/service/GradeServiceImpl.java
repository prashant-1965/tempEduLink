package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.ExamException;
import com.cts.eduLink.application.classexception.StudentException;
import com.cts.eduLink.application.dto.GradeRegistrationDto;
import com.cts.eduLink.application.entity.Exam;
import com.cts.eduLink.application.entity.Grade;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.repository.ExamRepository;
import com.cts.eduLink.application.repository.GradeRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.util.ClassSeparatorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
        Grade grade = ClassSeparatorUtils.gradeDtoSeparator(gradeRegistrationDto);
        grade.setExam(exam.get());
        grade.setStudent(student.get());
        gradeRepository.save(grade);
        log.info("Successfully registered grade for Student: {} in Exam: {}",student.get().getStudentId(), exam.get().getExamName());
        return "Thanks for completing the exam";
    }
}
