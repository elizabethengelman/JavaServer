import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static class ServerThread extends Thread {
        Socket connectedClient = null;
        PrintWriter outputToClient;
        HttpRequest request;
        HttpResponse response;

        public ServerThread(Socket newConnection) {
            connectedClient = newConnection;
        }

        public void run() {
            try {
                outputToClient = new PrintWriter(connectedClient.getOutputStream(), true);//needs the true to pass the test - autoFlush
                request = new HttpRequest(connectedClient.getInputStream());
                response = new HttpResponse(request);
                response.sendResponse(response.createResponse(), response.requestBody, outputToClient);
            } catch (IOException e) {
                System.out.println(e);
            }
            finally {
                try {
                    System.out.println("A client is going down, closing it's socket");
                    connectedClient.close();
                } catch (IOException e) {
                    System.out.println(e);
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
}