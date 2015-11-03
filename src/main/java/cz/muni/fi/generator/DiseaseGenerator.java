package cz.muni.fi.generator;

import cz.muni.fi.event.DiseaseEvent;
import cz.muni.fi.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vaculik on 31.10.15.
 */
public class DiseaseGenerator implements Generator {

    private static final Logger logger = LoggerFactory.getLogger(DiseaseGenerator.class);
    private static final Random random = new Random(System.currentTimeMillis());
    public static final double MORTALITY = 0.07;
    private Epidemic epidemic = null;

    private final String typePrefix = "TYPE_";
    private final char baseType = 'A';
    private int numOfTypes = 1;
    private static final double NEW_TYPE_CHANCE = 0.02;

    public List<Event> generateNextRound() {
        List<Event> events = new LinkedList<>();

        if (random.nextDouble() < 0.3) {
            DiseaseEvent event = createDiseaseEvent();
            if (epidemic == null && random.nextDouble() < EPIDEMIC_START_CHANCE) {
                epidemic = new Epidemic(event.getLocationX(), event.getLocationY());
            }
            events.add(event);
        }

        if (epidemic != null) {
            events.addAll(epidemic.generateDiseases());
            if (epidemic.isFinished()) {
                epidemic = null;
            }
        }

        return events;
    }

    private DiseaseEvent createDiseaseEvent() {
        DiseaseEvent event = new DiseaseEvent();
        event.setLocationX(random.nextInt(MAX_COORD_X));
        event.setLocationY(random.nextInt(MAX_COORD_Y));
        event.setDeath(random.nextDouble() < MORTALITY);
        event.setType(getRandomType());
        return event;
    }

    private DiseaseEvent createDiseaseEvent(int x, int y) {
        DiseaseEvent event = new DiseaseEvent();

        int locationX = random.nextInt(2 * EPIDEMIC_RANGE) + (x - EPIDEMIC_RANGE);
        if (locationX < 0) {
            locationX = 0;
        } else if (locationX > MAX_COORD_X) {
            locationX = MAX_COORD_X;
        }
        event.setLocationX(locationX);

        int locationY = random.nextInt(2 * EPIDEMIC_RANGE) + (y - EPIDEMIC_RANGE);
        if (locationY < 0) {
            locationY = 0;
        } else if (locationY > MAX_COORD_Y) {
            locationY = MAX_COORD_Y;
        }
        event.setLocationY(locationY);

        event.setDeath(random.nextDouble() < MORTALITY);
        event.setType(getRandomType());

        return event;
    }

    private String getRandomType() {
        int baseTypeShift = random.nextInt(numOfTypes);
        if (numOfTypes <= ('Z' - 'A') && random.nextDouble() < NEW_TYPE_CHANCE) {
            numOfTypes++;
        }
        return typePrefix + (char) (baseType + baseTypeShift);
    }

    private class Epidemic {
        private static final double EPIDEMIC_END_RATE = 0.2;
        private final int locationX;
        private final int locationY;
        private boolean finished = false;

        public Epidemic(int x, int y) {
            locationX = x;
            locationY = y;
        }

        public boolean isFinished() {
            return finished;
        }

        public List<Event> generateDiseases() {
            if (finished) {
                String msg = "Epidemic is over, cannot generate another disease events.";
                logger.warn(msg);
                throw new IllegalStateException(msg);
            }

            List<Event> events = new LinkedList<>();
            int numOfNewEvents = random.nextInt(3) + 1;
            for (int i = 0; i < numOfNewEvents; i++) {
                events.add(createDiseaseEvent(locationX, locationY));
            }
            finished = random.nextDouble() < EPIDEMIC_END_RATE;
            return events;
        }
    }
}
