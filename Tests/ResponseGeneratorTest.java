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
    public void test200WithoutHeaders(){
        generator.create200StatusWithoutHeaders();
        assertArrayEquals("HTTP/1.1 200 OK\r\n\r\n".getBytes(), generator.header);
    }

    @Test
    public void test200ForImage(){
        generator.create200StatusForImage();
        assertArrayEquals("HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n".getBytes(), generator.header);
    }

    @Test
    public void test200ForTextFile(){
        generator.create200StatusForTextFile();
        assertArrayEquals("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\n".getBytes(), generator.header);
    }

    @Test
    public void test405Status(){
        generator.create405Status();
        assertArrayEquals("HTTP/1.1 405 Method Not Allowed\r\n".getBytes(), generator.header);
    }

    @Test
    public void test200StatusForOptionsMethod(){
        generator.create200StatusForOptionsMethod();
        assertArrayEquals("HTTP/1.1 200 OK\r\n Allow: GET,HEAD,POST,OPTIONS,PUT\r\n".getBytes(), generator.header);
    }

    @Test
    public void testRedirectStatus(){
        generator.createRedirectStatus();
        assertArrayEquals("HTTP/1.1 307 Temporary Redirect\r\nLocation: http://localhost:5000/".getBytes(), generator.header);
    }

    @Test
    public void test404Status(){
        generator.create404Status();
        assertArrayEquals("HTTP/1.1 404 Not Found\r\n\r\n".getBytes(), generator.header);
    }

    @Test
    public void testSetBody(){
        byte[] testString = "test".getBytes();
        generator.setBody(testString);
        assertArrayEquals("test".getBytes(), generator.body);
    }

}
