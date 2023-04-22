package com.example.demo.handlerinterceptor;

import com.example.demo.FooIdResolver;
import com.example.demo.domain.FooId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class FooControllerPathParameterNameResolver implements FooIdResolver {
    @Override
    public Optional<FooId> resolveFooId(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        var uriTemplateVariables = (Map<String, String>) request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
        var fooId = uriTemplateVariables.get("fooId");
        if (Objects.nonNull(fooId)) {
            return Optional.of(new FooId(fooId));
        }
        return Optional.empty();
    }
}
