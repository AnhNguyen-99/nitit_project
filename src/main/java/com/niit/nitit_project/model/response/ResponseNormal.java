package com.niit.nitit_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseNormal<T> {
    private HttpStatus httpStatus;
    private String msg;
    private T data;
}
