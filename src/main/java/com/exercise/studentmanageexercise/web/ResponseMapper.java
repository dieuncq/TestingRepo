package com.exercise.studentmanageexercise.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {
    public static ResponseEntity<Object> errorToEntity(ResponseError error,
                                                       HttpStatus status) {
        return new ResponseEntity<Object>(error, status);
    }
}
