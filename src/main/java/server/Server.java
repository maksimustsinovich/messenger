package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.thread.ServerThread;
import utils.Payload;

public class Server {
    private static final Logger log = LogManager.getLogger(Server.class);
    private final ServerThread serverThread;
    private final int port;

    public Server(int port) {
        this.serverThread = new ServerThread(port, this);
        this.port = port;
    }

    public void start() {
        log.info(STR."Try to start server listening port \{port}.");
        serverThread.start();
    }

    public void stop() {
        log.info(STR."Try to stop server listening port \{port}.");
        serverThread.interrupt();
    }

    public void broadcast(Payload payload) {
        serverThread.sendBroadcastResponse(payload);
    }

    public void disconnect(ClientHandler clientHandler) {
        serverThread.disconnect(clientHandler);
    }
}
