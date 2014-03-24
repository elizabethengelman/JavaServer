import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/24/14.
 */
public class RequestLoggerTest {
    FileReader reader = new FileReader();
    @Test
    public void testLogRequest(){
        RequestLogger reqLog = new RequestLogger();
        reqLog.logRequest("test log entry");
        byte[] log = reader.readFile("/logs");
        String logString = new String(log);
        assertTrue(logString.contains("test log entry"));
    }
}
