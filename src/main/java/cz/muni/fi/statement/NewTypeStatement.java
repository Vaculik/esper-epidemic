package cz.muni.fi.statement;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by Vaculik on 03/11/2015.
 */
public class NewTypeStatement {
    private EPStatement statement;

    public NewTypeStatement(EPServiceProvider serviceProvider) {
        String expr = "insert into NewType " +
                      "select dis.type as type " +
                      "from Disease(dis.type not in (select type from NewType.std:unique(type))) as dis ";

        statement = serviceProvider.getEPAdministrator().createEPL(expr);
    }

    public void addListener(UpdateListener listener) {
        statement.addListener(listener);
    }
}
