package com.example.applocation.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
       /* Map<String, String> map = bindingResult.getFieldErrors().stream().collect(collector);
        for (Map.Entry<String, String> entry: map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }*/
        return bindingResult.getFieldErrors().stream().collect(collector);
    }
}
