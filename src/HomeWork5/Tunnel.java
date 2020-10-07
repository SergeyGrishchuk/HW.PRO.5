package HomeWork5;


import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore semaphore;


    public Tunnel (){

        semaphore = new Semaphore(MainClass.CARS_COUNT / 2);
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car car) {

        try {
            if (!semaphore.tryAcquire()) {
                System.out.println(car.getName() + " ждет начала этапа: " + description);
                semaphore.acquire();
            }

            System.out.println(car.getName() + " начал этап: " + description);
            Thread.sleep(length / car.getSpeed() * 1000);
        }catch (InterruptedException e) {
                e.printStackTrace();
        } finally {
            System.out.println(car.getName() + " закончил этап: " + description);
            semaphore.release();
        }
    }
}

