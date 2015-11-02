package cz.muni.fi;

import cz.muni.fi.event.HIVEvent;
import cz.muni.fi.generator.Generator;
import cz.muni.fi.generator.HIVGenerator;

/**
 * Hello world!
 *
 */
public class EsperEpidemicDemo
{
    public static void main( String[] args )
    {
        EpidemicMonitor monitor = new EpidemicMonitor();
        monitor.start(100);
    }
}
