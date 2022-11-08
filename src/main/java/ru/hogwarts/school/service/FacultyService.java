package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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

    public List<Faculty> findFacultyByColor(String color){
        return this.facultyRepository.findByColor(color);
    }

    public List<Faculty> findFacultyByColor(String color, Boolean ignoreCase){
        if(ignoreCase){
            return this.facultyRepository.findByColorIgnoreCase(color);
        }
        return this.facultyRepository.findByColor(color);
    }

    public String getLongestName(){
        return this.facultyRepository.findAll().stream()
                .max(Comparator.comparing(f -> f.getName().length()))
                .map(Faculty::getName)
                .orElse("");
    }

    public int getNumber() {
        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
    }
}
