import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/20/14.
 */
public interface Handler {
    public String CONTENT_TYPE_HTML_HEADER = "Content-Type: text/html";
    public void createResponse(HttpRequest request, String currentDirectory);
    public void sendResponse(OutputStream outputStream);
}
