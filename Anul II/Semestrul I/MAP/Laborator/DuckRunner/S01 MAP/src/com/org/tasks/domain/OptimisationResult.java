package com.org.tasks.domain;

public class OptimisationResult {
    private final Duck[] ducks;
    private final double time;
    public OptimisationResult(Duck[] ducks, double time) {
        this.ducks = ducks;
        this.time = time;
    }

    public Duck[] getDucks() {
        return ducks;
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {

        String ducksString = "";

        for(Duck duck : ducks){
            if(duck != null)
                ducksString += duck.toString() + '\n';
        }

        return "OptimisationResult{ \n" +
                ducksString +
                "time=" + time +
                '}';
    }
}
