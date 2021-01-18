package fifilyu.monday_log_server;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@SuppressWarnings(value = "unused")
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class LogResponse {
    private final int Code;
    private final String Message;

    public LogResponse(int code, String message) {
        Code = code;
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public int getCode() {
        return Code;
    }
}
