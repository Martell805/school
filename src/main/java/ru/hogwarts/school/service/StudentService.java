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

    public List<Student> findStudentByAge(int min, int max){
        return this.studentRepository.findByAgeBetween(min, max);
    }

    public int studentAmount(){
        return this.studentRepository.studentsAmount();
    }

    public double studentsAverageAge(){
        return this.studentRepository.studentsAverageAge();
    }

    public List<Student> findLastFiveStudents(){
        return this.studentRepository.findLastFiveStudents();
    }

    public List<Student> findStudentByName(String name) {
        return this.studentRepository.findByName(name);
    }
}
