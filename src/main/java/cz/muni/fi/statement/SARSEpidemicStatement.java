package cz.muni.fi.statement;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.generator.Generator;
import cz.muni.fi.generator.SARSGenerator;

/**
 * Created by Vaculik on 02/11/2015.
 */
public class SARSEpidemicStatement {
    private EPStatement statement;

    public SARSEpidemicStatement(EPServiceProvider serviceProvider) {
        String expr = "select count(*) as count, lst.locationX as locationX, lst.locationY as locationY " +
                      "from SARS(exists (select * " +
                                "from SARS.std:lastevent() as l " +
                                "where Math.abs(sr.locationX - l.locationX) <" + Generator.EPIDEMIC_RANGE + " * 2 and " +
                                      "Math.abs(sr.locationY - l.locationY) <" + Generator.EPIDEMIC_RANGE + " * 2))" +
                            ".win:time(1 sec) as sr, " +
                            "SARS.std:lastevent() as lst " +
                      "having count(*) >= " + SARSGenerator.EPIDEMIC_INDICATION_BOUND;

//        String expr = "select count(*) as count, l.locationX as locationX, l.locationY as locationY " +
//                "from SARS.std:lastevent() as l, SARS.win:time(1 sec) as s " +
//                "where Math.abs(s.locationX - l.locationX) <" + Generator.EPIDEMIC_RANGE + " * 2 and " +
//                      "Math.abs(s.locationY - l.locationY) <" + Generator.EPIDEMIC_RANGE + " * 2 " +
//                "having count(*) >= 2";

        statement = serviceProvider.getEPAdministrator().createEPL(expr);
    }

    public void addListener(UpdateListener listener) {
        statement.addListener(listener);
    }
}
