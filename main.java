package com.example.studentexception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice; import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime; import java.util.HashMap; import java.util.Map;

/* -------------------- MAIN APPLICATION	*/
 
@SpringBootApplication
public class StudentApplication {

public static void main(String[] args) { SpringApplication.run(StudentApplication.class, args);
}
}

/* -------------------- CONTROLLER	*/

@RestController
class StudentController {

@GetMapping("/student/{id}")
public Map<String,Object> getStudent(@PathVariable int id){

if(id <= 0){
throw new InvalidInputException("Student ID must be positive");
}

if(id != 1){
throw new StudentNotFoundException("Student with ID "+id+" not found");
}

Map<String,Object> student = new HashMap<>(); student.put("id",1);
student.put("name","Rahul"); student.put("course","Computer Science");

return student;
}
}

/* -------------------- CUSTOM EXCEPTION 1	*/

class StudentNotFoundException extends RuntimeException{ public StudentNotFoundException(String message){
 
super(message);
}
}

/* -------------------- CUSTOM EXCEPTION 2	*/

class InvalidInputException extends RuntimeException{ public InvalidInputException(String message){
super(message);
}
}

/* -------------------- GLOBAL EXCEPTION HANDLER	*/

@ControllerAdvice
class GlobalExceptionHandler {

@ExceptionHandler(StudentNotFoundException.class)
public ResponseEntity<Map<String,Object>> handleStudentNotFound(StudentNotFoundException ex){

Map<String,Object> error = new HashMap<>(); error.put("timestamp", LocalDateTime.now()); error.put("message", ex.getMessage()); error.put("statusCode", 404);

return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
}

@ExceptionHandler(InvalidInputException.class)
public ResponseEntity<Map<String,Object>> handleInvalidInput(InvalidInputException ex){

Map<String,Object> error = new HashMap<>(); error.put("timestamp", LocalDateTime.now()); error.put("message", ex.getMessage()); error.put("statusCode", 400);

return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
}
 
}
