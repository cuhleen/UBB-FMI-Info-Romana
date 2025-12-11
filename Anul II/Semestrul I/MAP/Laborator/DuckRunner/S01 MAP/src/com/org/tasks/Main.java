

package com.org.tasks;

import com.org.tasks.container.Container;
import com.org.tasks.domain.*;
import com.org.tasks.factory.Strategy;
import com.org.tasks.repo.RepoRace;
import com.org.tasks.runner.StrategyTaskRunner;
import com.org.tasks.runner.TaskRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
        private static void readFromFile(RepoRace repo) throws IOException {
            Path filePath = Paths.get("E:\\002 Facultate\\Anul II\\Semestrul I\\MAP\\Laborator\\DuckRunner\\S01 MAP\\src\\com\\org\\tasks\\repo\\natatie.in");
            Scanner scanner = new Scanner(filePath);
            List<Integer> values = new ArrayList<>();
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    values.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }

            Duck[] ducks = new Duck[values.get(0)];
            Lane[] lanes = new Lane[values.get(1)];

//            for(int i = 0; i < values.get(0); i++){
//                int id = i;
//                int speed = values.get(i*2 + 2);
//                int res = values.get(i*2 + 3);
//                Duck duck = new Duck(id,  speed, res);
//                //System.out.println(id + " " + speed + " " +  res);
//                ducks[id] = duck;
//            }

            int idDuck = 0;

            for(int i = 2; i < values.get(0) + 2; i++){
                int id = idDuck++;
                int speed = values.get(i);
                int res = values.get(values.get(0) + i);
                Duck duck = new Duck(id, speed, res);
                //System.out.println(id + " " + speed + " " +  res);
                ducks[id] = duck;
            }

            int idLane = 0;

            for(int i = values.get(0) * 2; i < values.get(0) * 2 + values.get(1); i++){
                int id = idLane++;
                int len = values.get(i);
                Lane lane = new Lane(id, len);
                //System.out.println(id + " " + len);
                lanes[id] = lane;
            }
            BinarySearchStrategy strategy = new BinarySearchStrategy();
            Race race = new Race(ducks, lanes, strategy);
            repo.addRace(race);

        }

    public static void main(String[] args) throws IOException {
            RepoRace repo =  new RepoRace();
            readFromFile(repo);
            BinarySearchStrategy strategy = new BinarySearchStrategy();

            TaskRunner taskRunner = new StrategyTaskRunner(Strategy.LIFO);

            for(var race: repo.getRaces()){
                taskRunner.addTask(race);
            }

           taskRunner.executeAll();

    }
}
