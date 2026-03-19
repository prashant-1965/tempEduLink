package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade,Long> {
}
