package by.antonpaulavets.skynet;
import java.util.*;
import java.util.concurrent.*;

public class RobotWars {

    private static final int DAYS = 100;
    private static final int FACTORY_CAPACITY = 10;
    private static final int FACTION_CAPACITY = 5;

    private static final Random random = new Random();

    enum Part {
        HEAD, TORSO, HAND, FEET
    }

    static class Factory {
        private final BlockingQueue<Part> parts = new LinkedBlockingQueue<>();

        public void produceParts() {
            while (true) {
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                int partsToProduce = random.nextInt(FACTORY_CAPACITY) + 1;
                for (int i = 0; i < partsToProduce; i++) {
                    Part part = Part.values()[random.nextInt(Part.values().length)];
                    parts.add(part);
                    System.out.println("Factory produced: " + part);
                }

                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
            }
        }

        public List<Part> getParts(int count) {
            List<Part> takenParts = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Part part = parts.poll();
                if (part != null) {
                    takenParts.add(part);
                } else {
                    break;
                }
            }
            return takenParts;
        }
    }

    static class Faction implements Runnable {
        private final String name;
        private final Factory factory;
        private int heads = 0;
        private int torsos = 0;
        private int hands = 0;
        private int feet = 0;
        private int robots = 0;

        public Faction(String name, Factory factory) {
            this.name = name;
            this.factory = factory;
        }

        @Override
        public void run() {
            for (int day = 1; day <= DAYS; day++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                List<Part> collectedParts = factory.getParts(FACTION_CAPACITY);
                System.out.println(name + " collected parts: " + collectedParts);

                for (Part part : collectedParts) {
                    switch (part) {
                        case HEAD:
                            heads++;
                            break;
                        case TORSO:
                            torsos++;
                            break;
                        case HAND:
                            hands++;
                            break;
                        case FEET:
                            feet++;
                            break;
                    }
                }

                // Build Robots
                while (heads >= 1 && torsos >= 1 && hands >= 2 && feet >= 2) {
                    heads--;
                    torsos--;
                    hands -= 2;
                    feet -= 2;
                    robots++;
                }
                System.out.println(name + " has " + robots + " robots after day " + day);
            }
        }

        public int getRobots() {
            return robots;
        }

        public String getName() {
            return name;
        }
    }
}