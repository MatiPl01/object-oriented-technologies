package pl.edu.agh.school.modules;

import pl.edu.agh.logger.IMessageSerializer;
import pl.edu.agh.logger.Logger;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Set;

public class LoggerProvider implements Provider<Logger> {
    private final Logger instance;

    @Inject
    public LoggerProvider(Set<IMessageSerializer> messageSerializers) {
        this.instance = new Logger(messageSerializers);
    }

    @Override
    public Logger get() {
        return instance;
    }
}
