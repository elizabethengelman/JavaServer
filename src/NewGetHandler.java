import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/26/14.
 */
public class NewGetHandler implements Handler {
    public void createResponse(HttpRequest request, String currentDirectory) {


    }

    private void indexResponse(){

    }
    public void sendResponse(OutputStream outputStream) {

    }

    @Override
    public boolean canHandleRequest(HttpRequest request) {
        return false;
    }
}
