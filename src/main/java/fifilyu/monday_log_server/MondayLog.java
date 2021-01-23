package fifilyu.monday_log_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings(value = "unused")
public class MondayLog {
    private final Logger logger;
    private final Map<String, Long> timeCostMap = new LinkedHashMap<>();

    /**
     * 将异常堆栈信息转为字符串
     */
    private static String stackTracetoString(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    public MondayLog() {
        this.logger = LoggerFactory.getLogger(MondayLog.class);
    }

    public MondayLog(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void beginCheckpoint(String location, String checkpoint) {
        final String key = location + "|" + checkpoint;
        final Long now = System.currentTimeMillis();
        timeCostMap.put(key, now);

        logger.trace(String.format("%s|Begin checkpoint: %s", location, checkpoint));
    }

    public void endCheckpoint(String location, String checkpoint) {
        final String key = location + "|" + checkpoint;
        final Long now = System.currentTimeMillis();

        if(timeCostMap.containsKey(key)) {
            final Long beginTime = timeCostMap.get(key);
            final Long difference = now - beginTime;
            logger.trace(String.format("%s|End checkpoint: %s, timing statistics: %dms", location, checkpoint, difference));
        } else {
            logger.trace(String.format("%s|End checkpoint: %s", location, checkpoint));
        }
    }

    public <T> void var(String location, String name, T value) {
        logger.trace(String.format("%s|var->%s=%s", location, name, value));
    }

    public <T> void input(String location, String name, T value) {
        logger.trace(String.format("%s|input->%s=%s", location, name, value));
    }

    public <T> void output(String location, String name, T value) {
        logger.trace(String.format("%s|output->%s=%s", location, name, value));
    }

    public void error(String location, String s) {
        logger.error(location + "|" + s);
    }

    public void warn(String location, String s) {
        logger.warn(location + "|" + s);
    }

    public void info(String location, String s) {
        logger.info(location + "|" + s);
    }

    public void debug(String location, String s) {
        logger.debug(location + "|" + s);
    }

    public void trace(String location, String s) {
        logger.trace(location + "|" + s);
    }

    public void error(String location, Exception e) {
        logger.error(location + "|" + stackTracetoString(e));
    }
}
