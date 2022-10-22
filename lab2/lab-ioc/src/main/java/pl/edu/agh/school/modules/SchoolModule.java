package pl.edu.agh.school.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import pl.edu.agh.school.IPersistenceManager;
import pl.edu.agh.school.annotations.Classes;
import pl.edu.agh.school.annotations.Teachers;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {
    @Provides
    IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Teachers
    static String provideTeachersStorageFileName() {
        return "teachers.dat";
    }

    @Provides
    @Classes
    static String provideClassStorageFileName() {
        return "classes.dat";
    }
}
