package com.exercise.studentmanageexercise;

import com.exercise.studentmanageexercise.dto.StudentDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class StudentManageExerciseApplication {

    @Profile("dev")
    @Bean
    public String devBean() {
        return "dev";
    }

    @Profile("prod")
    @Bean
    public String prodBean() {
        return "prod";
    }

    public static void main(String[] args) {
        SpringApplication.run(StudentManageExerciseApplication.class, args);
    }

}
