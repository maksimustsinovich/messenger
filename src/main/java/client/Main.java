package client;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static InetAddress inetAddress;
    private static int port;
    private static String nickname;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Options options = initializeOptions();
        parseOptions(args, options);

        log.info("Try to connect to server on {}:{}", inetAddress.getHostAddress(), port);
        try {
            Socket socket = new Socket(inetAddress, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Thread receiver = new Thread(() -> {
                while (true) {
                    try {
                        String raw = reader.readLine();
                        Response<?> r = XmlSerializer.convertXmlToResponse(raw);

                        BroadcastPayload pay = (BroadcastPayload) r.getPayload();

                        System.out.println(STR."\{pay.getSender()} > \{pay.getMessage()}");
                    } catch (IOException e) {

                    }
                }
            });
            receiver.start();
            Thread sender = new Thread(() -> {
                while (true) {
                    try {
                        String s = scanner.nextLine();
                        Request<?> r = new Request<>(Request.RequestType.BROADCAST_REQUEST, new BroadcastPayload(nickname, s, null, null));
                        String raw = XmlSerializer.convertRequestToXml(r);
                        writer.println(raw);
                    } catch (IOException e) {

                    }
                }
            });
            sender.start();
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
    }

    private static void parseOptions(String[] args, Options options) {
        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            inetAddress = InetAddress.getByName(commandLine.getOptionValue("address"));
            port = Integer.parseInt(commandLine.getOptionValue("port"));
            nickname = commandLine.getOptionValue("nickname");

            log.info("Command line arguments parsed successfully");
        } catch (ParseException | UnknownHostException exception) {
            helpFormatter.printHelp("utility-name -a <inet_address> -p <port>", options);
            log.error(exception.getMessage());

            System.exit(1);
        }
    }

    private static Options initializeOptions() {
        Options options = new Options();

        Option inetAddressOption = new Option("a", "address", true, "Server inet address");
        inetAddressOption.setRequired(true);

        Option portOption = new Option("p", "port", true, "Server port");
        portOption.setRequired(true);

        Option nicknameOption = new Option("n", "nickname", true, "Nickname");
        portOption.setRequired(true);

        options.addOption(inetAddressOption);
        options.addOption(portOption);
        options.addOption(nicknameOption);

        return options;
    }
}

