package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.EpidemicMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vaculik on 02/11/2015.
 */
public class EpidemicListener implements UpdateListener {
    private static final Logger logger = LoggerFactory.getLogger(EpidemicListener.class);
    private ResultsListener resultsListener;

    public EpidemicListener(ResultsListener listener) {
        resultsListener = listener;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        logger.info("EPIDEMIC ALLERT: Count=" + newEvents[0].get("count") + ", Location=[" +
                newEvents[0].get("locationX") + "," + newEvents[0].get("locationY") + "]");
        resultsListener.addResult(newEvents[0]);
    }
}
