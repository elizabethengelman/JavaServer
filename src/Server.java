import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elizabethengelman on 3/6/14.
 */
public class Server {
    private static String method;
    private static String path;
    private static String httpVersion = "HTTP/1.1";
    private static String statusCode;
    private static String reasonPhrase;
    private static List<String> requestArray = new ArrayList<String>();


    public static void main (String[] args) throws IOException {
        //do i need to set a port number, or just assume it to be on port 80
        int portNumber = 5000;
        String method;
        System.out.println("Server started");
        ServerSocket serverSocket = new ServerSocket(portNumber);
        try{
                BrowserThread thread = new BrowserThread(serverSocket.accept());
                thread.start();

        }
        finally{
            serverSocket.close();
        }
    }

    public static String getResponseInitialLine(){
        String response = httpVersion + " 200 OK\r\n";//how do i know if a request is "OK"
        return response;
    }

    public static class BrowserThread extends Thread {
        private Socket socket;

        public BrowserThread(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try{
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println(getResponseInitialLine());
            }
            catch(IOException e){
                System.out.println(e);
            }

        }
    }
}
