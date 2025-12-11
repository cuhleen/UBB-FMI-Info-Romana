package com.org.tasks.domain;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public abstract class Task {
    private Duck[] ducks;
    private Lane[] lanes;
    OptimisationStrategy strategy;

    public Task(Duck[] ducks, Lane[] lanes,  OptimisationStrategy strategy){
        this.ducks = ducks;
        this.lanes = lanes;
        this.strategy = strategy;
    }

    public abstract void execute() throws IOException;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.deepEquals(ducks, task.ducks) && Objects.deepEquals(lanes, task.lanes) && Objects.equals(strategy, task.strategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(ducks), Arrays.hashCode(lanes), strategy);
    }
}