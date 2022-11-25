package pl.edu.agh.to.school.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.to.school.courses.Course;
import pl.edu.agh.to.school.courses.CourseRepository;
import pl.edu.agh.to.school.grades.Grade;
import pl.edu.agh.to.school.grades.GradeRepository;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfigurator {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        CourseRepository courseRepository,
                                        GradeRepository gradeRepository) {
        return args -> {
            if (studentRepository.count() == 0) {
                Student kowalski = new Student("Jan",
                                               "Kowalski",
                                               LocalDate.now(),
                                               "123456"
                );
                Student zielinski = new Student("Michał",
                                                "Zieliński",
                                                LocalDate.now(),
                                                "234567"
                );
                Student jablonski = new Student("Krzysztof",
                                                "Jabłoński",
                                                LocalDate.now(),
                                                "345678"
                );

                Course matematyka = new Course("matematyka");
                Course fizyka = new Course("fizyka");
                Course informatyka = new Course("informatyka");

                List<Grade> kowalskiGrades = List.of(new Grade(5, matematyka),
                                                     new Grade(4, matematyka),
                                                     new Grade(4.5f, fizyka)
                );
                kowalskiGrades.forEach(kowalski::giveGrade);

                List<Grade> zielinskiGrades = List.of(new Grade(3, informatyka),
                                                      new Grade(4, matematyka),
                                                      new Grade(2, fizyka),
                                                      new Grade(5, informatyka)
                );
                zielinskiGrades.forEach(zielinski::giveGrade);

                List<Grade> jablonskiGrades = List.of(new Grade(2, fizyka));
                jablonskiGrades.forEach(jablonski::giveGrade);

                courseRepository.saveAll(List.of(
                        matematyka,
                        fizyka,
                        informatyka
                ));
                gradeRepository.saveAll(kowalskiGrades);
                gradeRepository.saveAll(zielinskiGrades);
                gradeRepository.saveAll(jablonskiGrades);
                studentRepository.saveAll(List.of(
                        kowalski,
                        zielinski,
                        jablonski
                ));
            }
        };
    }
}
