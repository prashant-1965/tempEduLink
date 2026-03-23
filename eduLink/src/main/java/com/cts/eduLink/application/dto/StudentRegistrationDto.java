package com.cts.eduLink.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class StudentRegistrationDto {

    @NotBlank(message = "Name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String userEmail;

    @NotNull(message = "Phone number is required")
    private Long phoneNumber;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate studentDOB;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other|Prefer not to say)$")
    private String studentGender;

    @NotBlank(message = "Address is required")
    private String studentAddress;

    private String password;
}
