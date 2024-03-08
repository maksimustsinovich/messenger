package utils;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "payloadClass"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = BroadcastPayload.class, name = "BroadcastPayload")
        }
)
public abstract class Payload {
}
