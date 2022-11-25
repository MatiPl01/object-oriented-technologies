package pl.edu.agh.to.school.grades;

import pl.edu.agh.to.school.courses.Course;

import javax.persistence.*;

@Entity
public class Grade {
    private float gradeValue;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Grade() {}

    public Grade(float gradeValue, Course course) {
        this.gradeValue = gradeValue;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(float gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
