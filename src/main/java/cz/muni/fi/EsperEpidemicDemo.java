package cz.muni.fi;

import cz.muni.fi.generator.DiseaseGenerator;


public class EsperEpidemicDemo
{
    public static void main( String[] args )
    {
        EpidemicMonitor monitor = new EpidemicMonitor();
        monitor.startProcessing(new DiseaseGenerator().generateTimeRounds(300));
    }
}
