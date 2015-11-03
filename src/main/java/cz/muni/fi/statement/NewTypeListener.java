package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vaculik on 03/11/2015.
 */
public class NewTypeListener implements UpdateListener {
    private static final Logger logger = LoggerFactory.getLogger(NewTypeListener.class);
    private ResultsListener resultsListener;

    public NewTypeListener(ResultsListener listener) {
        resultsListener = listener;
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        logger.info("NEW DISEASE TYPE FOUND: Type=" + newEvents[0].get("type"));
        resultsListener.addResult(newEvents[0]);
    }
}
