package pl.edu.agh.school;

import pl.edu.agh.logger.Logger;

import javax.inject.Inject;

public class SubjectFactory implements ISubjectFactory {
    private final Logger logger;

    @Inject
    public SubjectFactory(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Subject create(String name) {
        return new Subject(name, logger);
    }
}
