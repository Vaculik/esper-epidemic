package cz.muni.fi;

import cz.muni.fi.Event.HIVEvent;
import cz.muni.fi.Event.SARSEvent;
import cz.muni.fi.generator.Generator;
import cz.muni.fi.generator.HIVGenerator;
import cz.muni.fi.generator.SARSGenerator;

/**
 * Hello world!
 *
 */
public class EsperEpidemicDemo
{
    public static void main( String[] args )
    {
        Generator gen = new HIVGenerator();

        for (int i = 0; i < 10000; i++) {
            System.out.println("ROUND " + i);
            for (Object o : gen.generateNextRound()) {
                HIVEvent e = (HIVEvent) o;
                System.out.println(e);
            }
        }
    }
}
