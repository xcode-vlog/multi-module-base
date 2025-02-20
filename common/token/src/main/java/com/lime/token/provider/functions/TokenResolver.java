package com.lime.token.provider.functions;

import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Function;

public interface TokenResolver<T extends HttpServletRequest, R extends String> extends Function<T, R> {

}
