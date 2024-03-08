package server;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger log = LogManager.getLogger(client.Main.class);
    private static int port;

    public static void main(String[] args) {
        Options options = initializeOptions();
        parseOptions(args, options);

        Server server = new Server(port);

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

    private static void parseOptions(String[] args, Options options) {
        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            port = Integer.parseInt(commandLine.getOptionValue("port"));

            if (port < 1 || port > 65535) {
                throw new ParseException("Invalid port");
            }

            log.info("Command line arguments parsed successfully");
            log.debug(STR."Port=\{port}");
        } catch (ParseException parseException) {
            helpFormatter.printHelp("utility-name -p <port>", options);
            log.error(parseException.getMessage());

            System.exit(1);
        }
    }

    private static Options initializeOptions() {
        Options options = new Options();

        Option portOption = new Option("p", "port", true, "Server port");
        portOption.setRequired(true);

        options.addOption(portOption);

        return options;
    }
}
