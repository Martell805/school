package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student addStudent(Student student){
        this.logger.info("Added student {}", student.getName());

        return this.studentRepository.save(student);
    }

    public void deleteStudent(Long id){
        this.logger.info("Deleted student {}", id);

        this.studentRepository.deleteById(id);
    }

    public Student editStudent(Student student){
        this.logger.info("Edited student {}", student.getName());

        return this.studentRepository.save(student);
    }

    public Student findStudent(Long id){
        this.logger.info("Found student {}", id);

        return this.studentRepository.findById(id).orElse(null);
    }

    public List<Student> findStudentByAge(int age){
        this.logger.info("Found students by age {}", age);

        return this.studentRepository.findByAge(age);
    }

    public List<Student> findStudentByAge(int min, int max){
        this.logger.info("Found students by age from {} to {}", min, max);

        return this.studentRepository.findByAgeBetween(min, max);
    }

    public int studentAmount(){
        this.logger.info("Found students amount");

        return this.studentRepository.studentsAmount();
    }

    public double studentsAverageAge(){
        this.logger.info("Found students average age");

        return this.studentRepository.studentsAverageAge();
    }

    public List<Student> findLastFiveStudents(){
        this.logger.info("Found five last students");

        return this.studentRepository.findLastFiveStudents();
    }

    public List<String> findAllA(){
        return this.studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toLowerCase)
                .filter(s -> s.startsWith("a"))
                .sorted(String::compareTo)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public double studentsAverageAge2(){
        return this.studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }
                
    public List<Student> findStudentByName(String name) {
        return this.studentRepository.findByName(name);
    }

    public void printStudentsParallel(){
        List<Student> students = this.studentRepository.findAll();

        Thread thread12 = new Thread(() -> {
            System.out.println(students.get(0).getName());
            System.out.println(students.get(1).getName());
        });

        Thread thread34 = new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });

        Thread thread56 = new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });

        thread12.start();
        thread34.start();
        thread56.start();
    }

    private synchronized void printStudentName(Student student) {
        System.out.println(student.getName());
    }

    private synchronized void printStudentName(Student student1, Student student2) {
        System.out.println(student1.getName());
        System.out.println(student2.getName());
    }

    public void printStudentsParallelS(){
        List<Student> students = this.studentRepository.findAll();

        System.out.println("Collection:");
        students.forEach(this::printStudentName);

        System.out.println("Threads:");
        Thread thread12 = new Thread(() -> {
            this.printStudentName(students.get(0), students.get(1));
        });

        Thread thread34 = new Thread(() -> {
            this.printStudentName(students.get(2), students.get(3));
        });

        Thread thread56 = new Thread(() -> {
            this.printStudentName(students.get(4), students.get(5));
        });

        thread12.start();
        thread34.start();
        thread56.start();
    }
}
