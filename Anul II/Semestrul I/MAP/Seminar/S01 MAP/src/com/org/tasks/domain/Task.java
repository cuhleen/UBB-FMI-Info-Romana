package com.org.tasks.domain;

import java.util.Objects;

public abstract class Task {
    private String descriere;
    private String taskId;

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Task(String taskId, String descriere) {
        this.taskId = taskId;
        this.descriere = descriere;
    }

    public abstract void execute();

    @Override
    public String toString() {
        return "Task{" +
                "descriere='" + descriere + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(descriere, task.descriere) && Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}