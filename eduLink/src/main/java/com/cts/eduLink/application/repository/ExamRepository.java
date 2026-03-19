package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {

    @Query("select e from Exam e where e.examId = :examId")
    Optional<Exam> findExamById(@Param("examId") Long examId);
}
