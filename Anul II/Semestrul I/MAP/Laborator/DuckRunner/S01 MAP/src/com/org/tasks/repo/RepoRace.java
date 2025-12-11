package com.org.tasks.repo;

import com.org.tasks.domain.Race;

public class RepoRace {
    private Race[] races =  new Race[100];
    private int raceSize = 0;

    public void addRace(Race race){
        races[raceSize++] = race;
    }

    public Race[] getRaces(){
        Race[] actualRaces = new Race[raceSize];
        for(int i=0;i<raceSize;i++){
            actualRaces[i] = races[i];
        }
        return actualRaces;
    }

}
