package pl.edu.agh.school;

import pl.edu.agh.logger.Logger;

import javax.inject.Inject;

public class SchoolClassFactory implements ISchoolClassFactory {
    private final Logger logger;

    @Inject
    public SchoolClassFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public SchoolClass create(String name, String profile) {
        return new SchoolClass(name, profile, logger);
    }
}
