package bootstrap;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ApplicationConfig {
    private transient Namespace namespace;
    private String[] args;
    private String logFileName;
    private boolean debugLogEnabled;

    public ApplicationConfig(String[] args) {
        this.args = args;
        this.namespace = buildNamespace(args);
        this.logFileName = namespace.getString("log.file.name");
        this.debugLogEnabled = namespace.getBoolean("debug.log.enabled");
    }

    public String[] getArgs() {
        return args;
    }


    public String isDebugLogEnabled() {
        return String.valueOf(debugLogEnabled);
    }

    public String getLogFileName() {
        return logFileName;
    }

    public Namespace buildNamespace(String[] args) {
        Namespace namespace = null;
        ArgumentParser argumentParser = ArgumentParsers.newFor("The Great Indian Snakes & Ladders ").build().defaultHelp(true)
                .description("The Great Indian Snakes & Ladders \n The project is a command line 2-player game.");
        argumentParser.addArgument("--debug.log.enabled").type(Boolean.class).setDefault(true)
                .help("boolean to determine if debug logging is enabled.");
        argumentParser.addArgument("--log.file.name").type(String.class).setDefault("executionLogs/game.log")
                .help("Name of the log file.");

        try {
            namespace = argumentParser.parseArgs(args);
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        }

        return namespace;
    }
}
