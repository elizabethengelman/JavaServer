import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/20/14.
 */
interface Handler {
    public void createResponse(HttpRequest request, String currentDirectory);
    public void sendResponse(OutputStream outputStream);
}
