package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by Vaculik on 02/11/2015.
 */
public class SARSEpidemicListener implements UpdateListener {

    @Override
    public void update(EventBean[] newBeans, EventBean[] oldBeans) {
        System.out.println("BINGO Count=" + newBeans[0].get("count") + " X=" + newBeans[0].get("locationX") +
            " Y=" + newBeans[0].get("locationY"));
    }
}
