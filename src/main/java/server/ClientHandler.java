package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.thread.ClientHandlerThread;
import utils.Payload;

import java.net.Socket;

public class ClientHandler {
    private static final Logger log = LogManager.getLogger(ClientHandler.class);
    private final Server server;
    private final ClientHandlerThread clientHandlerThread;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientHandlerThread = new ClientHandlerThread(socket, this);
    }

    public void start() {
        clientHandlerThread.start();
    }

    public void stop() {
        clientHandlerThread.interrupt();
    }

    public void onBroadcast(Payload payload) {
        log.info("Handle broadcast request.");
        log.debug(STR."Payload=\{payload}");
        server.broadcast(payload);
    }

    public void sendMessage(Payload payload) {
        clientHandlerThread.sendMessage(payload);
    }

    public void onConnectionReset() {
        server.disconnect(this);
    }
}
