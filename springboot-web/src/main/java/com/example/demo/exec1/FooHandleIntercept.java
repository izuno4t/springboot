package com.example.demo.exec1;

import com.example.demo.FooIdResolver;
import com.example.demo.FooResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class FooHandleIntercept implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(FooHandleIntercept.class);

    private final Map<String, FooIdResolver> fooIdResolverMap;

    public FooHandleIntercept(List<FooIdResolver> fooIdResolverList) {
        var map = new HashMap<String, FooIdResolver>();
        fooIdResolverList.forEach(it -> map.put(it.getClass().getSimpleName(), it));
        fooIdResolverMap = Collections.unmodifiableMap(map);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Method targetMethod = handlerMethod.getMethod();
//        Object targetObject = handlerMethod.getBean();
//        log.info("targetMethod.getName() = {}", targetMethod.getName());
//        log.info("targetObject = {}", targetObject);
//        Collections.list(request.getAttributeNames()).forEach(it -> log.info("attribute: {}", it));
//        @SuppressWarnings("unchecked")
//        var uriTemplateVariables = (Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
//        var fooId = uriTemplateVariables.get("fooId");
        var handlerMethod = (HandlerMethod) handler;
        FooResolver resolverAnnotation = handlerMethod.getMethodAnnotation(FooResolver.class);
        if (Objects.isNull(resolverAnnotation)) {
            throw new RuntimeException("わかんない");
        }
        var resolverName = resolverAnnotation.name();
        var resolver = fooIdResolverMap.get(resolverName);
        var fooId = resolver.resolveFooId(request);
        log.info("fooId = {}", fooId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
