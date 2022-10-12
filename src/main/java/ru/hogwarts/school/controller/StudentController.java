package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping()
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(this.studentService.addStudent(student));
    }

    @PutMapping()
    public ResponseEntity<Student> editStudent(@RequestBody Student student){
        Student result = this.studentService.editStudent(student);

        if(result == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable Long id){
        Student result = this.studentService.findStudent(id);

        if(result == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
        this.studentService.deleteStudent(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("by-age/{age}")
    public ResponseEntity<List<Student>> findStudent(@PathVariable Integer age){
        return ResponseEntity.ok(this.studentService.findStudentByAge(age));
    }
}
