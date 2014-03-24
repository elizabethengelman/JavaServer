import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by elizabethengelman on 3/24/14.
 */
public class AuthenticatorTest {
    InputStream inputStream;
    HttpRequest request;
    Authenticator auth;
    private void setupObjects(String requestString){
        inputStream = new ByteArrayInputStream((requestString).getBytes());
        request = new HttpRequest(inputStream);
        auth = new Authenticator(request);
    }

    @Test
    public void testUnauthenticated(){
        setupObjects("GET / HTTP/1.1");
        assertFalse(auth.authenticated());
    }

    @Test
    public void testAuthenticated(){
        setupObjects("GET /logs HTTP/1.1Authorization: Basic YWRtaW46aHVudGVyMg==Connection: closeHost: localhost:5000");
        assertTrue(auth.authenticated());
    }
}
//
//
//    public Authenticator(HttpRequest req) {
//        request = req;
//    }
//
//    public boolean authenticated() {
//        boolean authenticated;
//        if (request.authorizationHeaderInfo().equals("admin:hunter2")) {
//            authenticated = true;
//        } else {
//            authenticated = false;
//        }
//        return authenticated;
//    }
//}
//
