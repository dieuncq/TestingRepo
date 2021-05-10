package com.exercise.studentmanageexercise.mapper;

import com.exercise.studentmanageexercise.dto.StudentDTO;
import com.exercise.studentmanageexercise.model.Student;

public class StudentMapper {
    public static Student DtoToEntity(StudentDTO stu) {
        return new Student().setName(stu.getName()).setEmail(stu.getEmail())
                .setAddress(stu.getAddress()).setBirthday(stu.getBirthday());
    }

    public static StudentDTO EntityToDto(Student student) {
        return new StudentDTO().setName(student.getName()).setEmail(student.getEmail())
                .setAddress(student.getAddress()).setBirthday(student.getBirthday());
    }
}
