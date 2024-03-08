package server.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientHandler;
import server.Server;
import utils.Payload;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerThread extends Thread {
    private static final Logger log = LogManager.getLogger(ServerThread.class);
    private final int port;
    private final Server server;
    private final CopyOnWriteArrayList<ClientHandler> clientHandlers;

    public ServerThread(int port, Server server) {
        this.port = port;
        this.server = server;
        this.clientHandlers = new CopyOnWriteArrayList<>();
        this.setName(String.valueOf(port));
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            serverSocket.setSoTimeout(1000);
            while (!isInterrupted()) {
                try {
                    Socket socket = serverSocket.accept();

                    ClientHandler clientHandler = new ClientHandler(socket, server);

                    clientHandler.start();
                    clientHandlers.add(clientHandler);

                    log.info("Connection accepted successful.");
                } catch (SocketTimeoutException socketTimeoutException) {
                    log.warn(socketTimeoutException.getMessage());
                }
            }

            clientHandlers.forEach(ClientHandler::stop);
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
    }

    public void sendBroadcastResponse(Payload payload) {
        clientHandlers.forEach((clientHandler) -> clientHandler.sendMessage(payload));
    }

    public void disconnect(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);

    }
}
