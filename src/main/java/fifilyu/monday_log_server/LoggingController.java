package fifilyu.monday_log_server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings(value = "unused")
@RestController
@RequestMapping(value = "/")
public class LoggingController {
    private final MondayLog log = new MondayLog(LoggingController.class);

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public LogResponse index() {
        return new LogResponse(0, "Welcome to Monday Log Server.");
    }

    @PostMapping("/add_log")
    public LogResponse addLog(@RequestBody LogRecord logRecord) {
        final LogType logType = logRecord.getLogType();
        final String location = logRecord.getLocation();
        final String message = logRecord.getMessage();
        final String functionName = logRecord.getFunctionName();
        final String varName = logRecord.getVarName();
        final String varValue = logRecord.getVarValue();

        if (logType == null) {
            return new LogResponse(1, "日志类型空指针");
        }

        if (logType == LogType.LOGTYPE_ENTER_FUNC || logType == LogType.LOGTYPE_EXIT_FUNC) {
            if (functionName == null) {
                return new LogResponse(1, "函数名称空指针");
            }
        }

        if (logType == LogType.LOGTYPE_VAR || logType == LogType.LOGTYPE_INPUT || logType == LogType.LOGTYPE_OUTPUT) {
            if (varName == null) {
                return new LogResponse(1, "变量名称空指针");
            }

            if (varValue == null) {
                return new LogResponse(1, "变量值空指针");
            }
        }

        switch (logType) {
            case LOGTYPE_ERROR:
                log.error(location, message);
                break;
            case LOGTYPE_WARN:
                log.warn(location, message);
                break;
            case LOGTYPE_INFO:
                log.info(location, message);
                break;
            case LOGTYPE_DEBUG:
                log.debug(location, message);
                break;
            case LOGTYPE_TRACE:
                log.trace(location, message);
                break;
            case LOGTYPE_ENTER_FUNC:
                log.enterFunc(location, logRecord.getFunctionName());
                break;
            case LOGTYPE_EXIT_FUNC:
                log.exitFunc(location, logRecord.getFunctionName());
                break;
            case LOGTYPE_VAR:
                log.var(location, logRecord.getVarName(), logRecord.getVarValue());
                break;
            case LOGTYPE_INPUT:
                log.input(location, logRecord.getVarName(), logRecord.getVarValue());
                break;
            case LOGTYPE_OUTPUT:
                log.output(location, logRecord.getVarName(), logRecord.getVarValue());
                break;
        }

        return new LogResponse(0, "日志处理成功");
    }
}
