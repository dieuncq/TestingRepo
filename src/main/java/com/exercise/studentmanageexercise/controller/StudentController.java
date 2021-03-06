package com.exercise.studentmanageexercise.controller;

import com.exercise.studentmanageexercise.dto.StudentDTO;
import com.exercise.studentmanageexercise.exception.EmailExistedException;
import com.exercise.studentmanageexercise.exception.StudentNotFoundException;
import com.exercise.studentmanageexercise.mapper.StudentMapper;
import com.exercise.studentmanageexercise.model.Student;
import com.exercise.studentmanageexercise.service.StudentService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;



    @GetMapping(value = "/students")
    List<Student> getAll() {
        return studentService.getAllStudents();
    }

    @GetMapping(value = "/student/{id}")
    ResponseEntity<Student> getById(@PathVariable("id") @Min(1) int id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));
        return ResponseEntity.ok().body(student);
    }

    public boolean isEmailExisted(String email) {
        List<Student> list = studentService.getAllStudents();
        for (Student s: list ) {
            if (s.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmailExistedWithId(String email, int id) {
        List<Student> list = studentService.getAllStudents();
        for (Student s: list ) {
            if (s.getId() == id) {
                break;
            }
            if (s.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    @PostMapping(value = "student")
    ResponseEntity<String> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        @Valid
        Student student = StudentMapper.DtoToEntity(studentDTO);
        if (!isEmailExisted(student.getEmail())) {
                throw new EmailExistedException("Email Has Already Existed");
        }
        Student added = studentService.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(added.getId()).toUri();
        return ResponseEntity.ok().body("Student with id " + added.getId() + " added successfully");
    }

    @PutMapping(value = "/student/{id}")
    ResponseEntity<Student> updateStudent(@PathVariable("id") @Min(1) int id,
                                          @Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));
        @Valid
        Student newStudent = StudentMapper.DtoToEntity(studentDTO);
        if (!isEmailExistedWithId(newStudent.getEmail(), id)) {
            throw new EmailExistedException("Email Has Already Existed");
        }
        newStudent.setId(student.getId());
        studentService.save(newStudent);
        return ResponseEntity.ok().body(newStudent);
    }

    @DeleteMapping(value = "/student/{id}")
    ResponseEntity<String> deleteStudent(@PathVariable("id") @Min(1) int id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student Not Found"));
        studentService.delete(student.getId());
        return ResponseEntity.ok().body("Student with ID " +id + "deleted with success");
    }

    @GetMapping("/students/{pageNo}/{pageSize}")
    public List<Student> getPaginatedStudent(@PathVariable int pageNo,
                                             @PathVariable int pageSize) {
        return studentService.pagination(pageNo, pageSize);
    }

    @GetMapping("/students/get-by-name/{name}")
    public List<Student> getStudentByName(@PathVariable String name) {
        return studentService.getStudentByName(name);
    }

    @GetMapping("/students/get-by-email/{email}")
    public List<Student> getStudentByEmail(@PathVariable String email) {
        return studentService.getStudentByEmail(email);
    }

    @GetMapping("/students/get-by-address/{address}")
    public List<Student> getStudentByAddress(@PathVariable String address) {
        return studentService.getStudentByAddress(address);
    }

    @GetMapping("/students/get-by-age/{age}")
    public List<Student> getStudentByAge(@PathVariable int age) {
        return studentService.getStudentByAge(age);
    }
    
    @GetMapping("/students/get-by")
    public ResponseEntity<?> getStudentBy(@RequestParam HashMap<String, String> getStudentRequest) {

        try {
            String name = getStudentRequest.get("name");
            String email = getStudentRequest.get("email");
            String address = getStudentRequest.get("address");
            String age_str = getStudentRequest.get("age");

            Integer age = null;

            if (age_str != null) {
                age = Integer.parseInt(age_str);
            } else {
                age = 0;
            }

            Optional<Student> list = null;

            if (name != null && email != null && address != null && getStudentRequest.get("age") == null) {
                list = studentService.findByNameAndEmailAndAddress(name, email, address);
                if (list.equals(null)) {
                    throw new StudentNotFoundException("Student Not Found");
                }
            }
            if (name != null && email == null && address == null && age_str == null) {
                list = studentService.findByName(name);
                if (list.equals(null)) {
                    throw new StudentNotFoundException("Student Not Found");
                }
            }
            if (name != null && email == null && address != null && age_str == null) {
                list = studentService.findByNameAndAddress(name, address);
                if (list.equals(null)) {
                    throw new StudentNotFoundException("Student Not Found");
                }
            }
            if (name != null && email != null && address == null && age_str == null) {
                list = studentService.findByNameAndEmail(name, email);
                if (list.equals(null)) {
                    throw new StudentNotFoundException("Student Not Found");
                }
            }

            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}