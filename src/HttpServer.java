import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static class ServerThread extends Thread {
        Socket connectedClient = null;
        HttpRequest request;
        RequestRouter router;
        Handler handler;

        public ServerThread(Socket newConnection) {
            connectedClient = newConnection;
        }

        public void run() {
            try {
                request = new HttpRequest(connectedClient.getInputStream());
                logRequest(request.requestString);
                router = new RequestRouter(request);
                handler = router.routeToHandler(); // the handler takes care of asking the generator to create the status
                                                    // line and get the body of the request
                handler.createResponse();
                handler.sendResponse(connectedClient.getOutputStream());
            } catch (IOException e) {
                System.out.println(e);
            }
            finally {
                try {
                    System.out.println("A client is going down, closing it's socket");
                    connectedClient.close();
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println("this is from the thread");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int portNumber = 5000;
        System.out.println("Server started!");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        try {
            while (true) {
                Socket newConnection = serverSocket.accept();
                ServerThread newThread = new ServerThread(newConnection);
                newThread.start();
            }
        } finally {
            serverSocket.close();
        }
    }

    public static void logRequest(String stringToLog){
        RequestLogger logger = new RequestLogger();
        logger.logRequest(stringToLog);
    }
}