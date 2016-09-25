package quantum.rx.web

import groovy.util.logging.Slf4j
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.context.request.async.WebAsyncUtils
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer
import rx.Observable

@Slf4j
class ObservableReturnValueHandler implements HandlerMethodReturnValueHandler {

    boolean supportsReturnType(MethodParameter returnType) {
        Class parameterType = returnType.getParameterType()
        return Observable.class.isAssignableFrom(parameterType)
    }

    void handleReturnValue(Object returnValue,
                                  MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        if(returnValue == null) {
            mavContainer.setRequestHandled(true)
            return
        }

        DeferredResult<Object> deferredResult = new DeferredResult<Object>()
        Observable observable = returnValue.toList()
        log.info 'CONVERTING OBSERVABLE TO DEFERREDRESULT'
        observable.subscribe({result -> deferredResult.setResult(result)}, {errors -> deferredResult.setErrorResult(errors)})

        WebAsyncUtils.getAsyncManager(webRequest).startDeferredResultProcessing(deferredResult, mavContainer)
    }
}
