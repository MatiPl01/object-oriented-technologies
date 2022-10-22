package pl.edu.agh.school;

import java.util.List;

public interface IPersistenceManager {
    void setTeachersStorageFileName(String teachersStorageFileName);
    void setClassStorageFileName(String classStorageFileName);
    void saveTeachers(List<Teacher> teachers);
    List<Teacher> loadTeachers();
    void saveClasses(List<SchoolClass> classes);
    List<SchoolClass> loadClasses();
}
