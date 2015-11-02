package cz.muni.fi.generator;

import cz.muni.fi.Event.Event;

import java.util.List;

/**
 * Created by vaculik on 31.10.15.
 */
public interface Generator {

    int MAX_COORD_X = 1000;
    int MAX_COORD_Y = 1000;
    int EPIDEMIC_RANGE = 50;
    double EPIDEMIC_START_CHANCE = 0.05;

    List<Event> generateNextRound();
}
