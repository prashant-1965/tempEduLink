package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.projection.CourseDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("select s.appUser from Student s where s.studentId = :studentId")
    Optional<AppUser> findAppUserByStudentId(@Param("studentId") Long studentId);

    @Query("select s from Student s where s.studentId = :studentId")
    Optional<Student> findStudentById(@Param("studentId") Long studentId);

    @Query("select size(s.courseSet) from Student s where s.studentId = :studentId")
    int studentCourseEnrollCount(@Param("studentId") Long studentId);

}
