package cz.muni.fi.statement;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import cz.muni.fi.generator.DiseaseGenerator;

/**
 * Created by Vaculik on 03/11/2015.
 */
public class MortalityStatement {
    private EPStatement statement;

    public MortalityStatement(EPServiceProvider serviceProvider) {
        String expr = "expression winLength alias for {20}" +
                      "select count(*) / winLength * 100 as avgMortality " +
                      "from Disease.win:length(winLength) " +
                      "where death=true " +
                      "having death=true AND (count(*) / winLength) > (0.01 + " + DiseaseGenerator.MORTALITY + ")";
        statement = serviceProvider.getEPAdministrator().createEPL(expr, "MortalityStatement");
    }

    public void addListener(UpdateListener listener) {
        statement.addListener(listener);
    }
}
