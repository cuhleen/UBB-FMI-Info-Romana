package com.org.tasks.runner;

import utils.Constants;

import java.io.IOException;
import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner {

    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() throws IOException {
        super.executeOneTask();
        System.out.println("Task ran at:"+ LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER));
    }
}
