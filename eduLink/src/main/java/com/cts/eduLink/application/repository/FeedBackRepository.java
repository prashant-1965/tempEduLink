package com.cts.eduLink.application.repository;

import com.cts.eduLink.application.entity.FeedBack;
import com.cts.eduLink.application.projection.FeedbackProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    @Query("select new com.cts.eduLink.application.projection.FeedbackProjection(a.userName,f.message,f.rating) from FeedBack f inner join f.appUser a")
    List<FeedbackProjection> findFeedBackList();
}
