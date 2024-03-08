package utils;

import java.util.Arrays;

public class BroadcastPayload extends Payload {
    String message;
    String sender;
    byte[] file;
    String fileName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public BroadcastPayload() {

    }

    public BroadcastPayload(String sender, String message, byte[] file, String fileName) {
        this.message = message;
        this.sender = sender;
        this.file = file;
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return STR."BroadcastPayload { sender=\{sender}, message=\{message}, file=\{Arrays.toString(file)} }";
    }
}
