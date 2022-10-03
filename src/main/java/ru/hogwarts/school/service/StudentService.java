package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private long maxId = 0;
    private final Map<Long, Student> students;


    public StudentService() {
        this.students = new HashMap<>();
    }

    public Student addStudent(Student student){
        maxId++;
        student.setId(maxId);
        this.students.put(maxId, student);
        return student;
    }

    public Student deleteStudent(Long id){
        Student student = this.students.get(id);

        if(student == null) return null;

        this.students.remove(id);

        return student;
    }

    public Student editStudent(Student new_student){
        Student student = this.students.get(new_student.getId());

        if(student == null) return null;

        this.students.put(new_student.getId(), new_student);

        return new_student;
    }

    public Student findStudent(Long id){
        return this.students.get(id);
    }

    public List<Student> findStudentByAge(int age){
        return this.students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
