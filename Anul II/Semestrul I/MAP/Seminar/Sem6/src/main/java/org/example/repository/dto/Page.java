package org.example.repository.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Page<E> {
    private final Iterable<E> elements;
    private final int totalElements;

}
