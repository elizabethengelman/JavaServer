import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import static junit.framework.Assert.assertEquals;

public class HttpRequestTest {
    ByteArrayInputStream inputStream;
    HttpRequest request;

    @Before
    public void setup(){
        inputStream = new ByteArrayInputStream(("GET / HTTP/1.1").getBytes());
        request = new HttpRequest(inputStream);
    }

    @Test
    public void parseTheMethod(){
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void parseThePath(){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(("GET /test_path HTTP/1.1").getBytes());
        HttpRequest request = new HttpRequest(inputStream);
        assertEquals("/test_path", request.getPath());
    }

    @Test
    public void parseTheHttpVersion(){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(("GET /test_path HTTP/1.1").getBytes());
        HttpRequest request = new HttpRequest(inputStream);
        assertEquals("HTTP/1.1", request.getHttpVersion());
    }
}
