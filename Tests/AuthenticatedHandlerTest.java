import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public class AuthenticatedHandlerTest {
    HttpRequest request;
    Handler handler;
    String currentDirectory = "../cob_spec/public";
    OutputStream outputStream;
    String testFullHeader = "";
    String testBody = "";

    public void setUp(String requestPath){
        request = new HttpRequest(new ByteArrayInputStream(("GET " + requestPath+ " HTTP/1.1").getBytes()));
        handler = new AuthenticatedHandler();
        outputStream = new ByteArrayOutputStream();
        testFullHeader = ("HTTP/1.1 401 Unauthorized\r\n\r\n");
        testBody = "Authentication required";

    }

    @Test
    public void testCreateResponseForUnauthorized(){
        setUp("/logs");
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        System.out.println("header: " + headerOutcome);
        System.out.println("body: " + bodyOutcome);
        assertTrue(testFullHeader.equals(headerOutcome));
        assertTrue(testBody.equals(bodyOutcome));
    }

    @Test
    public void testCreateResponseForAuth(){
        setUp("/logs");
        request = new HttpRequest(new ByteArrayInputStream(("GET /logs HTTP/1.1Authorization: Basic YWRtaW46aHVudGVyMg==Connection: closeHost: localhost:5000").getBytes()));
        testFullHeader = "HTTP/1.1 200 OK\r\nContent-Type: text/html\n\r\n";
        handler.processResponse(request, currentDirectory,outputStream);
        String headerOutcome = new String(handler.createResponse().get("header"));
        String bodyOutcome = new String(handler.createResponse().get("body"));
        assertTrue(testFullHeader.equals(headerOutcome));
        assertTrue(bodyOutcome.contains("GET /logs"));
    }
}
