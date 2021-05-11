package com.exercise.studentmanageexercise;

import com.exercise.studentmanageexercise.exception.EmailExistedException;
import com.exercise.studentmanageexercise.model.Student;
import com.exercise.studentmanageexercise.repository.StudentRepository;
import com.exercise.studentmanageexercise.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Assertions.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(MockitoExtension.class)
public class TestStudentService {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

//    @Test
//    void shouldSavedStudentSuccessfully() throws Exception {
//        final Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
//                LocalDate.of(1999,4,4), LocalDateTime.now(),
//                "null", LocalDateTime.now(), "null");
//
//        Mockito.when(studentService.save(student)).thenReturn(student);
//
//        String url = "api/student/" + student.getId().toString();
//
//        mockMvc.perform(post(url).contentType("application/json")
//                .content(objectMapper.writeValueAsString(student)).with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(content().string(""));
//    }

    @Test
    void shouldUpdateStudentSuccessfully() {
        final Student student = new Student(1,"Tan", "tannd1904@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentRepository.findStudentByEmail(student.getEmail()))
                .willReturn(Optional.empty());

        Student savedStudent = studentService.createStudent(student);

        Assertions.assertThat(savedStudent).isNotNull();

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void shouldThrowErrorWhenSaveStudentWithExistingEmail() {
        final Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentRepository.findStudentByEmail(student.getEmail()))
                .willReturn(Optional.empty());

        assertThrows(EmailExistedException.class, () -> {
            studentService.createStudent(student);
        });

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void updateStudent() {
        final Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentRepository.save(student)).willReturn(student);

        final Student expected = studentService.updateStudent(student);

        Assertions.assertThat(expected).isNotNull();

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void shouldReturnFindAll() {
        List<Student> datas = new ArrayList<>();
        datas.add(new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null"));
        datas.add(new Student(2,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null"));
        datas.add(new Student(3,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null"));

        BDDMockito.given(studentRepository.findAll()).willReturn(datas);

        List<Student> expected = studentService.findAllStudents();

        assertEquals(expected, datas);
    }

    @Test
    void findStudentById() {
        final int id = 1;
        final Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentRepository.findById(id)).willReturn(Optional.of(student));

        final Optional<Student> expected = studentService.findById(id);

        Assertions.assertThat(expected).isNotNull();
    }

    @Test
    void shouldBeDelete() {
        final int id = 1;

        studentService.deleteById(id);
        studentService.deleteById(id);

        verify(studentRepository, times(2)).deleteById(id);
    }

}
