package main.java;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    public static void main(String[] args)  throws InterruptedException {
        RobotWars.Factory factory = new RobotWars.Factory();

        RobotWars.Faction world = new RobotWars.Faction("World", factory);
        RobotWars.Faction wednesday = new RobotWars.Faction("Wednesday", factory);

        Thread factoryThread = new Thread(() -> factory.produceParts());
        Thread worldThread = new Thread(world);
        Thread wednesdayThread = new Thread(wednesday);

        factoryThread.start();
        worldThread.start();
        wednesdayThread.start();

        worldThread.join();
        wednesdayThread.join();
        factoryThread.interrupt();

        System.out.println("\n--- Final Results ---");
        System.out.println(world.getName() + " robots: " + world.getRobots());
        System.out.println(wednesday.getName() + " robots: " + wednesday.getRobots());

        if (world.getRobots() > wednesday.getRobots()) {
            System.out.println("World wins!");
        } else if (wednesday.getRobots() > world.getRobots()) {
            System.out.println("Wednesday wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}