package fifilyu.monday_log_server;

public enum LogType {
    LOGTYPE_ERROR,  // 0
    LOGTYPE_WARN,  // 1
    LOGTYPE_INFO,  // 2
    LOGTYPE_DEBUG,  // 3
    LOGTYPE_TRACE,  // 4
    LOGTYPE_BEGIN_CHECKPOINT,  // 5
    LOGTYPE_END_CHECKPOINT,  // 6
    LOGTYPE_VAR,  // 7
    LOGTYPE_INPUT,  // 8
    LOGTYPE_OUTPUT,  // 9
}
