package com.example.demo;

import com.example.demo.domain.FooId;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface FooIdResolver {

    Optional<FooId> resolveFooId(HttpServletRequest request);
}
