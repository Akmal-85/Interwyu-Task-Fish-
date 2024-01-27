package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

import static org.example.service.FishService.*;
import static org.example.util.FishUtils.*;
import static org.example.util.PrintUtils.*;


@Getter
@Setter
public class Fish extends Thread {
    public static final List<Fish> fishList = Collections.synchronizedList(new ArrayList<>());
    private Gender gender;
    private int maxAge;
    private int birthAge;
    private FishState fishState;
    private int happyDay;

    public Fish(Gender gender, int maxAge) {
        this.fishState = FishState.BORN;
        this.birthAge = 0;
        this.gender = gender;
        this.maxAge = maxAge;
    }

    @SneakyThrows
    @Override
    public void run() {
        for (int i = 1; i <= maxAge; i++) {
            this.birthAge = i;
            Thread.sleep(200);

            if (isChild(this)) {
                this.fishState = FishState.CHILD;
            }

            lock.lock();
            if (isMature(this) && !isMarried(this)) {
                this.fishState = FishState.MATURE;
                this.happyDay = happyDay();

                Optional<Fish> optionalFish = fishService.getHappyFish(this);
                if (optionalFish.isPresent()) {
                    Fish fish = optionalFish.get();

                    this.fishState = FishState.MARRIED;
                    fish.fishState = FishState.MARRIED;

                    addFish(this, fish);
                }
            }
            lock.unlock();

            if (isAdult(this)) {
                this.fishState = FishState.ADULT;
            }

            if (isTimeToDie(this)) {
                this.fishState = FishState.DEAD;
                printDead(this);
                fishList.remove(this);
            }
        }
    }
}