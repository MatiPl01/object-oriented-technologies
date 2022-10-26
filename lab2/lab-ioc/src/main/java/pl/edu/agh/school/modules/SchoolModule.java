package pl.edu.agh.school.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import pl.edu.agh.school.*;
import pl.edu.agh.school.annotations.Classes;
import pl.edu.agh.school.annotations.Teachers;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {
    protected void configure() {
        bind(ISchoolClassFactory.class).to(SchoolClassFactory.class);
        bind(ISubjectFactory.class).to(SubjectFactory.class);
    }

    @Provides
    protected IPersistenceManager providePersistenceManager(
            SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Teachers
    protected static String provideTeachersStorageFileName() {
        return "teachers.dat";
    }

    @Provides
    @Classes
    protected static String provideClassStorageFileName() {
        return "classes.dat";
    }
}
