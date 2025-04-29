package com.isi.scolaire.teacher;


import com.isi.scolaire.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teachers")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponse> addTeacher(
            @Valid @RequestBody TeacherRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.addTeacher(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getTeacherById(id));
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getAllTeachers(){
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.getAllTeachers());
    }

    @PutMapping
    public ResponseEntity<TeacherResponse> updateTeacher(
            @Valid @RequestBody TeacherRequest request) {
        TeacherResponse updatedTeacher = teacherService.updateTeacher(request);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable("id") Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teacherId}/assign-course/{courseId}")
    public ResponseEntity<TeacherResponse> assignCourseToTeacher(
            @PathVariable Long teacherId, @PathVariable Long courseId
    ){
        TeacherResponse response = teacherService.assignCourseToTeacher(teacherId, courseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<TeacherResponse>> listAllTeachers(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.listAllTeachers(page, size));
    }
}
