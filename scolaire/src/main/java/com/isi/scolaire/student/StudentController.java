package com.isi.scolaire.student;

import com.isi.scolaire.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
@AllArgsConstructor
@Getter
@Setter
@Slf4j
@Tag(name = "Students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(
            @Valid @RequestBody StudentRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable("id") Long id){
        log.info("getStudentById");
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents(){
        log.info("getAllStudents");
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }

    @PutMapping
    public ResponseEntity<StudentResponse> updateStudent(
            @Valid @RequestBody StudentRequest request) {
        StudentResponse updatedStudent = studentService.updateStudent(request);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<StudentResponse>> listAllStudents(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        log.info("listAllStudents");
        return ResponseEntity.status(HttpStatus.OK).body(studentService.listAllStudents(page, size));
    }
}
