package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty addFaculty(Faculty faculty){
        this.logger.info("Added faculty {}", faculty.getName());
        return this.facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id){
        this.logger.info("Deleted faculty {}", id);

        this.facultyRepository.deleteById(id);
    }

    public Faculty editFaculty(Faculty faculty){
        this.logger.info("Edited faculty {}", faculty.getName());

        return this.facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id){
        this.logger.info("Found faculty {}", id);

        return this.facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> findFacultyByColor(String color, Boolean ignoreCase){
        this.logger.info("Found all faculties with color {} (ignore case: {})", color, ignoreCase);

        if(ignoreCase){
            return this.facultyRepository.findByColorIgnoreCase(color);
        }
        return this.facultyRepository.findByColor(color);
    }
}
