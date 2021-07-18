package it.unipi.dii.inginf.dsmt.fastdoku.persistence;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class LevelDBUserTest {
    private final int NUM_THREAD = 10;
    // Test the getInstance
    @Test
    void getInstance() throws ExecutionException, InterruptedException {

        Callable<LevelDBUser> callable = () -> LevelDBUser.getInstance();

        ExecutorService executor = Executors.newCachedThreadPool();

        List<Future<LevelDBUser>> futureList = new ArrayList<>();

        for (int i = 0; i < NUM_THREAD; i++) {
            futureList.add(executor.submit(callable));
        }

        List<LevelDBUser> levelDBUsers = new ArrayList<>();

        for (int i=0; i<NUM_THREAD; i++) {
            levelDBUsers.add(futureList.get(i).get());
        }

        levelDBUsers.stream().parallel()
                .forEach(levelDBUser -> assertSame(levelDBUser, LevelDBUser.getInstance()));

        executor.shutdown();
    }
}
