package com.org.tasks.domain;

public class BinarySearchStrategy implements OptimisationStrategy{
    @Override
    public OptimisationResult solve(Race race) {
        // găsim timpul maxim
        Duck[] ducks = race.getDucks();
        Lane[] lanes = race.getLanes();
        int ducksSize = race.getDucks().length;
        int lanesSize = race.getLanes().length;

        double timpMax = (double) (lanes[lanesSize - 1].getDistance() * 2) / ducks[0].getSpeed();

        for(int i = 0; i < ducksSize; i++)
            if((double) (lanes[lanesSize - 1].getDistance() * 2) / ducks[i].getSpeed() > timpMax)
                timpMax = (double) (lanes[lanesSize - 1].getDistance() * 2) /  ducks[i].getSpeed();

        //
        Duck[] duckRes = new Duck[lanesSize];
        duckRes = binarySearch(0, timpMax, ducks, lanes, timpMax, duckRes);

        double timpFinal = -1;

        for(int i = 0; i < lanesSize; i++){
//            if(duckRes[i] == null) {
//                throw new NullPointerException("Nu se poate");
//            }
            if(duckRes[i] != null)
                if(timpFinal < (double) (lanes[i].getDistance() * 2) / duckRes[i].getSpeed())
                    timpFinal = (double) (lanes[i].getDistance() * 2) / duckRes[i].getSpeed();
        }

        // return la ce buddy? Că doar nu dai print de aici
        return new OptimisationResult(duckRes, timpFinal);
    }

    private Duck[] binarySearch(double st, double dr, Duck[] ducks, Lane[] lanes, double lastFastestTime, Duck[] duckRes){

        if(-0.001 < st - dr && st - dr < 0.001){
            System.out.println("Ultimul timp gasit: " + lastFastestTime);
            return duckRes;
        }

        double timp = (st + dr) / 2;
        int ok = 0;

        int[] duckUsed =  new int[ducks.length];
        int[] laneUsed =  new int[lanes.length];

        for(int i = 0; i < lanes.length; i++){
            for(int j = 0; j < ducks.length; j++){
                if(timp > (double) (lanes[i].getDistance() * 2) / ducks[j].getSpeed()){
                    if(laneUsed[i] == 0 && duckUsed[j] == 0) {
                        laneUsed[i] = 1;
                        duckUsed[j] = 1;
                        duckRes[i] = ducks[j];
                    }
                }
            }
        }

        if(laneUsed[lanes.length - 1] == 1){
            ok = 1;
        }

        if(ok == 1) // se poate mai rapid
            return binarySearch(st, timp, ducks, lanes, timp, duckRes);
        else // nu se poate mai rapid
            return binarySearch(timp, dr, ducks, lanes, lastFastestTime, duckRes);

    }
}
