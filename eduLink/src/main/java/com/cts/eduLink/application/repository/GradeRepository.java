package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade,Long> {

    @Query("select g from Grade g where g.gradeId = :gradeId")
    Optional<Grade> findGradeById(@Param("gradeId") Long gradeId);

    @Query("select g from Grade g inner join g.student s where s.studentId  =:studentId")
    Optional<Grade> checkStudentAvailableInGrade(@Param("studentId") Long studentId);

    @Query("select g.status from Grade g where g.gradeId = :gradeId")
    String findGradeStatus(@Param("gradeId") Long gradeId);

    @Query("select sum(g.score) from Grade g inner join g.student s where s.studentId = :studentId")
    double findGradeByStudentId(@Param("studentId") Long studentId);
}
