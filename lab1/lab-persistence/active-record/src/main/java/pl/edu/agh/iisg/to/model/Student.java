package pl.edu.agh.iisg.to.model;

import pl.edu.agh.iisg.to.executor.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Student {
    private final int id;

    private final String firstName;

    private final String lastName;

    private final int indexNumber;

    Student(final int id,
            final String firstName,
            final String lastName,
            final int indexNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.indexNumber = indexNumber;
    }

    public static Optional<Student> create(final String firstName,
                                           final String lastName,
                                           final int indexNumber) {
        String sql =
                "INSERT INTO student (first_name, last_name, index_number) VALUES (?, ?, ?)";
        // it is important to maintain the correct order of the variables
        Object[] args = { firstName, lastName, indexNumber };

        try {
            int id = QueryExecutor.createAndObtainId(sql, args);
            return Student.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<Student> findByIndexNumber(final int indexNumber) {
        String sql = "SELECT * FROM student WHERE index_number = ?";
        return find(indexNumber, sql);
    }

    public static Optional<Student> findById(final int id) {
        String sql = "SELECT * FROM student WHERE id = (?)";
        return find(id, sql);
    }

    private static Optional<Student> find(int value, String sql) {
        Object[] args = { value };
        try (ResultSet rs = QueryExecutor.read(sql, args)) {
            return rs.next() ? Optional.of(new Student(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("index_number")
            )) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Map<Course, Float> createReport() {
        String sql =
                "SELECT grade, course_id, name " +
                "FROM grade AS g " +
                "INNER JOIN course AS c " +
                "ON c.id = g.course_id " +
                "WHERE student_id = (?) " +
                "ORDER BY course_id";
        Object[] args = {
                id
        };

        Map<Course, Float> report = new HashMap<>();

        try (ResultSet rs = QueryExecutor.read(sql, args)) {
            int gradesSum = 0;
            int gradesCount = 0;
            int prevCourseId = -1;
            Course course = null;

            while (rs.next()) {
                int courseId = rs.getInt("course_id");
                if (courseId != prevCourseId) {
                    if (course != null)
                        report.put(course, gradesSum / (float) gradesCount);
                    course = new Course(courseId, rs.getString("name"));
                    prevCourseId = courseId;
                    gradesSum = 0;
                    gradesCount = 0;
                }
                gradesSum += rs.getInt("grade");
                gradesCount++;
            }

            if (course != null) {
                report.put(course, gradesSum / (float) gradesCount);
            }

            return report;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyMap();
    }

    public int id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public int indexNumber() {
        return indexNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Student student = (Student) o;

        if (id != student.id)
            return false;
        if (indexNumber != student.indexNumber)
            return false;
        if (!firstName.equals(student.firstName))
            return false;
        return lastName.equals(student.lastName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + indexNumber;
        return result;
    }

    public static class Columns {

        public static final String ID = "id";

        public static final String FIRST_NAME = "first_name";

        public static final String LAST_NAME = "last_name";

        public static final String INDEX_NUMBER = "index_number";
    }
}
