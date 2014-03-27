import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static class ServerThread extends Thread {
        Socket connectedClient = null;
        String mainDirectory;
        HttpRequest request;
        RequestRouter router;
        Handler handler;


        public ServerThread(Socket newConnection, String directory) {
            connectedClient = newConnection;
            mainDirectory = directory;
        }

        public void run() {
            try {
                request = new HttpRequest(connectedClient.getInputStream());
                logRequest(request.requestString);
                router = new RequestRouter(request);
                handler = router.routeToHandler();
                handler.processRequest(request, mainDirectory, connectedClient.getOutputStream());
//                handler.createResponse(request, mainDirectory);
//                handler.sendResponse(connectedClient.getOutputStream());
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
        int portNumber = Integer.parseInt(args[0]);
        String currentDirectory = args[1];
        System.out.println("Server started!");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        try {
            while (true) {
                Socket newConnection = serverSocket.accept();
                ServerThread newThread = new ServerThread(newConnection, currentDirectory);
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