package com.byritium.compose.directive;

public interface Directive<T> {
    void execute(T t);
}
