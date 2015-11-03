package cz.muni.fi;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.util.*;

/**
 * Created by vaculik on 3.11.15.
 */
public class EventTimeRound implements Iterable<Object> {
    private List<Object> timeRound = new LinkedList<>();

    public void addEvent(Object event) {
        timeRound.add(event);
    }

    public void addAllEvents(Collection<Object> events) {
        timeRound.addAll(events);
    }

    @Override
    public Iterator<Object> iterator() {
        return timeRound.iterator();
    }
}
