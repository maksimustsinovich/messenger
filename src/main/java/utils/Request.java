package utils;

public class Request<T extends Payload> {
    private RequestType requestType;
    private Payload payload;

    public Request() {

    }

    public Request(RequestType requestType, Payload payload) {
        this.requestType = requestType;
        this.payload = payload;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public enum RequestType {
        BROADCAST_REQUEST
    }
}
