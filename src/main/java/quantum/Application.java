package quantum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import quantum.config.WebConfig;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.info("Initializing server...");

        new SpringApplicationBuilder()
                .sources(WebConfig.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.REACTIVE)
                .build(args)
                .run(args);
    }
}
