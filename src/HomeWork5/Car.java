package HomeWork5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable{
    private static int CARS_COUNT;
    private Race race;
    private int speed;

    public int getSpeed() {
        return speed;
    }

    private String name;

    public String getName() {
        return name;
    }

    private CyclicBarrier barrier;
    private static AtomicInteger aI;

    static {
        aI = new AtomicInteger(0);
    }

    public Car(Race race, int speed, CyclicBarrier barrier) {
        this.race = race;
        this.speed = speed;
        this.barrier = barrier;
        this.name = "Участник №" + CARS_COUNT;
        CARS_COUNT++;
    }

    @Override
    public void run() {
        System.out.println(this.name + " готовится");
        try {
            Thread.sleep(500 + (int)(Math.random() * 800));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.name + " готов");

        try {
            barrier.await();//готовность гонщиков к гонке
            barrier.await();//готовность гонщиков к началу гонки

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        if(aI.incrementAndGet()==1){
            System.out.println(name + " победил в гонке!!!");
        }

        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
