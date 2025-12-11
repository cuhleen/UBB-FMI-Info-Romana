package com.org.tasks.domain;

import java.util.Objects;

public class Lane {
    private final int number;
    private final int distance;

    public Lane(int number, int distance) {
        this.number = number;
        this.distance = distance;
    }

    public int getNumber() {
        return number;
    }
    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Lane lane = (Lane) o;
        return number == lane.number && distance == lane.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, distance);
    }
}
