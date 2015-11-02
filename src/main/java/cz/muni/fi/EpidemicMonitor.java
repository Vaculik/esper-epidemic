package cz.muni.fi;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import cz.muni.fi.Event.Event;
import cz.muni.fi.Event.HIVEvent;
import cz.muni.fi.Event.SARSEvent;
import cz.muni.fi.generator.Generator;
import cz.muni.fi.generator.HIVGenerator;
import cz.muni.fi.generator.SARSGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by vaculik on 31.10.15.
 */
public class EpidemicMonitor {

    private static final Logger logger = LoggerFactory.getLogger(EpidemicMonitor.class);
    private static final long DEFAULT_DELAY = 200L;
    private EPServiceProvider serviceProvider;
    private final Generator hivGenerator = new HIVGenerator();
    private final Generator sarsGenerator = new SARSGenerator();

    public EpidemicMonitor() {
        logger.debug("Create and configure EPServiceProvider.");
        Configuration config = new Configuration();
        config.addEventType("HIV", HIVEvent.class);
        config.addEventType("SARS", SARSEvent.class);
        serviceProvider = EPServiceProviderManager.getProvider(EpidemicMonitor.class.getName(), config);


    }


    public void start(int numOfRounds) {
        start(numOfRounds, DEFAULT_DELAY);
    }

    public void start(int numOfRounds, long delay) {
        if (numOfRounds <= 0) {
            String msg = "Parameter numOfRounds must be positive.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        logger.debug("Create EPRuntime and start processing of events.");
        EPRuntime runtime = serviceProvider.getEPRuntime();
        for (int i = 0; i < numOfRounds; i++) {
            processEvents(hivGenerator, delay, runtime, HIVEvent.class);
            processEvents(sarsGenerator, delay, runtime, SARSEvent.class);
        }
    }

    private <T extends Event> void processEvents(Generator generator, long delay, EPRuntime runtime, Class<T> c) {
        for (Object o : generator.generateNextRound()) {
            T event = c.cast(o);

            logger.debug(event.toString());
            runtime.sendEvent(event);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.warn("Interrupted when was processing events", e);
            }
        }
    }


    public void closeServiceProvider() {
        logger.debug("Destroy EPServiceProvider.");
        serviceProvider.destroy();
    }
}
