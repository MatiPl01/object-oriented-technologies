package pl.edu.agh.school;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.modules.LoggerModule;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class LoggerModuleTest {
    private Injector injector;

    @BeforeEach
    public void setup() {
        injector = Guice.createInjector(new LoggerModule());
    }

    @Test
    public void testLoggerIsSingleton() {
        Logger logger1 = injector.getInstance(Logger.class);
        Logger logger2 = injector.getInstance(Logger.class);

        assertSame(logger1, logger2);
    }
}
