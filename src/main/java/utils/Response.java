package utils;

public class Response<T extends Payload> {
    private ResponseType responseType;
    private Payload payload;

    public Response(ResponseType responseType, T payload) {
        this.payload = payload;
        this.responseType = responseType;
    }

    public Response() {

    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public enum ResponseType {
        BROADCAST_RESPONSE
    }
}
