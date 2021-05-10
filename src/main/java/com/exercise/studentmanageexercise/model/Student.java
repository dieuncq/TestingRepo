package com.exercise.studentmanageexercise.model;

import com.exercise.studentmanageexercise.validation.AgeCheck;
import com.exercise.studentmanageexercise.validation.EmailCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Student")
@EntityListeners(AuditingEntityListener.class)
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is not blank")
    private String name;

    @EmailCheck
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Address is not blank")
    @Column(name = "address", nullable = false)
    private String address;

    @AgeCheck
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime created_date;

    @Column(name = "created_by")
    @CreatedBy
    private String created_by;

    @Column(name = "last_modified_at")
    @LastModifiedDate
    private LocalDateTime last_modified_at;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String last_modified_by;
}
