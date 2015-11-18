package cz.muni.fi;

import cz.muni.fi.event.DiseaseEvent;
import cz.muni.fi.generator.Generator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by vaculik on 3.11.15.
 */
public class EpidemicMonitorTest {
    private EpidemicMonitor monitor;
    private List<EventTimeRound> eventRounds;
    private EventTimeRound round;

    @Before
    public void init() {
        monitor = new EpidemicMonitor();
        eventRounds = new LinkedList<>();
        round = new EventTimeRound();
        eventRounds.add(round);
    }

    @After
    public void close() {
        monitor.destroyServiceProvider();
    }

    @Test
    public void noEpidemic() {
        round.addEvent(createDefaultEvent(100, 100));
        round.addEvent(createDefaultEvent(100 + Generator.EPIDEMIC_RANGE * 3, 100));
        round.addEvent(createDefaultEvent(100, 100 + Generator.EPIDEMIC_RANGE * 3));
        round.addEvent(createDefaultEvent(100 + Generator.EPIDEMIC_RANGE * 6, 100));
        round.addEvent(createDefaultEvent(100, 100 + Generator.EPIDEMIC_RANGE * 6));
        monitor.startProcessing(eventRounds, 0);

        assertEquals(0, monitor.getEpidemicResults().getNumOfResults());
    }

    @Test(expected = NullPointerException.class)
    public void nullTimeRoundsParameter() {
        monitor.startProcessing(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalitDelayParameter() {
        monitor.startProcessing(eventRounds, -1);
    }

    @Test
    public void epidemicOccurence() {
        for (int i = 0; i < 5; i++) {
            round.addEvent(createDefaultEvent());
        }
        monitor.startProcessing(eventRounds, 0);

        assertEquals(2, monitor.getEpidemicResults().getNumOfResults());
    }

    @Test
    public void noHighMortality() {
        for (int i = 0; i < 10; i++) {
            round.addEvent(createDefaultEvent());
        }
        monitor.startProcessing(eventRounds, 0);

        assertEquals(0, monitor.getMortalityResults().getNumOfResults());
    }

    @Test
    public void highMortalityOccurence() {
        DiseaseEvent event;
        for (int i = 0; i < 10; i++) {
            event = createDefaultEvent();
            event.setDeath(true);
            round.addEvent(event);
        }
        monitor.startProcessing(eventRounds, 0);

        assertTrue(monitor.getMortalityResults().getNumOfResults() > 0);
    }

    @Test
    public void oneType(){
        round.addEvent(createDefaultEvent());
        monitor.startProcessing(eventRounds, 0);

        assertEquals(1, monitor.getNewTypeResults().getNumOfResults());
    }

    @Test
    public void threeTypes() {
        DiseaseEvent event;
        round.addEvent(createDefaultEvent());
        event = createDefaultEvent();
        event.setType("B");
        round.addEvent(event);
        event = createDefaultEvent();
        event.setType("C");
        round.addEvent(event);
        monitor.startProcessing(eventRounds, 0);

        assertEquals(3, monitor.getNewTypeResults().getNumOfResults());
    }


    private DiseaseEvent createDefaultEvent() {
        return createDefaultEvent(100, 100);
    }

    private DiseaseEvent createDefaultEvent(int x, int y) {
        DiseaseEvent event = new DiseaseEvent();
        event.setLocationX(x);
        event.setLocationY(y);
        event.setDeath(false);
        event.setType("A");
        return event;
    }
}
