package cz.muni.fi;

import com.espertech.esper.client.*;
import cz.muni.fi.event.Event;
import cz.muni.fi.event.HIVEvent;
import cz.muni.fi.event.DiseaseEvent;
import cz.muni.fi.generator.Generator;
import cz.muni.fi.generator.HIVGenerator;
import cz.muni.fi.generator.DiseaseGenerator;
import cz.muni.fi.statement.EpidemicListener;
import cz.muni.fi.statement.EpidemicStatement;
import cz.muni.fi.statement.MortalityListener;
import cz.muni.fi.statement.MortalityStatement;
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
    private final Generator sarsGenerator = new DiseaseGenerator();

    public EpidemicMonitor() {
        logger.debug("Create and configure EPServiceProvider.");
        Configuration config = new Configuration();
        config.addEventType("HIV", HIVEvent.class);
        config.addEventType("SARS", DiseaseEvent.class);
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
        if (delay <= 0) {
            String msg = "Parameter delay must be positive.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        initStatements(delay);

        logger.debug("Create EPRuntime and start processing of events.");
        EPRuntime runtime = serviceProvider.getEPRuntime();
        for (int i = 0; i < numOfRounds; i++) {
            processEvents(hivGenerator, runtime, HIVEvent.class);
            processEvents(sarsGenerator, runtime, DiseaseEvent.class);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.warn("Interrupted when was processing events.", e);
            }
        }
    }

    private <T extends Event> void processEvents(Generator generator, EPRuntime runtime, Class<T> c) {
        for (Object o : generator.generateNextRound()) {
            T event = c.cast(o);

            logger.debug(event.toString());
            runtime.sendEvent(event);
        }
    }

    private void initStatements(long delay) {
        logger.debug("Create EpidemicStatement and add appropriate listeners.");
        EpidemicStatement epidemicStatement = new EpidemicStatement(serviceProvider, delay);
        epidemicStatement.addListener(new EpidemicListener());

        logger.debug("Create MortalityStatement and add appropriate listeners.");
        MortalityStatement mortalityStatement = new MortalityStatement(serviceProvider);
        mortalityStatement.addListener(new MortalityListener());
    }

    public void closeServiceProvider() {
        logger.debug("Destroy EPServiceProvider.");
        serviceProvider.destroy();
    }
}
