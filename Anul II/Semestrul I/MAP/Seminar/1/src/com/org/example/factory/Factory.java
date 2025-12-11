package com.org.example.factory;

import com.org.example.container.Container;

public interface Factory {
    Container createContainer(Strategy strategy);
}
