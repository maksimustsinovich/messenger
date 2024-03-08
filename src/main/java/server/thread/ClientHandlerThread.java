package server.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ClientHandler;
import utils.*;

import java.io.*;
import java.net.Socket;

public class ClientHandlerThread extends Thread {
    private static final Logger log = LogManager.getLogger(ClientHandlerThread.class);
    private final Socket socket;
    private final ClientHandler clientHandler;
    private BufferedWriter writer;
    private BufferedReader reader;

    public ClientHandlerThread(Socket socket, ClientHandler clientHandler) {
        this.socket = socket;
        this.clientHandler = clientHandler;
    }

    @Override
    public void run() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!isInterrupted()) {
                String rawRequest = reader.readLine();
                Request<?> request = XmlSerializer.convertXmlToRequest(rawRequest);

                handleRequest(request);
            }
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            clientHandler.onConnectionReset();
        } finally {
            try {
                writer.close();
                reader.close();
            } catch (IOException ioException) {
                log.error(ioException.getMessage());
            }
        }
    }

    private void handleRequest(Request<?> request) {
        switch (request.getRequestType()) {
            case BROADCAST_REQUEST -> {
                BroadcastPayload payload = (BroadcastPayload) request.getPayload();
                clientHandler.onBroadcast(payload);
            }
            default -> {

            }
        }
    }

    public void sendMessage(Payload payload) {
        Response<Payload> response = new Response<>(Response.ResponseType.BROADCAST_RESPONSE, payload);
        try {
            String rawXml = XmlSerializer.convertResponseToXml(response);
            writer.write(STR."\{rawXml}\n");
            writer.flush();
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
    }
}
