import java.io.OutputStream;

/**
 * Created by elizabethengelman on 3/26/14.
 */
public class CompositeHandler implements Handler {
    Handler[] handlers;
    Handler handler;

    public CompositeHandler(){
        Handler[] handlerArray = {new GetHandler(), new PutHandler(), new PostHandler(), new DeleteHandler(), new OptionsHandler()};
        handlers = handlerArray;
    }

    public void createResponse(HttpRequest request, String currentDirectory) {

    }

    public void sendResponse(OutputStream outputStream) {

    }

    @Override
    public boolean canHandleRequest(HttpRequest request) {
        return false;
    }

    @Override
    public Handler processRequest(HttpRequest request, String currentDirectory, OutputStream output) {
            Handler handler = null;
            for (Handler h : handlers){
                if (h.canHandleRequest(request)){
                    handler = h;
                    break;
                }
            }

        return this;
    }
}
