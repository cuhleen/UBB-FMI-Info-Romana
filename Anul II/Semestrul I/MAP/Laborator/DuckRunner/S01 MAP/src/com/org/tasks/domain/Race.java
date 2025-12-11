package com.org.tasks.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Race extends Task {
    private Duck[] ducks;
    private Lane[] lanes;

    public Race(Duck[] ducks, Lane[] lanes, OptimisationStrategy strategy) {
        super(ducks, lanes, strategy);
        this.ducks = ducks;
        this.lanes = lanes;
    }

    public Duck[] getDucks() {
        return ducks;
    }

    public Lane[] getLanes() {
        return lanes;
    }

    private void sortDucks() {
        for (int i = 0; i < ducks.length - 1; i++) {
            for (int j = i + 1; j < ducks.length; j++) {
                if (ducks[i].getResistance() > ducks[j].getResistance()) {
                    Duck temp = ducks[i];
                    ducks[i] = ducks[j];
                    ducks[j] = temp;
                } else if (ducks[i].getResistance() == ducks[j].getResistance() && ducks[i].getSpeed() > ducks[j].getSpeed()) {
                    Duck temp = ducks[i];
                    ducks[i] = ducks[j];
                    ducks[j] = temp;
                }
            }
        }
    }

    @Override
    public void execute() throws IOException {
        sortDucks();
        Race race =  new Race(ducks, lanes, strategy);
        OptimisationResult result = strategy.solve(race);
        BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\002 Facultate\\Anul II\\Semestrul I\\MAP\\Laborator\\DuckRunner\\S01 MAP\\src\\com\\org\\tasks\\repo\\natatie.out"));
        writer.write(String.valueOf(result));

        writer.close();
    }
}
