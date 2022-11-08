package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@RequiredArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping()
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty){
        return ResponseEntity.ok(this.facultyService.addFaculty(faculty));
    }

    @PutMapping()
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty){
        Faculty result = this.facultyService.editFaculty(faculty);

        if(result == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable Long id){
        Faculty result = this.facultyService.findFaculty(id);

        if(result == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id){
        this.facultyService.deleteFaculty(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-color")
    public ResponseEntity<List<Faculty>> findFaculty(@RequestParam String color, @RequestParam(required = false) Boolean ignoreCase){
        if(ignoreCase == null)
            return ResponseEntity.ok(this.facultyService.findFacultyByColor(color, false));
        return ResponseEntity.ok(this.facultyService.findFacultyByColor(color, ignoreCase));
    }
}
