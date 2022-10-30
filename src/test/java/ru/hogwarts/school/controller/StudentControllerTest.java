package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Student;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testAddStudent() throws Exception {
        Student student = new Student();
        student.setName("Test");
        student.setAge(100);

        Student response = this.restTemplate.postForObject("http://localhost:"+ port + "/student", student, Student.class);


        this.restTemplate.delete("http://localhost:"+ port + "/student/" + response.getId());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Test");
        Assertions.assertThat(response.getAge()).isEqualTo(100);
    }

    @Test
    public void testFindStudent() throws Exception {
        Student student = new Student();
        student.setName("Test");
        student.setAge(100);

        Student postResponse = this.restTemplate.postForObject("http://localhost:"+ port + "/student", student, Student.class);

        Student getResponse = this.restTemplate.getForObject("http://localhost:"+ port + "/student/" + postResponse.getId(), Student.class);

        this.restTemplate.delete("http://localhost:"+ port + "/student/" + postResponse.getId());

        Assertions.assertThat(getResponse.getId()).isEqualTo(postResponse.getId());
        Assertions.assertThat(getResponse.getName()).isEqualTo("Test");
        Assertions.assertThat(getResponse.getAge()).isEqualTo(100);
    }

    @Test
    public void testEditStudent() throws Exception {
        Student student = new Student();
        student.setName("Test");
        student.setAge(100);

        Student postResponse = this.restTemplate.postForObject("http://localhost:"+ port + "/student", student, Student.class);

        student.setId(postResponse.getId());
        student.setName("Test2");
        student.setAge(200);

        this.restTemplate.put("http://localhost:"+ port + "/student", student, Student.class);

        Student getResponse = this.restTemplate.getForObject("http://localhost:"+ port + "/student/" + postResponse.getId(), Student.class);

        this.restTemplate.delete("http://localhost:"+ port + "/student/" + postResponse.getId());

        Assertions.assertThat(getResponse.getId()).isEqualTo(postResponse.getId());
        Assertions.assertThat(getResponse.getName()).isEqualTo("Test2");
        Assertions.assertThat(getResponse.getAge()).isEqualTo(200);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setName("Test");
        student.setAge(100);

        Student postResponse = this.restTemplate.postForObject("http://localhost:"+ port + "/student", student, Student.class);

        this.restTemplate.delete("http://localhost:"+ port + "/student/" + postResponse.getId());

        Student getResponse = this.restTemplate.getForObject("http://localhost:"+ port + "/student/" + postResponse.getId(), Student.class);

        Assertions.assertThat(getResponse).isNull();
    }

    @Test
    public void testFindStudentByAge() throws Exception {
        Student student1 = new Student();
        student1.setName("Test");
        student1.setAge(100);

        Student student2 = new Student();
        student2.setName("Test2");
        student2.setAge(200);

        Student postResponse1 = this.restTemplate.postForObject("http://localhost:"+ port + "/student", student1, Student.class);
        Student postResponse2 = this.restTemplate.postForObject("http://localhost:"+ port + "/student", student2, Student.class);

        List<Student> getResponse1 = this.restTemplate.getForObject("http://localhost:"+ port + "/student/by-age?age=100", List.class);
        List<Student> getResponse2 = this.restTemplate.getForObject("http://localhost:"+ port + "/student/by-age?minAge=50&maxAge=150", List.class);

        this.restTemplate.delete("http://localhost:"+ port + "/student/" + postResponse1.getId());
        this.restTemplate.delete("http://localhost:"+ port + "/student/" + postResponse2.getId());

        Assertions
                .assertThat(getResponse1.stream()
                        .filter(
                                student -> student.getId().equals(postResponse1.getId())
                        ).findAny().orElse(null))
                .isNotNull();

        Assertions
                .assertThat(getResponse1.stream()
                        .filter(
                                student -> student.getId().equals(postResponse2.getId())
                        ).findAny().orElse(null))
                .isNull();

        Assertions
                .assertThat(getResponse2.stream()
                        .filter(
                                student -> student.getId().equals(postResponse1.getId())
                        ).findAny().orElse(null))
                .isNotNull();

        Assertions
                .assertThat(getResponse2.stream()
                        .filter(
                                student -> student.getId().equals(postResponse2.getId())
                        ).findAny().orElse(null))
                .isNull();
    }
}
