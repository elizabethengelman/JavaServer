/**
 * Created by elizabethengelman on 3/22/14.
 */
public class Authenticator {
    HttpRequest request;

    public Authenticator(HttpRequest req) {
        request = req;
    }

    public boolean authenticated() {
        boolean authenticated;
        if (request.authorizationHeaderInfo().equals("admin:hunter2")) {
            authenticated = true;
        } else {
            authenticated = false;
        }
        return authenticated;
    }
}

