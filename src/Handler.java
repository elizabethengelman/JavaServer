import java.io.OutputStream;
import java.util.Map;

/**
 * Created by elizabethengelman on 3/20/14.
 */
interface Handler {
    public Map<String, byte[]> createResponse();
    public void sendResponse(Map<String, byte[]> responsePieces);
    public void processResponse(HttpRequest httpRequest, String directory, OutputStream outputStream);
}
