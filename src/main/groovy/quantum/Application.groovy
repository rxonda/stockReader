package quantum

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import quantum.config.WebConfig

/**
 * Created by xonda on 13/03/2015.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import([WebConfig.class])
class Application {
    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
