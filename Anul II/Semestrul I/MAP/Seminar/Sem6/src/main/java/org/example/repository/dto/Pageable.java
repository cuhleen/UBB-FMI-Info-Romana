package org.example.repository.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Pageable {
    private final int pageNUmber;
    private final int pageSize;
}
