package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.ExamRegistrationDto;

public interface IExamService {
    String registerExam(ExamRegistrationDto examRegistrationDto);
}
