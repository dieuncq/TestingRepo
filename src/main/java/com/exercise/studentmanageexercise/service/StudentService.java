package com.exercise.studentmanageexercise.service;

import com.exercise.studentmanageexercise.exception.EmailExistedException;
import com.exercise.studentmanageexercise.model.Student;
import com.exercise.studentmanageexercise.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService implements Istudent{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findById(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> pagination(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Student> pagedResult = studentRepository.findAll(paging);

        return pagedResult.toList();
    }

    @Override
    public List<Student> getStudentByName(String name) {
        return studentRepository.getStudentByName(name);
    }

    @Override
    public List<Student> getStudentByEmail(String email) {
        return studentRepository.getStudentByEmail(email);
    }

    @Override
    public List<Student> getStudentByAddress(String address) {
        return studentRepository.getStudentByAddress(address);
    }

    @Override
    public List<Student> getStudentByAge(int age) {
        return studentRepository.getStudentByAge(age);
    }

    public Student createStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new EmailExistedException("Email Has Already Existed");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteById(int id) {
        studentRepository.deleteById(id);
    }

    public Optional<Student> findByName(String name) {
        return studentRepository.findStudentByName(name);
    }

    public Optional<Student> findByNameAndAddress(String name, String address) {
        return studentRepository.findStudentByNameAndAddress(name, address);
    }

    public Optional<Student> findByNameAndEmail(String name, String email) {
        return studentRepository.findStudentByNameAndEmail(name, email);
    }

    public Optional<Student> findByNameAndEmailAndAddress(String name, String email, String address) {
        return studentRepository.findStudentByNameAndEmailAndAddress(name, email, address);
    }


}
