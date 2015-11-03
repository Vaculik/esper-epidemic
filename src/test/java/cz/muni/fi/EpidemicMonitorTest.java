package cz.muni.fi;

import cz.muni.fi.event.DiseaseEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vaculik on 3.11.15.
 */
public class EpidemicMonitorTest {
    private EpidemicMonitor monitor;

    @Before
    public void init() {
        monitor = new EpidemicMonitor();
    }

    @After
    public void close() {
        monitor.destroyServiceProvider();
    }

    @Test
    public void noEpidemic() {

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
