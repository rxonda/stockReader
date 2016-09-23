package quantum

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Created by xonda on 13/03/2015.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
class Application {
    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
