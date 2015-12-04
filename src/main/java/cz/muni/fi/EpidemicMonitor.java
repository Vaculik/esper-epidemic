package cz.muni.fi;

import com.espertech.esper.client.*;
import cz.muni.fi.event.DiseaseEvent;
import cz.muni.fi.event.NewDiseaseType;
import cz.muni.fi.generator.Generator;
import cz.muni.fi.generator.DiseaseGenerator;
import cz.muni.fi.statement.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by vaculik on 31.10.15.
 */
public class EpidemicMonitor {

    private static final Logger logger = LoggerFactory.getLogger(EpidemicMonitor.class);
    private static final long DEFAULT_DELAY = 200L;
    private EPServiceProvider serviceProvider;

    private ResultsListener epidemicResults;
    private ResultsListener mortalityResults;
    private ResultsListener newTypeResults;

    public EpidemicMonitor() {
        Configuration configuration = new Configuration();
        configuration.configure();

        serviceProvider = EPServiceProviderManager.getProvider(EpidemicMonitor.class.getName(), configuration);
    }


    public void startProcessing(List<EventTimeRound> timeRounds) {
        startProcessing(timeRounds, DEFAULT_DELAY);
    }

    public void startProcessing(List<EventTimeRound> timeRounds, long delay) {
        if (timeRounds == null) {
            String msg = "Parameter timeRounds is null.";
            logger.warn(msg);
            throw new NullPointerException(msg);
        }
        if (delay < 0) {
            String msg = "Parameter delay cannot be negative.";
            logger.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        initStatements();

        logger.debug("Create EPRuntime and start processing of events.");
        EPRuntime runtime = serviceProvider.getEPRuntime();
        for (int i = 0; i < timeRounds.size(); i++) {
            for (Object o : timeRounds.get(i)) {
                DiseaseEvent event = (DiseaseEvent) o;

                logger.debug(event.toString());
                runtime.sendEvent(event);
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.warn("Interrupted when was processing events.", e);
            }
        }
        logger.info("Number of EpidemicStatement results: " + epidemicResults.getNumOfResults());
        logger.info("Number of MortalityStatement results: " + mortalityResults.getNumOfResults());
        logger.info("Number of NewTypeStatement results: " + newTypeResults.getNumOfResults());
    }


    private void initStatements() {
        logger.debug("Create EpidemicStatement and appropriate listeners.");
        EpidemicStatement epidemicStatement = new EpidemicStatement(serviceProvider);
        epidemicResults = new ResultsListener();
        epidemicStatement.addListener(new EpidemicListener(epidemicResults));

        logger.debug("Create MortalityStatement and appropriate listeners.");
        MortalityStatement mortalityStatement = new MortalityStatement(serviceProvider);
        mortalityResults = new ResultsListener();
        mortalityStatement.addListener(new MortalityListener(mortalityResults));

        logger.debug("Create NewTypeStatement and appropriate listeners");
        NewTypeStatement newTypeStatement = new NewTypeStatement(serviceProvider);
        newTypeResults = new ResultsListener();
        newTypeStatement.addListener(new NewTypeListener(newTypeResults));
    }

    public ResultsListener getEpidemicResults() {
        return epidemicResults;
    }

    public ResultsListener getMortalityResults() {
        return mortalityResults;
    }

    public ResultsListener getNewTypeResults() {
        return  newTypeResults;
    }

    public void destroyServiceProvider() {
        logger.debug("Destroy EPServiceProvider.");
        serviceProvider.destroy();
    }
}
