package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.LearningMaterial;
import com.cts.eduLink.application.projection.LearningCourseMaterialProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial,Long> {

    @Query("select count(l)>0 from LearningMaterial l where l.course.courseId = :courseId ")
    boolean checkExistingLearningMaterial(@Param("courseId") Long courseId);
    @Query("select new com.cts.eduLink.application.projection.LearningCourseMaterialProjection(lm.id,lm.learningMaterialTitle,lm.learningMaterialFile,lm.course.id) " +
            "from LearningMaterial lm where lm.course.courseId = :courseId")
    Optional<LearningCourseMaterialProjection> findMaterialsByCourseId(@Param("courseId") Long courseId);
}
