package quantum.rx.web

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.context.request.async.WebAsyncUtils
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer
import rx.Observable

@Slf4j
class ObservableReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Value('${async.response.timeout}')
    private int responseTimeout

    boolean supportsReturnType(MethodParameter returnType) {
        Class parameterType = returnType.parameterType
        Observable.class.isAssignableFrom(parameterType)
    }

    void handleReturnValue(Object returnValue,
                                  MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        if(returnValue == null) {
            mavContainer.requestHandled = true
            return
        }

        int timeout = webRequest.getParameter('timeout')?.toInteger()?:responseTimeout

        DeferredResult deferredResult = new DeferredResult(timeout)

        Observable observable = returnValue

        log.info "Converting Observable to Springs DeferredResult with timeout of $timeout"

        observable.toList().subscribe({result ->
            if(deferredResult.setOrExpired) {
                log.error('Response timeout')
            } else {
                deferredResult.setResult(result)
            }}, {errors ->
            if(deferredResult.setOrExpired) {
                log.error('Response timeout')
            } else {
                deferredResult.setErrorResult(errors)
            }})

        WebAsyncUtils.getAsyncManager(webRequest).startDeferredResultProcessing(deferredResult, mavContainer)
    }
}
