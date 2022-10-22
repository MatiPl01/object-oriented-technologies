package pl.edu.agh.school.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import pl.edu.agh.logger.ConsoleMessageSerializer;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.Logger;

import java.util.HashSet;

public class LoggerModule extends AbstractModule {
    @Provides
    @Singleton
    static Logger provideLogger() {
        return new Logger(new HashSet<>() {
            {
                add(new ConsoleMessageSerializer());
                add(new FileMessageSerializer("persistence.log"));
            }});
    }
}
