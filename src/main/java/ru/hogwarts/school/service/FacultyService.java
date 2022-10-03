package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private long maxId = 0;
    private final Map<Long, Faculty> faculties;


    public FacultyService() {
        this.faculties = new HashMap<>();
    }

    public Faculty addFaculty(Faculty faculty){
        maxId++;
        faculty.setId(maxId);
        this.faculties.put(maxId, faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id){
        Faculty faculty = this.faculties.get(id);

        if(faculty == null) return null;

        this.faculties.remove(id);

        return faculty;
    }

    public Faculty editFaculty(Faculty new_faculty){
        Faculty faculty = this.faculties.get(new_faculty.getId());

        if(faculty == null) return null;

        this.faculties.put(new_faculty.getId(), new_faculty);

        return new_faculty;
    }

    public Faculty findFaculty(Long id){
        return this.faculties.get(id);
    }

    public List<Faculty> findFacultyByColor(String color){
        return this.faculties.values().stream()
                .filter(student -> student.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
