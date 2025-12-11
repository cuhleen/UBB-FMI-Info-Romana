package com.org.tasks.factory;

import com.org.tasks.container.Container;

public interface Factory {
    Container createContainer(Strategy strategy);
}
