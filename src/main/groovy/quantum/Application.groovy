package quantum

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by xonda on 13/03/2015.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
class Application {

    @Bean
    ExecutorService executorService() {
        Executors.newFixedThreadPool(5);
    }

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
