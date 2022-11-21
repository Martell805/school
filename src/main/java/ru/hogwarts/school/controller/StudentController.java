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

    @GetMapping("/by-name")
    public ResponseEntity<List<Student>> findStudent(@RequestParam String name){
        return ResponseEntity.ok(this.studentService.findStudentByName(name));
    }

    @GetMapping("/by-age")
    public ResponseEntity<List<Student>> findStudent(@RequestParam(required = false) Integer age,
                                                     @RequestParam(required = false) Integer minAge,
                                                     @RequestParam(required = false) Integer maxAge){
        if(age != null){
            return ResponseEntity.ok(this.studentService.findStudentByAge(age));
        } else {
            return ResponseEntity.ok(this.studentService.findStudentByAge(minAge, maxAge));
        }
    }


    @GetMapping("/amount")
    public ResponseEntity<Integer> studentAmount(){
        return ResponseEntity.ok(this.studentService.studentAmount());
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> studentsAverageAge(){
        return ResponseEntity.ok(this.studentService.studentsAverageAge());
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> findLastFiveStudents(){
        return ResponseEntity.ok(this.studentService.findLastFiveStudents());
    }

    @GetMapping("/a")
    public ResponseEntity<List<String>> findAllA(){
        return ResponseEntity.ok(this.studentService.findAllA());
    }

    @GetMapping("/average-age2")
    public ResponseEntity<Double> studentsAverageAge2(){
        return ResponseEntity.ok(this.studentService.studentsAverageAge2());
    }

    @GetMapping("/psp")
    public ResponseEntity<Student> printStudentsParallel(){
        this.studentService.printStudentsParallel();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/psps")
    public ResponseEntity<Student> printStudentsParallelS(){
        this.studentService.printStudentsParallelS();

        return ResponseEntity.ok().build();
    }
}
