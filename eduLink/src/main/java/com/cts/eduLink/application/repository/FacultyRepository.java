package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.entity.Faculty;
import com.cts.eduLink.application.projection.FacultyDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    @Query("select f from Faculty f where f.facultyId = :facultyId")
    Optional<Faculty> findFacultyById(@Param("facultyId") Long facultyId);

    @Query("select new com.cts.eduLink.application.projection.FacultyDetailProjection(a.userName, f.facultyRating,f.facultyYearOfExperience)"+
            " from Faculty f inner join f.appUser a where f.facultyRating>= :facultyRating and f.facultyRating<:facultyRating+1")
    List<FacultyDetailProjection> filterFacultyByRating(@Param("facultyRating") int facultyRating);

    @Query("select f.appUser from Faculty f where f.facultyId = :facultyId")
    Optional<AppUser> findAppUserByFacultyId(@Param("facultyId") Long facultyId);
}
