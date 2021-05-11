package com.exercise.studentmanageexercise;

import com.exercise.studentmanageexercise.controller.StudentController;
import com.exercise.studentmanageexercise.model.Student;
import com.exercise.studentmanageexercise.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;
import org.hamcrest.CoreMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
@ActiveProfiles("test")
public class TestStudentController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Student> studentList;

    @BeforeEach
    void setUp() {
        this.studentList = new ArrayList<>();
        this.studentList.add(new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null"));
        this.studentList.add(new Student(2,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null"));
        this.studentList.add(new Student(3,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null"));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @Test
    void shouldFetchAllUsers() throws Exception {
        BDDMockito.given(studentService.findAllStudents())
                .willReturn(studentList);

        this.mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(studentList.size())));
    }

    @Test
    void shouldFetchOneStudentById() throws Exception {
        final int id = 1;
        final Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentService.findById(id)).willReturn(Optional.of(student));

        this.mockMvc.perform(get("/api/student/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", CoreMatchers.is(student.getEmail())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(student.getName())));
    }

    @Test
    void shouldReturn404WhenFindStudentById() throws Exception {
        final int id = 1;

        BDDMockito.given(studentService.findById(id))
                .willReturn(Optional.empty());

        this.mockMvc.perform(get("/api/student/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewStudent() throws Exception {
        BDDMockito.given(studentService.createStudent(any(Student.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        this.mockMvc.perform(post("/api/student/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email",CoreMatchers.is(student.getEmail())))
                .andExpect(jsonPath("$.name",CoreMatchers.is(student.getName())))
                .andExpect(jsonPath("$.address",CoreMatchers.is(student.getAddress())));
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        int id = 1;
        Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentService.findById(id)).willReturn(Optional.of(student));

        BDDMockito.given(studentService.updateStudent(any(Student.class))).willAnswer(
                invocation -> invocation.getArgument(0));

        this.mockMvc.perform(put("api/student/{id}", student.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email",CoreMatchers.is(student.getEmail())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(student.getName())))
                .andExpect(jsonPath("$.address", CoreMatchers.is(student.getAddress())));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingStudent() throws Exception {
        int id = 1;
        BDDMockito.given(studentService.findById(id)).willReturn(Optional.empty());

        Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        this.mockMvc.perform(put("api/student/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shoulDeleteStudent() throws Exception {
        int id = 1;
        Student student = new Student(1,"Tan", "tannd@gmail", "Ben Tre",
                LocalDate.of(1999,4,4), LocalDateTime.now(),
                "null", LocalDateTime.now(), "null");

        BDDMockito.given(studentService.findById(id)).willReturn(Optional.of(student));
        doNothing().when(studentService).deleteById(student.getId());

        this.mockMvc.perform(delete("api/student/{id}", student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", CoreMatchers.is(student.getEmail())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(student.getName())))
                .andExpect(jsonPath("$.address", CoreMatchers.is(student.getAddress())));
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingStudent() throws Exception {
        int id = 1;
        BDDMockito.given(studentService.findById(id)).willReturn(Optional.empty());

        this.mockMvc.perform(delete("/api/student/{id}", id))
                .andExpect(status().isNotFound());
    }
}

