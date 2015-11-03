package cz.muni.fi.statement;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by Vaculik on 03/11/2015.
 */
public class MortalityListener implements UpdateListener {

    @Override
    public void update(EventBean[] newBeans, EventBean[] oldBeans) {
        System.out.println("MORTALITY avg=" + newBeans[0].get("avgMortality") );
    }
}
