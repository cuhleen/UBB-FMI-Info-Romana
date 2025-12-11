package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User extends Entity<Integer> {
    private String username;
    private LocalDateTime createdAt;
    private int credits;
}
