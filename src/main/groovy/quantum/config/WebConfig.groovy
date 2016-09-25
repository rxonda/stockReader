package quantum.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import quantum.rx.web.ObservableReturnValueHandler

import javax.annotation.PostConstruct
import javax.annotation.Resource

/**
 * Created by xonda on 9/24/16.
 */
@Configuration
class WebConfig extends WebMvcConfigurerAdapter {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter

    @PostConstruct
    void init() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(requestMappingHandlerAdapter.getReturnValueHandlers())
        handlers.add(0, observableReturnValueHandler())
        requestMappingHandlerAdapter.setReturnValueHandlers(handlers)
    }

    @Bean
    HandlerMethodReturnValueHandler observableReturnValueHandler() {
        new ObservableReturnValueHandler()
    }
}
