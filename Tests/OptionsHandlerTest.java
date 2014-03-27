import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/27/14.
 */
public class OptionsHandlerTest {
    HttpRequest request;
    Handler handler;
    String currentDirectory = "../cob_spec/public";
    OutputStream outputStream;
    String body = "";
    String header = "";

    public void setUp(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("OPTIONS " + requestPath + " HTTP/1.1").getBytes()));
        handler = new OptionsHandler();
        outputStream = new ByteArrayOutputStream();
        header = ("HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\n\r\n");
        body = "";
    }

    @Test
    public void testCreateResponse(){
        setUp("/method_options");
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        assertTrue(header.equals(headerOutcome));
        assertTrue(body.equals(bodyOutcome));
    }
}
