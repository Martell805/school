package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public Faculty addFaculty(Faculty faculty){
        return this.facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id){
        this.facultyRepository.deleteById(id);
    }

    public Faculty editFaculty(Faculty faculty){
        return this.facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id){
        return this.facultyRepository.findById(id).orElse(null);
    }
}
