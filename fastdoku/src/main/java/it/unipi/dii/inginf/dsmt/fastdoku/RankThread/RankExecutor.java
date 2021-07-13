package it.unipi.dii.inginf.dsmt.fastdoku.RankThread;

import it.unipi.dii.inginf.dsmt.fastdoku.persistence.LevelDBUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RankExecutor {
    public static void main(String[] args) throws Exception {

        Runnable rank = new Runnable() {
            @Override
            public void run() {


                //funzione hashmap classifica generale e scrive sul db
                LevelDBUser rankByPoints = LevelDBUser.getInstance();
                HashMap<String, Integer > rank = rankByPoints.getRanking(3, "points");
                String mapAsString = rank.keySet().stream()
                        .map(key -> key + "," + rank.get(key))
                        .collect(Collectors.joining(", "));
                 rankByPoints.writeRank(mapAsString,"points");

                //chiamare funzione che scrive sul db


                //funzione hashmap classifica giornaliera
                LevelDBUser rankByDailyPoints = LevelDBUser.getInstance();
                HashMap<String, Integer > rankDaily = rankByDailyPoints.getRanking(3,"dailypoints");
                 mapAsString = rank.keySet().stream()
                        .map(key -> key + "," + rankDaily.get(key))
                        .collect(Collectors.joining(", "));
                rankByPoints.writeRank(mapAsString,"dailypoints");




            }

        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(rank, 0, 24, TimeUnit.HOURS);
    }
}