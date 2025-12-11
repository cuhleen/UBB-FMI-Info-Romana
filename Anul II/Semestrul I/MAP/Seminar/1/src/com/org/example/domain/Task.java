package com.org.example.domain;

import java.util.Objects;

public abstract class Task{
    private String taskID;
    private String descriere;

    public Task(String taskID, String descriere) {
        this.taskID = taskID;
        this.descriere = descriere;
    }

    public String getTaskID(){
        return taskID;
    }

    public String getDescriere(){
        return descriere;
    }

    public void setTaskID(String taskID){
        this.taskID = taskID;
    }

    public void setDescriere(String descriere){
        this.descriere = descriere;
    }

    public abstract void execute();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskID, task.taskID) && Objects.equals(descriere, task.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskID, descriere);
    }

    @Override
    public String toString() {
        return "Task{" + "TaskID='" + taskID + '\'' + ", Descriere='" + descriere + '\'' + '}';
    }
}