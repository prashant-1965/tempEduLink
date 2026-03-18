package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.AppUserException;
import com.cts.eduLink.application.classexception.FeedbackException;
import com.cts.eduLink.application.dto.FeedbackDto;
import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.entity.Faculty;
import com.cts.eduLink.application.entity.FeedBack;
import com.cts.eduLink.application.entity.Student;
import com.cts.eduLink.application.projection.FeedbackProjection;
import com.cts.eduLink.application.repository.AppUserRepository;
import com.cts.eduLink.application.repository.FacultyRepository;
import com.cts.eduLink.application.repository.FeedBackRepository;
import com.cts.eduLink.application.repository.StudentRepository;
import com.cts.eduLink.application.util.ClassSeparatorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedBackRepository feedBackRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    @Override
    @Transactional
    public String registerFeedback(FeedbackDto feedbackDto) throws AppUserException, FeedbackException {
        log.info("Attempting to register feedback for User ID: {} with Reviewer Type: {}",
                feedbackDto.getUserId(), feedbackDto.getReviewerType());
        Optional<AppUser> appUser = Optional.empty();
        if(feedbackDto.getReviewerType().equalsIgnoreCase("STUDENT")){
            log.debug("Fetching AppUser from student repository for ID: {}", feedbackDto.getUserId());
            appUser = studentRepository.findAppUserByStudentId(feedbackDto.getUserId());
        } else if(feedbackDto.getReviewerType().equalsIgnoreCase("FACULTY")){
            log.debug("Fetching AppUser from faculty repository for ID: {}", feedbackDto.getUserId());
            appUser = facultyRepository.findAppUserByFacultyId(feedbackDto.getUserId());
        }
        if (appUser.isEmpty() || appUser.isEmpty()){
            log.error("Feedback registration failed: No AppUser found for ID: {} and Type: {}",
                    feedbackDto.getUserId(), feedbackDto.getReviewerType());
            throw new AppUserException("Invalid feedback type", HttpStatus.BAD_REQUEST);
        }
        FeedBack feedBack = ClassSeparatorUtils.feedBackDtoSeparator(feedbackDto);
        feedBack.setAppUser(appUser.get());
        feedBackRepository.save(feedBack);
        log.info("Feedback successfully saved for User ID: {}", feedbackDto.getUserId());
        return "Thank you for your feedback!";
    }

    @Override
    public List<FeedbackProjection> findFeedBackList() throws FeedbackException {
        log.info("Fetching all feedback records from the repository");
        List<FeedbackProjection> feedbackProjections = feedBackRepository.findFeedBackList();
        if(feedbackProjections.isEmpty()){
            log.warn("No feedback records found in the database");
            throw new FeedbackException("No feedback yet for the platform",HttpStatus.NOT_FOUND);
        }
        log.info("Successfully retrieved {} feedback records", feedbackProjections.size());
        return feedbackProjections;
    }
}
