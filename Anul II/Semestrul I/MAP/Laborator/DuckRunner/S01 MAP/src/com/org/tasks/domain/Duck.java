package com.org.tasks.domain;

import java.util.Objects;

public class Duck {
    private final int duckId;
    private final int speed;
    private final int resistance;

    public Duck(int duckId, int speed, int resistance) {
        this.duckId = duckId;
        this.speed = speed;
        this.resistance = resistance;
    }

    public int getDuckId() {
        return duckId;
    }

    public int getSpeed() {
        return speed;
    }

    public int getResistance() {
        return resistance;
    }

    @Override
    public String toString() {
        return "Duck{" +
                "duckId=" + duckId +
                ", speed=" + speed +
                ", resistance=" + resistance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Duck duck = (Duck) o;
        return duckId == duck.duckId && speed == duck.speed && resistance == duck.resistance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(duckId, speed, resistance);
    }
}
