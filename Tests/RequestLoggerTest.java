import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/24/14.
 */
public class RequestLoggerTest {
    FileReader reader = new FileReader();
    RequestLogger reqLog = new RequestLogger();
    @Test
    public void testLogRequest(){
        reqLog.logRequest("test log entry");
        byte[] log = reader.readFile("../cob_spec/public/logs");
        String logString = new String(log);
        assertTrue(logString.contains("test log entry"));
    }
}
