import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender("Console-Appender", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd} [%c{1}] [%t] - %m%n"
    }
}

root(INFO,["Console-Appender"])

logger('quantum.dao', TRACE)