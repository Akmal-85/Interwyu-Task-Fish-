package org.example.service;

import lombok.*;
import org.example.model.Fish;
import org.example.model.FishState;
import org.example.model.Gender;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.util.FishUtils.*;
import static org.example.util.PrintUtils.*;

@EqualsAndHashCode()
@Data
public class FishService {

    public static final List<Fish> fishList = Collections.synchronizedList(new ArrayList<>());
    public static final Lock lock = new ReentrantLock();
    private static final int ONE_YEAR_LIFE = 100;
    private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    public static final FishService fishService = new FishService();


    public Optional<Fish> getHappyFish(Fish currentFish) {
        for (Fish fish : fishList) {
            if (Objects.equals(fish.getHappyDay(), currentFish.getHappyDay()) && (currentFish.getGender() != fish.getGender())
                    && isMature(fish) && (fish.getFishState() != FishState.MARRIED)) {
                return Optional.of(fish);
            }
        }
        return Optional.empty();
    }


    public static void addFish() {
        int maleCount = fishesCount();
        int femaleCount = fishesCount();

        for (int i = 0; i < maleCount; i++) {
            Fish fish = new Fish(Gender.MALE, maxAge());
            fishList.add(fish);
            cachedThreadPool.execute(fish);
        }

        for (int i = 0; i < femaleCount; i++) {
            Fish fish = new Fish(Gender.FEMALE, maxAge());
            fishList.add(fish);

            cachedThreadPool.execute(fish);
        }
        System.out.printf("boshida akvariumda %d ta ogil va %d ta qiz bor \n", maleCount, femaleCount);
    }


    public static void addFish(Fish fish1, Fish fish2) {
        int maleCount = fishesCount();
        int femaleCount = fishesCount();


        for (int i = 0; i < maleCount; i++) {
            Fish fish = new Fish(Gender.MALE, maxAge());
            fishList.add(fish);
            cachedThreadPool.execute(fish);
        }

        for (int i = 0; i < femaleCount; i++) {
            Fish fish = new Fish(Gender.FEMALE, maxAge());
            fishList.add(fish);
            cachedThreadPool.execute(fish);
        }
        printMarriage(fish1, fish2, maleCount, femaleCount);
    }
}
