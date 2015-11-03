package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vaculik on 03/11/2015.
 */
public class MortalityListener implements UpdateListener {
    private static final Logger logger = LoggerFactory.getLogger(MortalityListener.class);
    private ResultsListener resultsListener;

    public MortalityListener(ResultsListener listener) {
        resultsListener = listener;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        logger.info("HIGH MORTALITY: Rate=" + newEvents[0].get("avgMortality") + " %");
        resultsListener.addResult(newEvents[0]);
    }
}
