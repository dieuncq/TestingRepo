package com.exercise.studentmanageexercise;

import com.exercise.studentmanageexercise.model.Student;
import com.exercise.studentmanageexercise.repository.StudentRepository;
import com.exercise.studentmanageexercise.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.BDDMockito;

import java.time.LocalDate;
import java.time.LocalDateTime;


@ExtendWith(MockitoExtension.class)
public class TestStudentService {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldSavedStudentSuccessfully() {
        final Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentRepository.getStudentByEmail(student.getEmail()))
                .willReturn(null);
        BDDMockito.given(studentRepository.save(student)).willAnswer(invocation -> invocation.getArguments());

        Student savedStudent = studentService.save(student);

        Assertions.assertThat(savedStudent).isNotNull();

    }
}
