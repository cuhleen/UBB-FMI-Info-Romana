package com.org.example.factory;

import com.org.example.container.Container;
import com.org.example.container.QueueContainer;
import com.org.example.container.StackContainer;

public class TaskContainerFactory implements Factory {
    @Override
    public Container createContainer(Strategy strategy) {
        return switch(strategy){
            case LIFO -> new StackContainer();
            case FIFO -> new QueueContainer();
        };
    }

    private TaskContainerFactory(){}
    private static TaskContainerFactory instance;
    public static TaskContainerFactory getInstance(){
        if(instance == null)
            instance = new TaskContainerFactory();

        return instance;
    }
}
