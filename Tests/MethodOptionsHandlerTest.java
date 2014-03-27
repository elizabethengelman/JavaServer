import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class MethodOptionsHandlerTest {
    HttpRequest request;
    Handler handler;
    String currentDirectory = "../cob_spec/public";
    OutputStream outputStream;
    String testFullHeader = "";
    String testBody = "";

    public void setUp(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("GET " + requestPath + " HTTP/1.1").getBytes()));
        handler = new MethodOptionsHandler();
        outputStream = new ByteArrayOutputStream();
        testFullHeader = ("HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\n\r\n");
        testBody = "";
    }

    @Test
    public void testCreateResponse(){
        setUp("/method_options");
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        assertTrue(testFullHeader.equals(headerOutcome));
        assertTrue(testBody.equals(bodyOutcome));
    }
}
