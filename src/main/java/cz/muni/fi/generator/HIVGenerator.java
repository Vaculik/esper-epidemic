package cz.muni.fi.generator;

import cz.muni.fi.event.Event;
import cz.muni.fi.event.HIVEvent;
import cz.muni.fi.event.HIVStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by vaculik on 31.10.15.
 */
public class HIVGenerator implements Generator {

    private static final Logger logger = LoggerFactory.getLogger(HIVGenerator.class);
    private static final Random random = new Random(System.currentTimeMillis());
    private Epidemic epidemic = null;

    public List<Event> generateNextRound() {
        List<Event> events = new LinkedList<>();

        if (random.nextDouble() < 0.3) {
            HIVEvent event = createHIVEvent();
            if (epidemic == null && random.nextDouble() < EPIDEMIC_START_CHANCE) {
                epidemic = new Epidemic();
            }
            events.add(event);
        }

        if (epidemic != null) {
            events.addAll(epidemic.generateHIV());
            if (epidemic.isFinished()) {
                epidemic = null;
            }
        }

        return events;
    }

    private HIVEvent createHIVEvent() {
        HIVEvent event = new HIVEvent();
        event.setLocationX(random.nextInt(MAX_COORD_X));
        event.setLocationY(random.nextInt(MAX_COORD_Y));
        event.setStage(getRandomStage());
        return event;
    }

    private HIVStage getRandomStage() {
        HIVStage stage;
        double randomNum = random.nextDouble();
        if (randomNum < 0.1) {
            stage = HIVStage.AIDS;
        } else if (randomNum < 0.3) {
            stage = HIVStage.CHRONIC_INFECTION;
        } else {
            stage = HIVStage.ACUTE_INFECTION;
        }
        return stage;
    }


    private class Epidemic {
        private static final double EPIDEMIC_END_RATE = 0.1;
        private int progress = 1;
        private boolean finished = false;

        public boolean isFinished() {
            return finished;
        }

        public List<Event> generateHIV() {
            if (finished) {
                String msg = "Epidemic is over, cannot generate another SARS events.";
                logger.warn(msg);
                throw new IllegalStateException(msg);
            }

            List<Event> events = new LinkedList<>();
            for (int i = 0; i < progress; i++) {
                events.add(createHIVEvent());
            }
            if (progress < 8) {
                progress += random.nextInt(2);
                finished = random.nextDouble() < EPIDEMIC_END_RATE;
            } else {
                finished = true;
            }

            return events;
        }
    }
}
