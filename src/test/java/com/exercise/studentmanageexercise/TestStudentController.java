package com.exercise.studentmanageexercise;

import com.exercise.studentmanageexercise.controller.StudentController;
import com.exercise.studentmanageexercise.model.Student;
import com.exercise.studentmanageexercise.service.StudentService;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.fieldType;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TestStudentController {

//    @Mock
//    private StudentService studentService;
//
//    @InjectMocks
//    StudentController studentController = new StudentController();
//
//    @Captor
//    private ArgumentCaptor<Student> studentArgumentCaptor;
//
//    private List<Student> studentList;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        this.studentList = new ArrayList<>();
//        Student student = new Student();
//        student.setId(69);
//        student.setName("Nguyn Duy Tan");
//        student.setEmail("tannd1904@gmail.com");
//        student.setAddress("Ben Tre");
//        student.setBirthday(LocalDate.of(1999,4,4));
//        this.studentList.add(student);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
//    }
//
//    @Test
//    void shouldFetchAllUsers() throws Exception {
//
//        given(userService.findAllUsers()).willReturn(userList);
//
//        this.mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(userList.size())));
//    }
//
//    @Test
//    void shouldFetchOneUserById() throws Exception {
//        final Long userId = 1L;
//        final User user = new User(1L, "ten@mail.com","teten","teten");
//
//        given(userService.findUserById(userId)).willReturn(Optional.of(user));
//
//        this.mockMvc.perform(get("/api/users/{id}", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is(user.getEmail())))
//                .andExpect(jsonPath("$.name", is(user.getName())));
//    }
//
//    @Test
//    void shouldReturn404WhenFindUserById() throws Exception {
//        final Long userId = 1L;
//        given(userService.findUserById(userId)).willReturn(Optional.empty());
//
//        this.mockMvc.perform(get("/api/user/{id}", userId))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void shouldCreateNewUser() throws Exception {
//        given(userService.createUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));
//
//        User user = new User(null, "newuser1@gmail.com", "pwd", "Name");
//
//        this.mockMvc.perform(post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.email", is(user.getEmail())))
//                .andExpect(jsonPath("$.password", is(user.getPassword())))
//                .andExpect(jsonPath("$.name", is(user.getName())))
//        ;
//    }
//
//    @Test
//    void shouldReturn400WhenCreateNewUserWithoutEmail() throws Exception {
//        User user = new User(null, null, "pwd", "Name");
//
//        this.mockMvc.perform(post("/api/users")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isBadRequest())
//                .andExpect(header().string("Content-Type", is("application/problem+json")))
//                .andExpect(jsonPath("$.type", is("https://zalando.github.io/problem/constraint-violation")))
//                .andExpect(jsonPath("$.title", is("Constraint Violation")))
//                .andExpect(jsonPath("$.status", is(400)))
//                .andExpect(jsonPath("$.violations", hasSize(1)))
//                .andExpect(jsonPath("$.violations[0].field", is("email")))
//                .andExpect(jsonPath("$.violations[0].message", is("Email should not be empty")))
//                .andReturn()
//        ;
//    }
//
//    @Test
//    void shouldUpdateUser() throws Exception {
//        Long userId = 1L;
//        User user = new User(userId, "user1@gmail.com", "pwd", "Name");
//        given(userService.findUserById(userId)).willReturn(Optional.of(user));
//        given(userService.updateUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));
//
//        this.mockMvc.perform(put("/api/users/{id}", user.getId())
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is(user.getEmail())))
//                .andExpect(jsonPath("$.password", is(user.getPassword())))
//                .andExpect(jsonPath("$.name", is(user.getName())));
//
//    }
//
//    @Test
//    void shouldReturn404WhenUpdatingNonExistingUser() throws Exception {
//        Long userId = 1L;
//        given(userService.findUserById(userId)).willReturn(Optional.empty());
//        User user = new User(userId, "user1@gmail.com", "pwd", "Name");
//
//        this.mockMvc.perform(put("/api/users/{id}", userId)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isNotFound());
//
//    }
//
//    @Test
//    void shouldDeleteUser() throws Exception {
//        Long userId = 1L;
//        User user = new User(userId, "user1@gmail.com", "pwd", "Name");
//        given(userService.findUserById(userId)).willReturn(Optional.of(user));
//        doNothing().when(userService).deleteUserById(user.getId());
//
//        this.mockMvc.perform(delete("/api/users/{id}", user.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is(user.getEmail())))
//                .andExpect(jsonPath("$.password", is(user.getPassword())))
//                .andExpect(jsonPath("$.name", is(user.getName())));
//
//    }
//
//    @Test
//    void shouldReturn404WhenDeletingNonExistingUser() throws Exception {
//        Long userId = 1L;
//        given(userService.findUserById(userId)).willReturn(Optional.empty());
//
//        this.mockMvc.perform(delete("/api/users/{id}", userId))
//                .andExpect(status().isNotFound());
//
//    }
}
