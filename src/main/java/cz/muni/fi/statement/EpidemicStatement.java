package cz.muni.fi.statement;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.generator.Generator;

/**
 * Created by Vaculik on 02/11/2015.
 */
public class EpidemicStatement {
    public static final int EPIDEMIC_INDICATION_BOUND = 4;
    private EPStatement statement;

    public EpidemicStatement(EPServiceProvider serviceProvider) {
//        String expr = "select count(epid) as count, lst.locationX as locationX, lst.locationY as locationY " +
//                      "from Disease(exists (select * " +
//                                "from Disease.std:lastevent() as l " +
//                                "where Math.abs(dis.locationX - l.locationX) <" + Generator.EPIDEMIC_RANGE + " * 2 and " +
//                                      "Math.abs(dis.locationY - l.locationY) <" + Generator.EPIDEMIC_RANGE + " * 2))" +
//                            ".win:time(1 sec) as dis, " +
//                            "Disease.std:lastevent() as lst " +
//                      "having count(epid) >= " + EPIDEMIC_INDICATION_BOUND;


        String expr = "select count(*) as count, lst.locationX as locationX, lst.locationY as locationY " +
                "from Disease.std:lastevent() as lst, Disease.win:length(10) as dis " +
                "where Math.abs(dis.locationX - lst.locationX) <" + Generator.EPIDEMIC_RANGE + " * 2 and " +
                      "Math.abs(dis.locationY - lst.locationY) <" + Generator.EPIDEMIC_RANGE + " * 2 " +
                "having count(*) >= " + EPIDEMIC_INDICATION_BOUND;


        statement = serviceProvider.getEPAdministrator().createEPL(expr, "EpidemicStatement");
    }

    public void addListener(UpdateListener listener) {
        statement.addListener(listener);
    }
}
