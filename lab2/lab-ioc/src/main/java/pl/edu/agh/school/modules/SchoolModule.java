package pl.edu.agh.school.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

public class SchoolModule extends AbstractModule {
    @Provides
    IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Named("Teachers")
    static String provideTeachersStorageFileName() {
        return "teachers.dat";
    }

    @Provides
    @Named("Class")
    static String provideClassStorageFileName() {
        return "classes.dat";
    }

    @Provides
    @Singleton
    Logger logger() {
        return new Logger();
    }
}
