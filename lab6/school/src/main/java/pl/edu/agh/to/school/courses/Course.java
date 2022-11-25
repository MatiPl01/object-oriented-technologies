package pl.edu.agh.to.school.courses;

import pl.edu.agh.to.school.student.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    List<Student> students = new ArrayList<>();

    public Course() {}

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void assignStudent(Student student) {
        students.add(student);
    }

    void removeStudent(Student student) {
        students.remove(student);
    }

    List<Student> getStudents() { return students; }
}
