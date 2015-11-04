package cz.muni.fi;

import cz.muni.fi.generator.DiseaseGenerator;


public class EsperEpidemic
{
    private static int numOfRounds = 200;
    private static boolean noDelay = false;

    public static void main( String[] args )
    {
        if (args.length == 1 && args[0].equals("--help")) {
            System.out.println(getHelp());
            return;
        }
        if (!parseArgs(args)) {
            System.out.println("Invalid arguments!");
            System.out.println(getHelp());
            return;
        }

        EpidemicMonitor monitor = new EpidemicMonitor();
        if (noDelay) {
            monitor.startProcessing(new DiseaseGenerator().generateTimeRounds(numOfRounds), 0);
        } else {
            // Start monitor with default delay
            monitor.startProcessing(new DiseaseGenerator().generateTimeRounds(numOfRounds));
        }

        monitor.destroyServiceProvider();
    }


    private static boolean parseArgs(String[] args) {
        if (args.length == 0) {
            return true;
        }

        if (args.length == 1) {
            if (parseRounds(args[0])) {
                return true;
            } else {
                return parseNoDelay(args[0]);
            }
        }

        if (args.length == 2) {
            return (parseNoDelay(args[0]) && parseRounds(args[1]));
        }

        return false;
    }


    private static boolean parseRounds(String arg) {
        int value;
        try {
            value = Integer.parseInt(arg);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (value <= 0) {
            return false;
        }
        numOfRounds = value;
        return true;
    }


    private static boolean parseNoDelay(String arg) {
        if (arg.equals("-n") || arg.equals("--no-delay")) {
            noDelay = true;
            return true;
        }
        return false;
    }


    private static String getHelp() {
        String help = "EsperEpidemic [-n] [value]\n\n";
        help += "Options:\n";
        help += "\t-n, --no-delay\tprocess events with no delay\n";
        help += "\tvalue\t\t\tnumber of time rounds, positive integer value\n\n";
        help += "No arguments is equivalent to EsperEpidemic 200\n";
        return help;
    }
}
