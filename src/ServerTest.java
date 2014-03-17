import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
* Created by elizabethengelman on 3/10/14.
*/
public class ServerTest {
    String testGetRequest;

    @Before
    public void setup(){
       testGetRequest = "GET / HTTP/1.1";
    }
    @Test
    public void parsesHTTPVersion(){
        assertEquals("HTTP/1.1", Server.getHTTPVersion(testGetRequest));
    }

    @Test
    public void parsesRequestMethod(){
        assertEquals("GET", Server.getMethod(testGetRequest));
    }

    @Test
    public void parsesRequestPath(){
        assertEquals("/", Server.getPath(testGetRequest));
    }


}
