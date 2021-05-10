package com.exercise.studentmanageexercise.service;

import com.exercise.studentmanageexercise.model.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface Istudent {
    List<Student> getAllStudents();
    Optional<Student> findById(int id);
    Student save(Student student);
    void delete(int id);

    List<Student> pagination(int pageNo, int pageSize);

    List<Student> getStudentByName(String name);
    List<Student> getStudentByEmail(String email);
    List<Student> getStudentByAddress(String address);
    List<Student> getStudentByAge(int age);
}
