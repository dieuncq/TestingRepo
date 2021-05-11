package com.exercise.studentmanageexercise.repository;

import com.exercise.studentmanageexercise.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query(value = "select s from Student s where s.name=:name")
    List<Student> getStudentByName(String name);

    @Query(value = "select s from Student s where s.email=:email")
    List<Student> getStudentByEmail(String email);

    @Query(value = "select s from Student s where s.address=:address")
    List<Student> getStudentByAddress(String address);

    @Query(value = "select s from Student s where (datediff(yy,s.birthday,getdate())) =:age")
    List<Student> getStudentByAge(int age);

    Optional<Student> findStudentByEmail(String email);

}
