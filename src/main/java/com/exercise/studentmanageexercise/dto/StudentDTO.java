package com.exercise.studentmanageexercise.dto;

import com.exercise.studentmanageexercise.validation.AgeCheck;
import com.exercise.studentmanageexercise.validation.EmailCheck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class StudentDTO {
    @NotBlank(message = "Name is required")
    @NotNull
    private String name;


    @NotBlank(message = "Email is required")
    @NotNull
    @EmailCheck(groups = {Default.class})
    private String email;

    @NotBlank(message = "Address is required")
    @NotNull
    private String address;

    @NotNull
    @AgeCheck
    private LocalDate birthday;
}