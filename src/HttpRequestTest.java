import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class HttpRequestTest {
    ByteArrayInputStream inputStream;
    HttpRequest request;


    private void createRequestObject(String requestString){
        inputStream = new ByteArrayInputStream((requestString).getBytes());
        request = new HttpRequest(inputStream);
    }

    @Test
    public void parseTheMethod(){
        createRequestObject("GET / HTTP/1.1");
        assertEquals("GET", request.getMethod());
    }

    @Test
    public void parseThePath(){
        createRequestObject("GET /test_path HTTP/1.1");
        assertEquals("/test_path", request.getPath());
    }

    @Test
    public void setThePath(){
        createRequestObject("GET /test_path HTTP/1.1");
        request.setPath("/");
        assertEquals("/", request.getPath());
    }

    @Test
    public void parseTheHttpVersion(){
        createRequestObject("GET /test_path HTTP/1.1");
        assertEquals("HTTP/1.1", request.getHttpVersion());
    }

    @Test
    public void breakUpParameters(){
        createRequestObject("GET /parameters?variable_1=test1&variable_2=test2");
        Map<String,String> testMap = new LinkedHashMap<String, String>();
        testMap.put("variable_1", "test1");
        testMap.put("variable_2", "test2");
        assertEquals(testMap, request.getIndividualParams());
    }

    @Test
    public void testDecodeParameters(){
        createRequestObject("GET /test_path HTTP/1.1");
        String stringToDecode = "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        String decoded = "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        assertEquals(decoded, request.decodeCharacters(stringToDecode));
    }
}
