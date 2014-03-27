import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
* Created by elizabethengelman on 3/20/14.
*/
public class ResponseGeneratorTest {
    ResponseGenerator generator;
    @Before
    public void setUp(){
        generator = new ResponseGenerator();
    }

    @Test
    public void testCreatingA200Response(){
        generator.setStatusLine("200");
        generator.setHeaders("Content-Type: text/html");
        generator.setBody("test body".getBytes());
        assertArrayEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\n\r\n".getBytes(), generator.fullHeader);
        assertArrayEquals("test body".getBytes(), generator.body);
    }

    @Test
    public void testCreatingA405Response(){
        generator.setStatusLine("405");
        generator.setHeaders("");
        generator.setBody("test body".getBytes());
        assertArrayEquals("HTTP/1.1 405 Method Not Allowed\r\n\n\r\n".getBytes(), generator.fullHeader);
    }
}