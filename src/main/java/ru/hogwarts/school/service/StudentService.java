package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student addStudent(Student student){
        return this.studentRepository.save(student);
    }

    public void deleteStudent(Long id){
        this.studentRepository.deleteById(id);
    }

    public Student editStudent(Student student){
        return this.studentRepository.save(student);
    }

    public Student findStudent(Long id){
        return this.studentRepository.findById(id).orElse(null);
    }

    public List<Student> findStudentByAge(int age){
        return this.studentRepository.findByAge(age);
    }
}
