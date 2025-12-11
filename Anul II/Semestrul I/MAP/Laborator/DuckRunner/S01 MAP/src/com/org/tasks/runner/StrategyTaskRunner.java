package com.org.tasks.runner;

import com.org.tasks.container.Container;
import com.org.tasks.domain.Task;
import com.org.tasks.factory.Strategy;
import com.org.tasks.factory.TaskContainerFactory;

import java.io.IOException;

public class StrategyTaskRunner implements TaskRunner {

    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask() throws IOException {
        if(!container.isEmpty()) {
            container.remove().execute();
        }
    }

    @Override
    public void executeAll() throws IOException {
        while(!container.isEmpty()) {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
