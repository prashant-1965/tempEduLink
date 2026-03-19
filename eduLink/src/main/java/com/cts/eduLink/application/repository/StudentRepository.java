package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.projection.StudentDetailByIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("select s.appUser from Student s where s.studentId = :studentId")
    Optional<AppUser> findAppUserByStudentId(@Param("studentId") Long studentId);

    @Query("select s from Student s where s.studentId = :studentId")
    Optional<Student> findStudentById(@Param("studentId") Long studentId);

    @Query("select size(s.courseSet) from Student s where s.studentId = :studentId")
    int studentCourseEnrollCount(@Param("studentId") Long studentId);

    @Query("select new com.cts.eduLink.application.projection.StudentDetailByIdProjection(s.studentId,a.userName,a.userEmail,a.phoneNumber,s.studentDOB,s.studentGender,s.studentAddress,s.studentEnrollmentDateTime)"+
            " from Student s "+
            " inner join s.appUser a where s.studentId = :studentId")
    Optional<StudentDetailByIdProjection> findStudentDetailsById(@Param("studentId") Long studentId);

    @Query("select s from Student s inner join s.courseSet c where c.courseId = :courseId and s.studentId = :studentId")
    Optional<Student> checkStudentRegisteredcourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
